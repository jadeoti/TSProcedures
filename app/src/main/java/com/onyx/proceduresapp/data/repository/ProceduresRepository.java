package com.onyx.proceduresapp.data.repository;


import com.google.common.base.Preconditions;
import com.onyx.proceduresapp.data.Procedure;
import com.onyx.proceduresapp.data.ProcedureDetail;
import com.onyx.proceduresapp.data.repository.datasource.DataSource;
import com.onyx.proceduresapp.data.repository.datasource.LocalDataSource;
import com.onyx.proceduresapp.data.repository.datasource.RemoteDataSource;

import java.util.List;

import io.reactivex.Observable;

public class ProceduresRepository implements DataSource{

    private LocalDataSource mLocalDataSource;

    private RemoteDataSource mRemoteDataSource;

    public ProceduresRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = Preconditions.checkNotNull(remoteDataSource, "remote datasource cannot be null");
    }

    @Override
    public Observable<List<Procedure>> getProcedures() {
        // We are only using remote for now
        return mRemoteDataSource.getProcedures();
    }

    @Override
    public Observable<ProcedureDetail> getProcedureDetail(String procedureId) {
        return mRemoteDataSource.getProcedureDetail(procedureId);
    }

    public void refreshProcedures(){

    }
}
