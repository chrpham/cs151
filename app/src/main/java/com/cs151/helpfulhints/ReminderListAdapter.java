package com.cs151.helpfulhints;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.cs151.helpfulhints.Callbacks.DataChangeListener;
import com.cs151.helpfulhints.Fragments.EditReminderDialogFragment;
import com.cs151.helpfulhints.Helpers.ItemTouchHelperAdapter;

import java.util.Collections;

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListViewHolder> implements ItemTouchHelperAdapter {

    private int subjectIndex;
    private FragmentManager mFragManager;

    public ReminderListAdapter(int subjectIndex, FragmentManager fragmentManager) {
        this.subjectIndex = subjectIndex;
        this.mFragManager = fragmentManager;
    }

    @Override
    public ReminderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.subject_list_card, parent, false);

        return new ReminderListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ReminderListViewHolder holder, final int position) {
        final Reminder r = MainApplication.Data.getSubject(subjectIndex).getReminder(position);
        DataChangeListener listener = new DataChangeListener() {
            @Override
            public void onDataChange() {
                holder.mTitleText.setText(MainApplication.Data.getSubject(subjectIndex).getReminder(position).mTitle);
                holder.mDescriptionText.setText(MainApplication.Data.getSubject(subjectIndex).getReminder(position).mBody);
            }
        };
        MainApplication.Data.attachListener(listener);
        holder.mTitleText.setText(r.mTitle);
        holder.mDescriptionText.setText(r.mBody);
        holder.mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = mFragManager.beginTransaction();
                Fragment fragment = mFragManager.findFragmentByTag("editReminderFragment");
                if (fragment != null) {
                    ft.remove(fragment);
                }
                ft.addToBackStack(null);
                DialogFragment editSubjDialog = EditReminderDialogFragment.newInstance(subjectIndex, position);
                editSubjDialog.show(ft, "editReminderFragment");
            }
        });
        holder.mToggle.setChecked(r.on);
        holder.mToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                r.on = isChecked;
            }
        });

    }

    @Override
    public int getItemCount() {
        return MainApplication.Data.getSubject(subjectIndex).getReminderCount();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(MainApplication.Data.getSubject(subjectIndex).getData(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        MainApplication.Data.getSubject(subjectIndex).getData().remove(position);
        notifyItemRemoved(position);
    }
}
