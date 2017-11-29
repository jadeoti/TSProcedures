package com.onyx.proceduresapp.procedures;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onyx.proceduresapp.R;
import com.onyx.proceduresapp.data.Procedure;
import com.onyx.proceduresapp.data.repository.ProceduresRepository;
import com.onyx.proceduresapp.data.repository.datasource.RemoteDataSource;
import com.onyx.proceduresapp.util.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class ProceduresFragment extends Fragment implements ProceduresContract.View{

    private ProceduresPresenter mPresenter;

    private ProceduresAdapter mListAdapter;

    public ProceduresFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new ProceduresAdapter(new ArrayList<Procedure>(0), mItemListener);
        mPresenter = new ProceduresPresenter(SchedulerProvider.getInstance(), this, new ProceduresRepository(null, new RemoteDataSource()));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_procedures, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.procedures_list);
        recyclerView.setAdapter(mListAdapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Pull-to-refresh
        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadProcedures(true);
            }
        });
        return root;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showProgress(boolean active) {

        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(active);

        }

    }

    @Override
    public void showProcedures(List<Procedure> procedures) {
        mListAdapter.replaceData(procedures);
        System.out.println("showing procedures");

    }

    @Override
    public void showPrecedureDetailUi(String procedureID) {
//        Intent intent = new Intent(getContext(), LessonDetailActivity.class);
//        intent.putExtra(LessonDetailActivity.EXTRA_LESSON_ID, lesson.getId());
//        startActivity(intent);

    }

    @Override
    public void showEmpty() {
        System.out.println("showing empty");

    }

    public static Fragment newInstance() {
        return new ProceduresFragment();
    }


    private static class ProceduresAdapter extends RecyclerView.Adapter<ProceduresAdapter.ViewHolder> {

        private List<Procedure> mProcedures;
        private ProcedureItemListener mItemListener;

        ProceduresAdapter(List<Procedure> lessons, ProcedureItemListener itemListener) {
            setList(lessons);
            mItemListener = itemListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.item_procedure, parent, false);

            return new ViewHolder(noteView, mItemListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Procedure procedure = mProcedures.get(position);

            viewHolder.nameView.setText(procedure.getName());
            Glide.with(viewHolder.itemView.getContext()).load(procedure.getIcon()).into(viewHolder.imageView);

        }

        public void replaceData(List<Procedure> procedures) {
            setList(procedures);
            notifyDataSetChanged();
        }

        private void setList(List<Procedure> procedures) {
            mProcedures = checkNotNull(procedures);
        }

        @Override
        public int getItemCount() {
            return mProcedures.size();
        }

        Procedure getItem(int position) {
            return mProcedures.get(position);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView nameView;
            ImageView imageView;
            private ProcedureItemListener mItemListener;

            ViewHolder(View itemView, ProcedureItemListener listener) {
                super(itemView);
                mItemListener = listener;
                nameView = (TextView) itemView.findViewById(R.id.procedure_name);
                imageView = (ImageView) itemView.findViewById(R.id.procedure_image);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Procedure less = getItem(position);
                mItemListener.onProcedureClick(less);

            }
        }
    }

    public interface ProcedureItemListener {

        void onProcedureClick(Procedure clickedProcedure);
    }





    /**
     * Listener for clicks on procedures in the RecyclerView.
     */
    ProcedureItemListener mItemListener = new ProcedureItemListener() {
        @Override
        public void onProcedureClick(Procedure clickedProcedure) {
            mPresenter.openProceduresDetails(clickedProcedure);
        }

    };

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }
}
