package com.onyx.proceduresapp.proceduredetail;

import com.google.common.collect.Lists;
import com.onyx.proceduresapp.data.Phase;
import com.onyx.proceduresapp.data.ProcedureDetail;
import com.onyx.proceduresapp.data.repository.ProceduresRepository;
import com.onyx.proceduresapp.proceduredetails.ProcedureDetailContract;
import com.onyx.proceduresapp.proceduredetails.ProcedureDetailPresenter;
import com.onyx.proceduresapp.util.schedulers.BaseSchedulerProvider;
import com.onyx.proceduresapp.util.schedulers.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProcedureDetailPresenterTest {
    private final Phase phaseOne = new Phase("Posterior Approach to the Hip",
            "https://s3-eu-west-1.amazonaws.com/media-touchsurgery-dev/media/modules/PosteriorApproachHip/icon/100.jpg");

    private ProcedureDetail mDetail;

    @Mock
    private ProceduresRepository mProceduresRepository;

    @Mock
    private ProcedureDetailContract.View mDetailView;

    private BaseSchedulerProvider mSchedulerProvider;


    private ProcedureDetailPresenter mProcedureDetailPresenter;


    @Before
    public void setupProceduresPresenter() {

        MockitoAnnotations.initMocks(this);

        // Make the sure that all schedulers are immediate.
        mSchedulerProvider = new ImmediateSchedulerProvider();

        List<Phase> phases = Lists.newArrayList(phaseOne);

        // We subscribe the details
        mDetail = new ProcedureDetail("procedure-TSC_PosteriorApproachHip",
                "Posterior Approach to the Hip",
                phases, "https://s3-eu-west-1.amazonaws.com/media-touchsurgery-dev/media/modules/PosteriorApproachHip/icon/100.jpg",
                "https://s3-eu-west-1.amazonaws.com/media-touchsurgery-dev/media/modules/PosteriorApproachHip/card/100.jpg");

    }

    @Test
    public void get_procedure_detail_and_load_into_view() {
        // When procedures presenter is asked to open a procedure detail
        // Get a reference to the class under test
        mProcedureDetailPresenter = new ProcedureDetailPresenter(mDetail.getId(), mDetailView, mProceduresRepository, mSchedulerProvider);

        setProcedureDetailAvailable(mDetail);

        mProcedureDetailPresenter.subscribe();

        // Then detail is loaded from model,
        // callback is captured
        // and progress indicator is shown
        verify(mProceduresRepository).getProcedureDetail(eq(mDetail.getId()));
        verify(mDetailView).showProgress(true);

        // Then progress indicator is hidden and title, description and completion status are shown
        // in UI
        verify(mDetailView).showProgress(false);
        verify(mDetailView).showCard(mDetail.getCard());
        verify(mDetailView).showTitle(mDetail.getName());
        verify(mDetailView).showPhases(mDetail.getPhases());
    }

    private void setProcedureDetailAvailable(ProcedureDetail procedureDetail) {
        when(mProceduresRepository.getProcedureDetail(eq(procedureDetail.getId())))
                .thenReturn(Observable.just(procedureDetail));
    }

}
