package com.cs151.learningassistant;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SubjectCardViewHolder extends RecyclerView.ViewHolder {

    protected TextView mTitleText;
    protected TextView mDescriptionText;

    public SubjectCardViewHolder(View itemView) {
        super(itemView);
        mTitleText = (TextView) itemView.findViewById(R.id.subject_title_text);
        mDescriptionText = (TextView) itemView.findViewById(R.id.subject_description_text);
    }
}
