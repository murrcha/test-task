package com.kkaysheva.task65apps;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kkaysheva.task65apps.adapter.PersonAdapter;
import com.kkaysheva.task65apps.database.model.Person;
import com.kkaysheva.task65apps.viewmodel.PersonViewModel;

import java.util.List;

/**
 * PersonFragment
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class PersonFragment extends Fragment {

    public static final String SPECIALTY_ID = "specialty_id";

    private static final String TAG = PersonFragment.class.getSimpleName();

    private PersonAdapter adapter;
    private int specialtyId;

    private CLickPersonCallback callback;

    public interface CLickPersonCallback {
        void personClicked(Person person);
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.person_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new PersonAdapter();
        adapter.setOnClickListener(new PersonAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Person person) {
                callback.personClicked(person);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            specialtyId = savedInstanceState.getInt(SPECIALTY_ID);
        }
        if (getArguments() != null) {
            specialtyId = getArguments().getInt(SPECIALTY_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_list_fragment, container, false);
        initRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PersonViewModel personViewModel = ViewModelProviders.of(getActivity()).get(PersonViewModel.class);
        personViewModel.getPersonBySpecialty(specialtyId).observe(getViewLifecycleOwner(), new Observer<List<Person>>() {
            @Override
            public void onChanged(@Nullable List<Person> persons) {
                if (persons == null || persons.isEmpty()) {
                    Log.e(TAG, "Person data is null or empty");
                } else {
                    adapter.clearItems();
                    adapter.setItems(persons);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SPECIALTY_ID, specialtyId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (CLickPersonCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
