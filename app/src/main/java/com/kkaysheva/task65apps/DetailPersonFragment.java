package com.kkaysheva.task65apps;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kkaysheva.task65apps.database.model.Person;
import com.kkaysheva.task65apps.database.model.Specialty;
import com.kkaysheva.task65apps.utility.Utility;
import com.kkaysheva.task65apps.viewmodel.PersonViewModel;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * DetailPersonFragment
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class DetailPersonFragment extends Fragment {

    public static final String PERSON = "person";
    private static final String NO_DATA = "-";

    private Person person;

    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.first_name)
    TextView firstName;
    @BindView(R.id.last_name)
    TextView lastName;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.specialty)
    TextView specialty;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            person = (Person) savedInstanceState.getSerializable(PERSON);
        }
        if (getArguments() != null) {
            person = (Person) getArguments().getSerializable(PERSON);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_person_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        String urlPhoto = person.getAvatarUrl(); //no photo by url
        //urlPhoto = "https://avatars.mds.yandex.net/get-pdb/368827/dfc5122e-f0f6-44d7-b600-a03ae8c8bd5b/s1200";
        if (urlPhoto != null && !urlPhoto.trim().isEmpty()) {
            Picasso.get()
                    .load(urlPhoto)
                    .placeholder(R.drawable.ic_photo_holder)
                    .error(R.drawable.ic_photo_holder)
                    .into(photo);
        }
        firstName.setText(Utility.capitalizeString(person.getFirstName()));
        lastName.setText(Utility.capitalizeString(person.getLastName()));
        String stringDate = person.getBirthday();
        if (stringDate == null || stringDate.trim().isEmpty()) {
            birthday.setText(NO_DATA);
            age.setText(NO_DATA);
        } else {
            Date date = Utility.getDateFromString(stringDate);
            birthday.setText(Utility.getDateFormatOut(date));
            int personAge = Utility.getAge(date);
            age.setText(getResources().getQuantityString(R.plurals.plural_age, personAge, personAge));
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PersonViewModel personViewModel = ViewModelProviders.of(getActivity()).get(PersonViewModel.class);
        personViewModel.getSpecialtyByPerson(person.getId()).observe(getViewLifecycleOwner(), new Observer<List<Specialty>>() {
            @Override
            public void onChanged(@Nullable List<Specialty> specialties) {
                if (specialties == null || specialties.isEmpty()) {
                    specialty.setText(NO_DATA);
                } else {
                    for (Specialty s : specialties) {
                        specialty.append(s.getName());
                        specialty.append("\n");
                    }
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PERSON, person);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
