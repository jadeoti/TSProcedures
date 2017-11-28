package com.onyx.proceduresapp.data.repository.datasource;


import com.onyx.proceduresapp.data.Procedure;
import com.onyx.proceduresapp.data.ProcedureDetail;
import com.onyx.proceduresapp.data.webservice.ApiClient;
import com.onyx.proceduresapp.data.webservice.ProcedureService;

import java.util.List;

import io.reactivex.Observable;

public class RemoteDataSource implements DataSource {
    // Create retrofit endpoints
    private ProcedureService service = new ApiClient().getService();

    @Override
    public Observable<List<Procedure>> getProcedures() {
        return service.fetchProcedures();
    }

    @Override
    public Observable<ProcedureDetail> getProcedureDetail(String procedureId) {
        return service.getProcedureDetail(procedureId);
    }
}
