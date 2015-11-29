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

public class EditReminderDialogFragment extends DialogFragment {

    private EditText titleText;
    private EditText descriptionText;
    private String mHintName;
    private String mHintBody;
    private Button cancelAdd;
    private Button confirmAdd;
    private TextInputLayout titleLayout;
    private TextInputLayout contentLayout;
    private int mSubjectIndex;
    private int mHintIndex;

    public static EditReminderDialogFragment newInstance(int subjIndex, int hintIndex){
        EditReminderDialogFragment dialogFragment = new EditReminderDialogFragment();
        dialogFragment.mSubjectIndex = subjIndex;
        dialogFragment.mHintIndex = hintIndex;
        dialogFragment.mHintName = MainApplication.Data.getSubject(subjIndex).getReminder(hintIndex).getTitle();
        dialogFragment.mHintBody = MainApplication.Data.getSubject(subjIndex).getReminder(hintIndex).getBody();
        return dialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_subject, container, false);

        titleLayout = (TextInputLayout) v.findViewById(R.id.title_layout);
        titleLayout.setHint("Hint Name");
        contentLayout = (TextInputLayout) v.findViewById(R.id.edit_desription_layout);
        contentLayout.setHint("Hint Description");

        titleText = (EditText) v.findViewById(R.id.add_subjname_edittext);
        titleText.setText(mHintName);

        descriptionText = (EditText) v.findViewById(R.id.add_desription_edittext);
        descriptionText.setText(mHintBody);

        confirmAdd = (Button) v.findViewById(R.id.save_new_subject);
        confirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainApplication.Data.getSubject(mSubjectIndex).getReminder(mHintIndex)
                    .setTitle(titleText.getText().toString());
                MainApplication.Data.getSubject(mSubjectIndex).getReminder(mHintIndex)
                    .setBody(descriptionText.getText().toString());
                MainApplication.Data.notifyDataChange();
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
