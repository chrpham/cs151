package com.cs151.learningassistant.Reminders;

import java.io.Serializable;

public class Definition implements Reminder, Serializable{

    final String word;
    final String definition;

    public Definition(String w, String d) {
        word = w;
        definition = d;
    }

    @Override
    public String toString() {
        return String.format("definition { \n\t\tword: %s\n\t\tdefinition: %s\n\t}", word, definition);
    }
}
