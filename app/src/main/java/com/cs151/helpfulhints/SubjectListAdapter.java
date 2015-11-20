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
import com.cs151.helpfulhints.Callbacks.SubjectClickListener;
import com.cs151.helpfulhints.Fragments.EditSubjectDialogFragment;
import com.cs151.helpfulhints.Helpers.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectCardViewHolder> implements ItemTouchHelperAdapter {

    final private FragmentManager fragManager;
    private SubjectClickListener clickListener;

    public void setClickListener(SubjectClickListener l) {
        clickListener = l;
    }

    public SubjectListAdapter(ArrayList<Subject> d, FragmentManager manager) {
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
        holder.setClickerListener(clickListener);
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
                Fragment fragment = fragManager.findFragmentByTag("editSubjectFragment");
                if (fragment != null) {
                    ft.remove(fragment);
                }
                ft.addToBackStack(null);
                DialogFragment editSubjDialog = EditSubjectDialogFragment.newInstance(subject);
                editSubjDialog.show(ft, "editSubjectFragment");
            }
        });
        holder.mSwitch.setChecked(subject.on);
        holder.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subject.on = isChecked;
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainApplication.Data.getData().size();
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(MainApplication.Data.getData(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        MainApplication.Data.getData().remove(position);
        notifyItemRemoved(position);
    }
}
