package com.cs151.helpfulhints;

import android.content.Context;
import android.util.Log;

import com.cs151.helpfulhints.Callbacks.DataChangeListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class ReminderDataStructure {
    private static final String TAG = ReminderDataStructure.class.getSimpleName();
    private static final String saveFileName = "SaveData.ser";
    private static File saveFile;
    private ArrayList<DataChangeListener> listeners;
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
        return subjects;
    }

    /**
     * Write to JSON object containing all the reminder data
     */
    public void save(){
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(subjects);
            oos.close();
            fos.close();
            Log.v(TAG, "Data saved");
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

    public static synchronized ReminderDataStructure getInstance(Context context){
        Log.v(TAG, "Getting instance");
        if(instance == null) {
            instance = new ReminderDataStructure(context);
            saveFile = new File(context.getFilesDir(), saveFileName);
            if (saveFile.exists()) {
                try {
                    FileInputStream fileIn = new FileInputStream(saveFile);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    instance.subjects = (ArrayList<Subject>) in.readObject();
                    in.close();
                    fileIn.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return instance;
    }

    public void addReminder(int subjectIndex, Reminder r) {
        subjects.get(subjectIndex).addReminder(r);
        updateListeners();
    }

    public Reminder getNextNotification() {
        ArrayList<Reminder> hints = new ArrayList<>();
        Random r = new Random(System.currentTimeMillis());
        int numberOfReminders = 0;
        for(int i = 0; i < subjects.size(); i++) {
            Log.v(TAG, String.format("Subject: %s on: %b", subjects.get(i).name, subjects.get(i).on));
            if(subjects.get(i).on) {
                for (int j = 0; j < subjects.get(i).getData().size(); j++) {
                    Log.v(TAG, String.format("Hint: %s on: %b", subjects.get(i).getData().get(j).mTitle, subjects.get(i).getData().get(j).on));
                    if (subjects.get(i).getData().get(j).on) {
                        numberOfReminders++;
                        hints.add(subjects.get(i).getData().get(j));
                    }
                }
            }
        }
        if(numberOfReminders > 0) {
            int indexOfHint = r.nextInt(numberOfReminders);
            Log.v(TAG, String.format("Displaying: %s -> %s", hints.get(indexOfHint).mTitle, hints.get(indexOfHint).mBody));
            return hints.get(indexOfHint);
        } else {
            return null;
        }
    }

}
