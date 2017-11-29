package com.onyx.proceduresapp.procedures;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.onyx.proceduresapp.R;

public class ProceduresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procedures);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (null == savedInstanceState) {
            // Get the requested note id
            initFragment(ProceduresFragment.newInstance());
        }
    }



    private void initFragment(Fragment proceduresFragment) {
        // Add the ProceduresFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, proceduresFragment);
        transaction.commit();
    }

}
