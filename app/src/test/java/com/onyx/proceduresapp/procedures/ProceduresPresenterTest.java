package com.onyx.proceduresapp.procedures;


import com.google.common.collect.Lists;
import com.onyx.proceduresapp.data.Procedure;
import com.onyx.proceduresapp.data.repository.ProceduresRepository;
import com.onyx.proceduresapp.util.schedulers.BaseSchedulerProvider;
import com.onyx.proceduresapp.util.schedulers.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProceduresPresenterTest {

    private static List<Procedure> PROCEDURES;
    private static List<Procedure> EMPTY_PROCEEDURES = new ArrayList<>(0);

    @Mock
    private ProceduresRepository mProceduresRepository;

    @Mock
    private ProceduresContract.View mProceduresView;

    private BaseSchedulerProvider mSchedulerProvider;


    private ProceduresPresenter mProceduresPresenter;


    @Before
    public void setupProceduresPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Make the sure that all schedulers are immediate.
        mSchedulerProvider = new ImmediateSchedulerProvider();

        // Get a reference to the class under test
        mProceduresPresenter = new ProceduresPresenter(mSchedulerProvider, mProceduresView, mProceduresRepository);

        // We subscribe the procedures to 3, with one active and two completed
        PROCEDURES = Lists.newArrayList(new Procedure("procedure-TSC_PosteriorApproachHip",
                        "Posterior Approach to the Hip", "https://s3-eu-west-1.amazonaws.com/media-touchsurgery-dev/media/modules/PosteriorApproachHip/icon/100.jpg"),
                new Procedure("procedure-TSC_LateralApproachHip",
                        "Lateral Approach to the Hip", "https://s3-eu-west-1.amazonaws.com/media-touchsurgery-dev/media/procedure/12/icon/icon.jpg"));
    }


    @Test
    public void load_all_procedures_from_repository_into_view() {
        // Given an initialized ProceduresPresenter with initialized procedures
        when(mProceduresRepository.getProcedures()).thenReturn(Observable.just(PROCEDURES));
        // When loading of Procedures is requested
        mProceduresPresenter.loadProcedures(true);

        // Then progress indicator is shown
        verify(mProceduresView).showProgress(true);
        // Then progress indicator is hidden and all procedures are shown in UI
        verify(mProceduresView).showProgress(false);
    }


    @Test
    public void click_on_procedure_shows_detail_ui() {
        // Given a stubbed procedure
        Procedure requestedProcedure =   new Procedure("procedure-TSC_LateralApproachHip",
                "Lateral Approach to the Hip", "https://s3-eu-west-1.amazonaws.com/media-touchsurgery-dev/media/procedure/12/icon/icon.jpg");

        // When open procedure details is requested
        mProceduresPresenter.openProceduresDetails(requestedProcedure);

        // Then procedure detail UI is shown
        verify(mProceduresView).showPrecedureDetailUi(any(String.class));
    }

}
