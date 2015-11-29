package com.cs151.helpfulhints.Fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs151.helpfulhints.Callbacks.DataChangeListener;
import com.cs151.helpfulhints.Helpers.SimpleItemTouchHelperCallback;
import com.cs151.helpfulhints.MainApplication;
import com.cs151.helpfulhints.R;
import com.cs151.helpfulhints.ReminderListAdapter;

public class ReminderListFragment extends Fragment {
    private static final String TAG = ReminderListFragment.class.getSimpleName();
    private int subjectIndex;
    private static final String BUNDLE_ARG_INDEX= "subject_index";

    public static ReminderListFragment newInstance(int index) {
        ReminderListFragment frag = new ReminderListFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_ARG_INDEX, index);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle args) {
        super.onCreate(args);
        subjectIndex = getArguments().getInt(BUNDLE_ARG_INDEX);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reminder_list, container, false);
        final RecyclerView recView = (RecyclerView) rootView.findViewById(R.id.reminder_list_rec_view);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final ReminderListAdapter adapter = new ReminderListAdapter(subjectIndex, getFragmentManager());
        recView.setAdapter(adapter);

        final FloatingActionButton addSubjectButton = (FloatingActionButton) rootView.findViewById(R.id.add_reminder_button);
        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = getFragmentManager().findFragmentByTag("addReminderFragment");
                if (fragment != null) {
                    ft.remove(fragment);
                }
                ft.addToBackStack(null);
                DialogFragment addSubj = AddReminderDialogFragment.newInstance(subjectIndex);
                addSubj.show(ft, "addReminderFragment");
            }
        });
        DataChangeListener dataChangeListener = new DataChangeListener() {
            @Override
            public void onDataChange() {
                adapter.notifyDataSetChanged();
                recView.invalidate();
            }
        };
        MainApplication.Data.attachListener(dataChangeListener);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        if(view.getContext() instanceof AppCompatActivity) {
            ActionBar bar = ((AppCompatActivity) view.getContext()).getSupportActionBar();
            if(bar != null) {
                bar.setTitle(MainApplication.Data.getSubject(subjectIndex).getName());
            }
        }
    }

}
