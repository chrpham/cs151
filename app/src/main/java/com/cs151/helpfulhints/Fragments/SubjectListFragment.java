package com.cs151.helpfulhints.Fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs151.helpfulhints.Callbacks.DataChangeListener;
import com.cs151.helpfulhints.Callbacks.SubjectClickListener;
import com.cs151.helpfulhints.Helpers.SimpleItemTouchHelperCallback;
import com.cs151.helpfulhints.MainApplication;
import com.cs151.helpfulhints.R;
import com.cs151.helpfulhints.SubjectListAdapter;

public class SubjectListFragment extends Fragment implements SubjectClickListener {
    private static final String TAG = SubjectListFragment.class.getSimpleName();
    private ItemTouchHelper mItemTouchHelper;
    private SubjectListAdapter mAdapter;
    private RecyclerView recView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subject_list, container, false);
        recView = (RecyclerView) rootView.findViewById(R.id.subject_list_rec_view);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SubjectListAdapter(MainApplication.Data.getData(), getFragmentManager());
        mAdapter.setClickListener(this);
        recView.setAdapter(mAdapter);
        DataChangeListener dataChangeListener = new DataChangeListener() {
            @Override
            public void onDataChange() {
                mAdapter.notifyDataSetChanged();
                recView.invalidate();
            }
        };
        MainApplication.Data.attachListener(dataChangeListener);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recView);

        final FloatingActionButton addSubjectButton = (FloatingActionButton) view.findViewById(R.id.add_subject_button);

        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = getFragmentManager().findFragmentByTag("addSubjectFragment");
                if(fragment != null) {
                    ft.remove(fragment);
                }
                ft.addToBackStack(null);
                DialogFragment addSubj = AddSubjectDialogFragment.newInstance();
                addSubj.show(ft, "addSubjectFragment");
            }
        });

    }

    @Override
    public void onSubjectClicked(int pos) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame_layout, ReminderListFragment.newInstance(pos));
        ft.addToBackStack("subjectList");
        ft.commit();
    }
}
