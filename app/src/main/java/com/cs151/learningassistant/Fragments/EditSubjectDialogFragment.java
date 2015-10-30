package com.cs151.learningassistant.Fragments;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs151.learningassistant.R;
import com.cs151.learningassistant.Subject;

public class EditSubjectDialogFragment extends DialogFragment {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button cancelEdit;
    private Button confirmEdit;
    private String title;
    private String description;
    private View.OnClickListener onCancelButtonClicked;
    private View.OnClickListener onConfirmButtonClicked;

    public static EditSubjectDialogFragment newInstance(Subject s) {
        EditSubjectDialogFragment fragment = new EditSubjectDialogFragment();
        fragment.title = s.getName();
        fragment.description = s.getDescription();
        fragment.onCancelButtonClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }
        };
        fragment.onConfirmButtonClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save
                Toast.makeText(v.getContext(), "Save", Toast.LENGTH_SHORT).show();
            }
        };
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_subject_dialog_fragment, container, false);
        titleEditText = (EditText) v.findViewById(R.id.edit_subjname_edittext);
        descriptionEditText = (EditText) v.findViewById(R.id.edit_desription_edittext);
        cancelEdit = (Button) v.findViewById(R.id.cancel_edit_subject);
        confirmEdit = (Button) v.findViewById(R.id.save_edit_subject);

        titleEditText.setText(title);
        descriptionEditText.setText(description);
        cancelEdit.setOnClickListener(onCancelButtonClicked);
        confirmEdit.setOnClickListener(onConfirmButtonClicked);

        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }
}
