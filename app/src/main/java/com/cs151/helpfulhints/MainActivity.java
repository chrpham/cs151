package com.cs151.helpfulhints;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import com.cs151.helpfulhints.Background.ReminderGCMTaskService;
import com.cs151.helpfulhints.Fragments.SubjectListFragment;
import com.google.android.gms.gcm.GcmNetworkManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MainActivity extends AppCompatActivity{

    private AlertDialog notifIntervalDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tbar = (Toolbar) findViewById(R.id.activity_toolbar);
        SwitchCompat masterToggle = (SwitchCompat) tbar.findViewById(R.id.master_toggle);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        masterToggle.setChecked(prefs.getBoolean(MainApplication.MASTER_TOGGLE_PREF, true));
        masterToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.edit().putBoolean(MainApplication.MASTER_TOGGLE_PREF, isChecked).commit();
                if (isChecked) {
                    MainApplication.scheduleTask(buttonView.getContext());
                } else {
                    GcmNetworkManager.getInstance(buttonView.getContext()).cancelTask(MainApplication.NOTIF_TASK_TAG, ReminderGCMTaskService.class);
                }
            }
        });
        masterToggle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("This is a master switch which turns on or off all hint notifications");
                builder.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });

        ImageButton notifTimeButton = (ImageButton) tbar.findViewById(R.id.alarm_interval_time);
        notifTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                int intervalTime = preferences.getInt(MainApplication.NOTIF_INTERVAL_PREF,
                    MainApplication.DEFAULT_NOTIF_INTERVAL);
                View view = getLayoutInflater().inflate(R.layout.notif_interval, null, false);
                final NumberPicker picker = (NumberPicker) view.findViewById(R.id.numberPicker);
                picker.setMaxValue(60);
                picker.setMinValue(1);
                picker.setValue(intervalTime);
                Button cancelButton = (Button) view.findViewById(R.id.cancel_set_interval);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(notifIntervalDialog != null && notifIntervalDialog.isShowing()) {
                            notifIntervalDialog.dismiss();
                        }
                    }
                });
                Button confirmButton = (Button) view.findViewById(R.id.confirm_set_interval);
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(notifIntervalDialog != null && notifIntervalDialog.isShowing()) {
                            preferences.edit().putInt(
                                MainApplication.NOTIF_INTERVAL_PREF, picker.getValue()).commit();
                            Class m = MainApplication.class;
                            Method[] methods = m.getMethods();
                            for (int i = 0; i < methods.length; i++) {
                                if(methods[i].getName().equals("scheduleTask")) {
                                    try {
                                        methods[i].invoke(null, v.getContext());
                                    } catch (IllegalAccessException | InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                }
                            }
                            notifIntervalDialog.dismiss();
                        }
                    }
                });
                builder.setView(view);
                notifIntervalDialog = builder.create();
                notifIntervalDialog.show();
            }
        });
        tbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tbar);

        // Set an instance of a subject list fragment in the frame layout
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        SubjectListFragment subjectList = new SubjectListFragment();
        ft.replace(R.id.main_frame_layout, subjectList);
        ft.commit();

    }

    @Override
    public void onBackPressed(){
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause(){
        super.onPause();
        MainApplication.Data.save();
    }
}
