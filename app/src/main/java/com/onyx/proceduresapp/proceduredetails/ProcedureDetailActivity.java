package com.onyx.proceduresapp.proceduredetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.onyx.proceduresapp.R;

public class ProcedureDetailActivity extends AppCompatActivity {

    public static final String EXTRA_PROC_ID = "EXTRA_PROC_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procedure_detail);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // Get the requested procedure id
        String taskId = getIntent().getStringExtra(EXTRA_PROC_ID);

        ProcedureDetailFragment procedureDetailFragment = (ProcedureDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (procedureDetailFragment == null) {
            procedureDetailFragment = ProcedureDetailFragment.newInstance(taskId);
            initFragment(procedureDetailFragment);
        }
    }

    private void initFragment(Fragment detailsFragment) {
        // Add the ProceduresFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, detailsFragment);
        transaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
