package com.cs151.helpfulhints;

import java.io.Serializable;

public class Reminder implements Serializable {
    protected String mTitle;
    protected String mBody;
    protected boolean on;
    private static final long serialVersionUID = 1L;

    public Reminder(){
        this(null, null);
    }

    public Reminder(String title) {
        this(title, null);
    }

    public Reminder(String title, String body) {
        mTitle = title;
        mBody = body;
    }

    public void turnOn(boolean on) {
        this.on = on;
    }

    @Override
    public boolean equals(Object r) {
        return r instanceof Reminder
            && ((Reminder)r).mTitle.equals(this.mTitle)
            && ((Reminder)r).mBody.equals(this.mBody)
            && this.on == ((Reminder)r).on;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String s) {
        mTitle = s;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String s) {
        mBody = s;
    }
}
