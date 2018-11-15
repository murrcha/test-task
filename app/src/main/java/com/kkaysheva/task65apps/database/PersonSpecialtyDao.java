package com.kkaysheva.task65apps.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kkaysheva.task65apps.database.model.Person;
import com.kkaysheva.task65apps.database.model.PersonSpecialtyJoin;
import com.kkaysheva.task65apps.database.model.Specialty;

import java.util.List;

/**
 * PersonSpecialtyDao
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
@Dao
public interface PersonSpecialtyDao {

    @Query("SELECT person.* "
            + "FROM person "
            + "INNER JOIN person_specialty_join "
            + "ON person.id == person_specialty_join.idPerson "
            + "WHERE person_specialty_join.idSpecialty == :idSpecialty")
    LiveData<List<Person>> getPersonBySpecialty(final int idSpecialty);

    @Query("SELECT specialty.* "
            + "FROM specialty "
            + "INNER JOIN person_specialty_join "
            + "ON specialty.id == person_specialty_join.idSpecialty "
            + "WHERE person_specialty_join.idPerson == :idSpecialist")
    LiveData<List<Specialty>> getSpecialtyByPerson(final int idSpecialist);

    @Insert
    void insertAll(List<PersonSpecialtyJoin> personSpecialtyJoins);

    @Query("DELETE FROM person_specialty_join")
    void deleteAll();
}
