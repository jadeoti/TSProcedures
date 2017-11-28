package com.onyx.proceduresapp.data.repository;


import com.onyx.proceduresapp.data.Procedure;

import java.util.List;

import io.reactivex.Observable;

public interface ProceduresRepository {
    Observable<List<Procedure>> getProcedures();

    void refreshProcedures();
}
