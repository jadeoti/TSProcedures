package com.onyx.proceduresapp.proceduredetails;

import com.onyx.proceduresapp.data.Phase;
import com.onyx.proceduresapp.data.ProcedureDetail;

import java.util.List;

public interface ProcedureDetailContract {
    interface View{
        void showProgress(boolean active);

        void showDetail(ProcedureDetail procedureDetail);

        void showError(String type);

        void showCard(String cardUrl);

        void showTitle(String name);

        void showPhases(List<Phase> phases);

        void showInvalidProcedure();
    }

    interface ProcedureDetailPresenter{
        void loadDetail();

        void subscribe();

        void unsubscribe();

    }
}
