package com.onyx.proceduresapp.procedures;


import android.support.annotation.NonNull;

import com.onyx.proceduresapp.data.ProcedureDetail;
import com.onyx.proceduresapp.data.repository.ProceduresRepository;
import com.onyx.proceduresapp.util.schedulers.BaseSchedulerProvider;

public class ProceduresPresenter implements ProceduresContract.Presenter {

    private BaseSchedulerProvider mSchedulerProvider;

    private ProceduresContract.View mProceduresView;

    private ProceduresRepository mProceduresRepository;

    public ProceduresPresenter(BaseSchedulerProvider provider, ProceduresContract.View view,
                               ProceduresRepository repository) {
        this.mSchedulerProvider = provider;
        this.mProceduresView = view;
        this.mProceduresRepository = repository;
    }

    @Override
    public void loadProcedures(boolean forceUpdate) {

    }

    @Override
    public void openProceduresDetails(@NonNull ProcedureDetail detail) {

    }
}
