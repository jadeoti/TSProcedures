package com.onyx.proceduresapp.data.webservice;

import com.onyx.proceduresapp.data.Procedure;
import com.onyx.proceduresapp.data.ProcedureDetail;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Morph-Deji on 27-Nov-17.
 */

public interface ProcedureService {
    @GET("procedures")
    Observable<List<Procedure>> fetchProcedures();


    @GET("procedure_details/{id}")
    Observable<ProcedureDetail> getProcedureDetail(@Path("id") String procedureID);
}
