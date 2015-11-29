package com.cs151.helpfulhints.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cs151.helpfulhints.MainApplication;
import com.cs151.helpfulhints.R;
import com.cs151.helpfulhints.Reminder;

public class AddReminderDialogFragment extends DialogFragment {
    private static final String TAG = AddSubjectDialogFragment.class.getSimpleName();

    private EditText titleText;
    private EditText descriptionText;
    private TextInputLayout titleInputLayout;
    private TextInputLayout bodyInputLayout;
    private Button cancelAdd;
    private Button confirmAdd;
    private int mSubjectIndex;

    public static AddReminderDialogFragment newInstance(int subjIndex){
        AddReminderDialogFragment dialogFragment = new AddReminderDialogFragment();
        dialogFragment.mSubjectIndex = subjIndex;
        return dialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_subject, container, false);

        titleInputLayout = (TextInputLayout) v.findViewById(R.id.title_layout);
        titleInputLayout.setHint("Hint Name");

        titleText = (EditText) v.findViewById(R.id.add_subjname_edittext);

        bodyInputLayout = (TextInputLayout) v.findViewById(R.id.edit_desription_layout);
        bodyInputLayout.setHint("Hint Content");

        descriptionText = (EditText) v.findViewById(R.id.add_desription_edittext);

        confirmAdd = (Button) v.findViewById(R.id.save_new_subject);
        confirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reminder r = new Reminder(titleText.getText().toString(), descriptionText.getText().toString());
                MainApplication.Data.addReminder(mSubjectIndex, r);
                dismiss();
            }
        });

        cancelAdd = (Button) v.findViewById(R.id.cancel_new_subject);
        cancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.5) ;
        getDialog().getWindow().setLayout(width, height);
    }
}
