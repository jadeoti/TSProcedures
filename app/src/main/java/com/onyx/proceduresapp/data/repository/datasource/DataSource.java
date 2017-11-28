package com.onyx.proceduresapp.data.repository.datasource;

import com.onyx.proceduresapp.data.Procedure;
import com.onyx.proceduresapp.data.ProcedureDetail;

import java.util.List;

import io.reactivex.Observable;

public interface DataSource {
    Observable<List<Procedure>> getProcedures();

    Observable<ProcedureDetail> getProcedureDetail(String procedureId);
}
