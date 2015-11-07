package com.cs151.learningassistant;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ReminderDataStructure implements Serializable {
    private transient static final String TAG = ReminderDataStructure.class.getSimpleName();
    private transient static final String saveFileName = "SaveData.ser";
    private transient static File saveFile;
    private transient ArrayList<DataChangeListener> listeners;
    private ArrayList<Subject> subjects;
    private static volatile ReminderDataStructure instance = null;

    private ReminderDataStructure(Context context){
        saveFile = new File(context.getFilesDir(), saveFileName);
        listeners = new ArrayList<>();
        subjects = new ArrayList<>();
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
        updateListeners();
    }

    public void attachListener(DataChangeListener l) {
        listeners.add(l);
    }

    public Subject getSubject(int index) {
        return subjects.get(index);
    }

    public ArrayList<Subject> getData(){
        return (ArrayList<Subject>) subjects.clone();
    }

    /**
     * Write to JSON object containing all the reminder data
     */
    public void save(){
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(instance);
            oos.close();
            fos.close();
            Log.v(TAG, "Data saved");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyDataChange(){
        updateListeners();
    }

    private void updateListeners(){
        for(int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onDataChange();
        }
    }

    public static ReminderDataStructure getInstance(Context context){
        Log.v(TAG, "Getting instance");
        if(instance == null) {
            saveFile = new File(context.getFilesDir(), saveFileName);
            if(saveFile.exists()) {
                try {
                    FileInputStream fileIn = new FileInputStream(saveFile);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    instance = (ReminderDataStructure) in.readObject();
                    in.close();
                    fileIn.close();
                    instance.listeners = new ArrayList<>();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                instance = new ReminderDataStructure(context);
            }
        }
        if(instance == null) {
            instance = new ReminderDataStructure(context);
        }
        return instance;
    }
}
