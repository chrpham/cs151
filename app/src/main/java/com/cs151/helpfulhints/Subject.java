package com.cs151.helpfulhints;

import java.io.Serializable;
import java.util.ArrayList;

public class Subject implements Serializable {
    protected String name;
    protected String description;
    protected boolean on;
    private ArrayList<Reminder> mReminders = new ArrayList<>();
    private static final long serialVersionUID = 1L;

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

    public Reminder getReminder(int i) {
        if(mReminders.size() > 0) {
            return mReminders.get(i);
        } else {
            return null;
        }
    }

    public void turnOn(boolean on) {
        this.on = on;
    }

    public int getReminderCount(){
        return mReminders.size();
    }

    public ArrayList<Reminder> getData(){
        return mReminders;
    }

    @Override
    public boolean equals(Object subject) {
        if(subject instanceof Subject) {
            if(((Subject)subject).on == this.on
                && ((Subject)subject).name.equals(this.name)
                && ((Subject)subject).description.equals(this.description)
                && ((Subject)subject).mReminders.equals(this.mReminders)) {

                return true;
            }
        }
        return false;
    }
}
