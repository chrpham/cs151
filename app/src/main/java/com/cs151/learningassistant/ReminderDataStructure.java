package com.cs151.learningassistant;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class ReminderDataStructure implements Serializable {
    private static final String TAG = ReminderDataStructure.class.getSimpleName();
    private final String saveFileName = "SaveData";
    private ArrayList<DataChangeListener> listeners;
    private ArrayList<Subject> subjects;

    public ReminderDataStructure(Context context){

        File saveFile = new File(context.getFilesDir(), saveFileName);
        if(saveFile.exists()) {
            //Load the data into the subjectMap
        } else {
            //no data exists!
            subjects = new ArrayList<>();
        }
        listeners = new ArrayList<>();

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
        Gson gson = new Gson();
        Log.d(TAG, "gson.toJson: \n" + gson.toJson(this));
    }

    public void notifyDataChange(){
        updateListeners();
    }

    private void updateListeners(){
        for(int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onDataChange();
        }
    }
}
