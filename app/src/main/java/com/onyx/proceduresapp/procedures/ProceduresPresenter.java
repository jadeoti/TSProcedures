package com.onyx.proceduresapp.procedures;


import android.support.annotation.NonNull;
import android.util.Log;

import com.onyx.proceduresapp.data.Procedure;
import com.onyx.proceduresapp.data.repository.ProceduresRepository;
import com.onyx.proceduresapp.util.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProceduresPresenter implements ProceduresContract.Presenter {

    private BaseSchedulerProvider mSchedulerProvider;

    private ProceduresContract.View mProceduresView;

    private ProceduresRepository mProceduresRepository;

    private CompositeDisposable mCompositeDisposable;

    private boolean mFirstLoad = true;

    public ProceduresPresenter(BaseSchedulerProvider provider, ProceduresContract.View view,
                               ProceduresRepository repository) {
        this.mSchedulerProvider = provider;
        this.mProceduresView = view;
        this.mProceduresRepository = repository;
        mCompositeDisposable = new CompositeDisposable();

    }

    @Override
    public void loadProcedures(boolean forceUpdate) {
        //Network interaction is forced on first load
        loadProcedures(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void openProceduresDetails(@NonNull Procedure procedure) {
        checkNotNull(procedure, "procedure cannot be null!");
        mProceduresView.showPrecedureDetailUi(procedure.getId());
    }


    @Override
    public void subscribe() {
        loadProcedures(false);
        Log.d("PROC", "subscribe");
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }


    private void loadProcedures(boolean forceReload, boolean showProgress) {

        if (showProgress) {
            mProceduresView.showProgress(true);
        }
        if (forceReload) {
            mProceduresRepository.refreshProcedures();
        }


        mCompositeDisposable.clear();
        Disposable disposable = mProceduresRepository
                .getProcedures()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<List<Procedure>>() {
                    @Override
                    public void accept(List<Procedure> procedures) throws Exception {

                        if (procedures.isEmpty()) {
                            // Show a message indicating there are no procedures
                            mProceduresView.showEmpty();
                        } else {
                            // Show the list of tasks
                            mProceduresView.showProcedures(procedures);
                        }

                    }
                }, new Consumer<Throwable>() { // on error
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("PROC", throwable.getMessage());
                        //  treat error


                    }
                }, new Action() { // on complete
                    @Override
                    public void run() throws Exception {
                        mProceduresView.showProgress(false);
                    }
                });

        mCompositeDisposable.add(disposable);

    }
}
