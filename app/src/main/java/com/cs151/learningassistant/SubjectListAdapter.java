package com.cs151.learningassistant;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs151.learningassistant.Fragments.EditSubjectDialogFragment;

import java.util.ArrayList;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectCardViewHolder> {

    private ArrayList<Subject> data;
    final private FragmentManager fragManager;

    public SubjectListAdapter(ArrayList<Subject> d, FragmentManager manager) {
        data = d;
        fragManager = manager;
    }

    @Override
    public SubjectCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.subject_list_card, parent, false);

        return new SubjectCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SubjectCardViewHolder holder, final int position) {
        final Subject subject = MainApplication.Data.getSubject(position);
        DataChangeListener listener = new DataChangeListener() {
            @Override
            public void onDataChange() {
                holder.mTitleText.setText(MainApplication.Data.getSubject(position).getName());
                holder.mDescriptionText.setText(MainApplication.Data.getSubject(position).getDescription());
            }
        };
        MainApplication.Data.attachListener(listener);
        holder.mTitleText.setText(subject.name);
        holder.mDescriptionText.setText(subject.description);
        holder.mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fragManager.beginTransaction();
                Fragment fragment = fragManager.findFragmentByTag("editFragment");
                if(fragment != null) {
                    ft.remove(fragment);
                }
                ft.addToBackStack(null);
                DialogFragment editSubjDialog = EditSubjectDialogFragment.newInstance(subject);
                editSubjDialog.show(ft, "editFragment");
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
