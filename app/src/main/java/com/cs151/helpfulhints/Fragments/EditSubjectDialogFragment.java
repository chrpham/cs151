package com.cs151.helpfulhints.Fragments;

import android.app.Dialog;
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
import com.cs151.helpfulhints.Subject;

public class EditSubjectDialogFragment extends DialogFragment {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button cancelEdit;
    private Button confirmEdit;
    private String title;
    private String description;
    private TextInputLayout titleLayout;
    private TextInputLayout contentLayout;
    private View.OnClickListener onCancelButtonClicked;
    private View.OnClickListener onConfirmButtonClicked;

    public static EditSubjectDialogFragment newInstance(final Subject s) {
        final EditSubjectDialogFragment fragment = new EditSubjectDialogFragment();
        fragment.title = s.getName();
        fragment.description = s.getDescription();
        fragment.onCancelButtonClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.dismiss();
            }
        };
        fragment.onConfirmButtonClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s.setName(fragment.titleEditText.getText().toString());
                s.setDescription(fragment.descriptionEditText.getText().toString());
                MainApplication.Data.notifyDataChange();
                fragment.dismiss();
            }
        };
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_subject_dialog_fragment, container, false);
        titleLayout = (TextInputLayout) v.findViewById(R.id.title_layout);
        titleLayout.setHint("Subject Name");
        contentLayout = (TextInputLayout) v.findViewById(R.id.edit_desription_layout);
        contentLayout.setHint("Subject Description");
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

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume(){
        super.onResume();
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.5) ;
        getDialog().getWindow().setLayout(width, height);
    }
}
