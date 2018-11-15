package com.kkaysheva.task65apps.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kkaysheva.task65apps.database.PersonRepository;
import com.kkaysheva.task65apps.database.model.Person;
import com.kkaysheva.task65apps.database.model.Specialty;

import java.util.List;

/**
 * PersonViewModel
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class PersonViewModel extends AndroidViewModel{

    private static final String TAG = PersonViewModel.class.getSimpleName();

    private PersonRepository repository;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        repository = new PersonRepository(application);
    }

    public void loadRemoteData() {
        repository.fetchRemoteData();
    }

    public LiveData<List<Specialty>> getAllSpecialty() {
        return repository.getAllSpecialty();
    }

    public LiveData<List<Person>> getPersonBySpecialty(int id) {
        return repository.getPersonBySpecialty(id);
    }

    public LiveData<List<Specialty>> getSpecialtyByPerson(int id) {
        return repository.getSpecialtyByPerson(id);
    }
}
