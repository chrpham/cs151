package com.cs151.learningassistant;

import com.cs151.learningassistant.Reminders.Reminder;

import java.util.ArrayList;

public class Subject {
    protected String name;
    protected String description;
    private ArrayList<Reminder> mReminders = new ArrayList<>();

    public Subject(String n) {
        name = n;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addReminder(Reminder r) {
        mReminders.add(r);
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("subject: " + name + "{\n");
        for(Reminder r : mReminders) {
            stringBuilder.append("\t"+r.toString()+"\n");
        }
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    public void setDescription(String d) {
        description = d;
    }
}
