package com.cs151.learningassistant.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs151.learningassistant.MainApplication;
import com.cs151.learningassistant.R;
import com.cs151.learningassistant.SubjectListAdapter;

public class SubjectListFragment extends Fragment {
    private static final String TAG = SubjectListFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subject_list, container, false);
        RecyclerView recView = (RecyclerView) rootView.findViewById(R.id.subject_list_rec_view);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.setAdapter(new SubjectListAdapter(MainApplication.Data.getData(), getFragmentManager()));

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        RecyclerView recView = (RecyclerView) view.findViewById(R.id.subject_list_rec_view);

        final Toolbar tbar = (Toolbar) view.findViewById(R.id.toolbar);

        final FloatingActionButton addSubjectButton = (FloatingActionButton) view.findViewById(R.id.add_subject_button);

        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment addSubjFrag = new AddSubjectFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setSharedElementReturnTransition(new ChangeBounds());
                    addSubjFrag.setSharedElementEnterTransition(new ChangeBounds());
                    ft.addSharedElement(tbar, "add_subject_tbar_transition");
                    ft.addSharedElement(addSubjectButton, "add_subject_fab_transition");
                }
                ft.replace(R.id.main_frame_layout, addSubjFrag)
                    .addToBackStack("add_subject_transaction")
                    .commit();
            }
        });

    }

}
