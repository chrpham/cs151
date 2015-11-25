package com.cs151.helpfulhints;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.cs151.helpfulhints.Fragments.SubjectListFragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MainActivity extends AppCompatActivity{

    private AlertDialog notifIntervalDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tbar = (Toolbar) findViewById(R.id.activity_toolbar);
        tbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tbar);

        // Set an instance of a subject list fragment in the frame layout
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        SubjectListFragment subjectList = new SubjectListFragment();
        ft.replace(R.id.main_frame_layout, subjectList);
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subject_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_reminder_delay) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            int intervalTime = preferences.getInt(MainApplication.NOTIF_INTERVAL_PREF,
                MainApplication.DEFAULT_NOTIF_INTERVAL);
            View v = getLayoutInflater().inflate(R.layout.notif_interval, null, false);
            final NumberPicker picker = (NumberPicker) v.findViewById(R.id.numberPicker);
            picker.setMaxValue(60);
            picker.setMinValue(1);
            picker.setValue(intervalTime);
            Button cancelButton = (Button) v.findViewById(R.id.cancel_set_interval);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(notifIntervalDialog != null && notifIntervalDialog.isShowing()) {
                        notifIntervalDialog.dismiss();
                    }
                }
            });
            Button confirmButton = (Button) v.findViewById(R.id.confirm_set_interval);
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
            builder.setView(v);
            notifIntervalDialog = builder.create();
            notifIntervalDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
