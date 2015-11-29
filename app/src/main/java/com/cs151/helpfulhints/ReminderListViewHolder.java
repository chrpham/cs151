package com.cs151.helpfulhints;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ReminderListViewHolder extends RecyclerView.ViewHolder {

    protected TextView mTitleText;
    protected TextView mDescriptionText;
    protected ImageButton mSettingsButton;
    protected SwitchCompat mToggle;

    public ReminderListViewHolder(final View itemView) {
        super(itemView);
        mTitleText = (TextView) itemView.findViewById(R.id.subject_title_text);
        mDescriptionText = (TextView) itemView.findViewById(R.id.subject_description_text);
        mSettingsButton = (ImageButton) itemView.findViewById(R.id.edit_subject_button);
        mToggle = (SwitchCompat) itemView.findViewById(R.id.subject_toggle_switch);
    }

}
