package com.cs151.helpfulhints;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cs151.helpfulhints.Callbacks.SubjectClickListener;

public class SubjectCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected TextView mTitleText;
    protected TextView mDescriptionText;
    protected ImageButton mSettingsButton;
    protected SwitchCompat mSwitch;
    protected SubjectClickListener listener;

    public SubjectCardViewHolder(final View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mTitleText = (TextView) itemView.findViewById(R.id.subject_title_text);
        mDescriptionText = (TextView) itemView.findViewById(R.id.subject_description_text);
        mSettingsButton = (ImageButton) itemView.findViewById(R.id.edit_subject_button);
        mSwitch = (SwitchCompat) itemView.findViewById(R.id.subject_toggle_switch);
    }

    public void setClickerListener(SubjectClickListener listener) {
        this.listener = listener;
    }

    /**
     * On clicking the view holder, open the subject info fragment in the activity
     * @param v
     */
    @Override
    public void onClick(View v) {
        listener.onSubjectClicked(getLayoutPosition());
    }
}
