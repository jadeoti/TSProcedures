package com.onyx.proceduresapp.procedures;


import android.support.annotation.NonNull;

import com.onyx.proceduresapp.data.Procedure;

import java.util.List;

/**
 * Contracts specficies interaction between view and presenter
 */
public interface ProceduresContract {
    interface View {

        void showProgress(boolean active);

        void showProcedures(List<Procedure> procedures);

        void showPrecedureDetailUi(String procedureID);
    }

    interface UserActionsListener {

        void loadProcedures(boolean forceUpdate);

        void openProceduresDetails(@NonNull Procedure procedure);
    }
}
