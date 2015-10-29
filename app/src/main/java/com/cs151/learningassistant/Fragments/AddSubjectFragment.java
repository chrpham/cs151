package com.cs151.learningassistant.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cs151.learningassistant.MainApplication;
import com.cs151.learningassistant.R;
import com.cs151.learningassistant.Reminders.Definition;
import com.cs151.learningassistant.Subject;

public class AddSubjectFragment extends Fragment {
    private static final String TAG = AddSubjectFragment.class.getSimpleName();

    private FloatingActionButton fab;
    private EditText titleText;
    private EditText descriptionText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_subject, container, false);

        titleText = (EditText) v.findViewById(R.id.add_title_edit_text);

        descriptionText = (EditText) v.findViewById(R.id.add_desription_textview);

        fab = (FloatingActionButton) v.findViewById(R.id.confirm_subject_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subject subject = new Subject(titleText.getText().toString());
                subject.setDescription(descriptionText.getText().toString());
                MainApplication.Data.addSubject(subject);
                getFragmentManager().popBackStackImmediate();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
