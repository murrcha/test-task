package com.kkaysheva.task65apps;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.arch.lifecycle.ViewModelProviders;
import android.widget.TextView;

import com.kkaysheva.task65apps.adapter.SpecialtyAdapter;
import com.kkaysheva.task65apps.viewmodel.PersonViewModel;
import com.kkaysheva.task65apps.database.model.Specialty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * SpecialtyFragment
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class SpecialityFragment extends Fragment {

    private static final String TAG = SpecialityFragment.class.getSimpleName();

    @BindView(R.id.sync_button)
    FloatingActionButton syncButton;
    @BindView(R.id.error_load)
    TextView error_load;
    private Unbinder unbinder;
    private PersonViewModel personViewModel;
    private SpecialtyAdapter adapter;

    private CLickSpecialtyCallback callback;

    public interface CLickSpecialtyCallback {
        void specialtyClicked(int specialtyId);
    }

    private void initRecyclerView(final View view) {
        RecyclerView recyclerView = view.findViewById(R.id.specialities_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new SpecialtyAdapter();
        adapter.setOnClickListener(new SpecialtyAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int specialtyId) {
                callback.specialtyClicked(specialtyId);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.specialities_list_fragment, container, false);
        initRecyclerView(view);
        unbinder = ButterKnife.bind(this, view);
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personViewModel.loadRemoteData();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        personViewModel = ViewModelProviders.of(getActivity()).get(PersonViewModel.class);
        personViewModel.getAllSpecialty().observe(getViewLifecycleOwner(), new Observer<List<Specialty>>() {
            @Override
            public void onChanged(@Nullable List<Specialty> specialties) {
                if (specialties == null || specialties.isEmpty()) {
                    Log.e(TAG, "Specialty data is null or empty");
                    error_load.setVisibility(View.VISIBLE);
                } else {
                    error_load.setVisibility(View.GONE);
                    adapter.clearItems();
                    adapter.setItems(specialties);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (CLickSpecialtyCallback) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
