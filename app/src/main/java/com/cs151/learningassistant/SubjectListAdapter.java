package com.cs151.learningassistant;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectCardViewHolder> {

    private ArrayList<Subject> data;

    public SubjectListAdapter(ArrayList<Subject> d) {
        data = d;
    }

    @Override
    public SubjectCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.subject_list_card, parent, false);

        return new SubjectCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubjectCardViewHolder holder, int position) {
        Subject subject = MainApplication.Data.getSubject(position);
        holder.mTitleText.setText(subject.name);
        holder.mDescriptionText.setText(subject.description);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
