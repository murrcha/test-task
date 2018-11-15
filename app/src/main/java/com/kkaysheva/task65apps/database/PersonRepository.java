package com.kkaysheva.task65apps.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.kkaysheva.task65apps.network.Gitlab65AppsApi;
import com.kkaysheva.task65apps.network.RetrofitInstance;
import com.kkaysheva.task65apps.database.model.Person;
import com.kkaysheva.task65apps.database.model.PersonList;
import com.kkaysheva.task65apps.database.model.PersonSpecialtyJoin;
import com.kkaysheva.task65apps.database.model.Specialty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * PersonRepository
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class PersonRepository {

    private static final String TAG = PersonRepository.class.getSimpleName();

    private PersonDao personDao;
    private SpecialtyDao specialtyDao;
    private PersonSpecialtyDao personSpecialtyDao;

    private LiveData<List<Person>> allPerson;
    private LiveData<List<Specialty>> allSpecialty;

    public PersonRepository(Application application) {
        PersonDatabase database = PersonDatabase.getInstance(application);
        personDao = database.getPersonDao();
        specialtyDao = database.getSpecialtyDao();
        personSpecialtyDao = database.getPersonSpecialtyDao();
        fetchRemoteData();
        if (allPerson == null) {
            allPerson = personDao.getAll();
        }
        if (allSpecialty == null) {
            allSpecialty = specialtyDao.getAll();
        }
    }

    public void fetchRemoteData() {
        Gitlab65AppsApi api = RetrofitInstance.getInstance().getApi();
        Call<PersonList> call = api.getPersonList();
        Log.d(TAG, String.format("Url called : %s", call.request().url()));
        call.enqueue(new Callback<PersonList>() {

            @Override
            public void onResponse(Call<PersonList> call, Response<PersonList> response) {
                List<Person> persons = response.body().getPersons();
                if (persons != null && !persons.isEmpty()) {
                    Log.d(TAG, "Fetch remote data :");
                    Log.d(TAG, String.format("Person index 0 : %s", persons.get(0).toString()));
                    putData(persons);
                } else {
                    Log.e(TAG, "No persons data");
                }
            }

            @Override
            public void onFailure(Call<PersonList> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t.fillInStackTrace());
            }
        });
    }

    public void putData(List<Person> persons) {
        new PutDataAsyncTask(personDao, specialtyDao, personSpecialtyDao).execute(persons);
    }

    public LiveData<List<Person>> getAllPersons() {
        return this.allPerson;
    }

    public LiveData<List<Specialty>> getAllSpecialty() {
        return this.allSpecialty;
    }

    public LiveData<List<Person>> getPersonBySpecialty(int id) {
        return personSpecialtyDao.getPersonBySpecialty(id);
    }

    public LiveData<List<Specialty>> getSpecialtyByPerson(int id) {
        return personSpecialtyDao.getSpecialtyByPerson(id);
    }

    private static class PutDataAsyncTask extends AsyncTask<List<Person>, Void, Void> {
        private PersonDao personDao;
        private SpecialtyDao specialtyDao;
        private PersonSpecialtyDao personSpecialtyDao;

        public PutDataAsyncTask(PersonDao personDao, SpecialtyDao specialtyDao, PersonSpecialtyDao personSpecialtyDao) {
            this.personDao = personDao;
            this.specialtyDao = specialtyDao;
            this.personSpecialtyDao = personSpecialtyDao;
        }

        @Override
        protected Void doInBackground(List<Person>... persons) {
            personSpecialtyDao.deleteAll();
            personDao.deleteAll();
            specialtyDao.deleteAll();
            List<PersonSpecialtyJoin> personSpecialtyJoins = new ArrayList<>();
            HashMap<Integer, Specialty> specialities = new HashMap<>();
            Integer countId = 1;
            for (Person person : persons[0]) {
                person.setId(countId++);
                if (person.getSpecialities() != null) {
                    for (Specialty specialty : person.getSpecialities()) {
                        personSpecialtyJoins.add(new PersonSpecialtyJoin(person.getId(), specialty.getId()));
                        specialities.put(specialty.getId(), specialty);
                    }
                }
            }
            personDao.insertAll(persons[0]);
            specialtyDao.insertAll(specialities.values());
            personSpecialtyDao.insertAll(personSpecialtyJoins);
            return null;
        }
    }
}
