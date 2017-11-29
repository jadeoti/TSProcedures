package com.onyx.proceduresapp.proceduredetails;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.onyx.proceduresapp.R;
import com.onyx.proceduresapp.data.Phase;
import com.onyx.proceduresapp.data.ProcedureDetail;
import com.onyx.proceduresapp.data.repository.ProceduresRepository;
import com.onyx.proceduresapp.data.repository.datasource.RemoteDataSource;
import com.onyx.proceduresapp.util.schedulers.SchedulerProvider;

import java.util.List;

/**
 * Implements Details View
 */
public class ProcedureDetailFragment extends Fragment implements ProcedureDetailContract.View{


    private static final String ARGUMENT_PROCEDURE_ID = "arg.proc.id";

    private ProcedureDetailPresenter mPresenter;

    private String mProcedureID;

    public ProcedureDetailFragment() {
        // Required empty public constructor
    }

    public static ProcedureDetailFragment newInstance(String procedureID) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_PROCEDURE_ID, procedureID);
        ProcedureDetailFragment fragment = new ProcedureDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if(args != null){
            mProcedureID = args.getString(ARGUMENT_PROCEDURE_ID);
        }
        mPresenter = new ProcedureDetailPresenter(mProcedureID, this,
                new ProceduresRepository(null, new RemoteDataSource()), // TODO: get them by injection
                SchedulerProvider.getInstance());

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_procedure_detail, container, false);
    }

    @Override
    public void showProgress(boolean active) {

    }

    @Override
    public void showDetail(ProcedureDetail procedureDetail) {

    }

    @Override
    public void showError(String type) {

    }

    @Override
    public void showCard(String cardUrl) {
        if(getView() != null) {
            ImageView imageView = (ImageView) getView().findViewById(R.id.card_image);
            Glide.with(this).load(cardUrl).into(imageView);
        }
    }

    @Override
    public void showTitle(String name) {
        if(getView() != null) {
            TextView textView = (TextView) getView().findViewById(R.id.procedure_name);
            textView.setText(name);
        }
    }

    @Override
    public void showPhases(List<Phase> phases) {
        if(getView() != null) {
            LinearLayout container = (LinearLayout) getView().findViewById(R.id.phases_container);
            for (Phase phase : phases) {
                // inflate phaseview and add to parent
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_phase, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.phase_icon);
                Glide.with(this).load(phase.getIcon()).into(imageView);

                TextView textView = (TextView) view.findViewById(R.id.phase_name);
                textView.setText(phase.getName());

                container.addView(view);

            }
        }

    }

    @Override
    public void showInvalidProcedure() {
        Toast.makeText(getContext(), R.string.invalid_proc, Toast.LENGTH_SHORT).show();

    }
}
