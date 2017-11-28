package com.onyx.proceduresapp.proceduredetails;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;
import com.onyx.proceduresapp.data.ProcedureDetail;
import com.onyx.proceduresapp.data.repository.ProceduresRepository;
import com.onyx.proceduresapp.util.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Morph-Deji on 28-Nov-17.
 */

public class ProcedureDetailPresenter implements ProcedureDetailContract.ProcedureDetailPresenter {

    @NonNull
    private BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private ProcedureDetailContract.View mProcedureDetailView;

    @NonNull
    private ProceduresRepository mProceduresRepository;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    @NonNull
    private String mProcedureID;

    public ProcedureDetailPresenter(@NonNull String procedureId, @NonNull ProcedureDetailContract.View detailView,
                                    @NonNull ProceduresRepository repository,
                                    @NonNull BaseSchedulerProvider schedulerProvider) {
        this.mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null!");
        this.mProcedureDetailView = checkNotNull(detailView, "detailView cannot be null!");
        this.mProceduresRepository = checkNotNull(repository, "repository cannot be null!");
        this.mProcedureID = checkNotNull(procedureId, "procedureId cannot be null!");
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        loadDetail();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadDetail() {

        if (Strings.isNullOrEmpty(mProcedureID)) {
            mProcedureDetailView.showInvalidProcedure();
            return;
        }

        mProcedureDetailView.showProgress(true);

        mCompositeDisposable.clear();

        Disposable disposable = mProceduresRepository
                .getProcedureDetail(mProcedureID)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<ProcedureDetail>() {
                    @Override
                    public void accept(ProcedureDetail detail) throws Exception {
                        if(null != detail) {
                            showDetail(detail);
                        }else {
                            mProcedureDetailView.showError("1");
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        mCompositeDisposable.add(disposable);
    }

    private void showDetail(ProcedureDetail detail) {
        mProcedureDetailView.showProgress(false);
        mProcedureDetailView.showCard(detail.getCard());
        mProcedureDetailView.showTitle(detail.getName());
        mProcedureDetailView.showPhases(detail.getPhases());

    }
}
