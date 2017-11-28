package com.onyx.proceduresapp.procedures;


import android.support.annotation.NonNull;

import com.onyx.proceduresapp.data.Procedure;
import com.onyx.proceduresapp.data.ProcedureDetail;

import java.util.List;

/**
 * Contracts specifies interaction between view and presenter
 */
public interface ProceduresContract {
    interface View {

        void showProgress(boolean active);

        void showProcedures(List<Procedure> procedures);

        void showPrecedureDetailUi(String procedureID);
    }

    interface Presenter {

        void loadProcedures(boolean forceUpdate);

        void openProceduresDetails(@NonNull ProcedureDetail detail);
    }
}
