package com.onyx.proceduresapp.data.repository.datasource;


import com.onyx.proceduresapp.data.Procedure;
import com.onyx.proceduresapp.data.ProcedureDetail;

import java.util.List;

import io.reactivex.Observable;

public class LocalDataSource implements DataSource {
    @Override
    public Observable<List<Procedure>> getProcedures() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<ProcedureDetail> getProcedureDetail(String procedureId) {
        throw new UnsupportedOperationException();
    }

}
