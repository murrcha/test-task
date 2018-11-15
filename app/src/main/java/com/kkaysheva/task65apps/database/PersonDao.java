package com.kkaysheva.task65apps.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kkaysheva.task65apps.database.model.Person;

import java.util.Collection;
import java.util.List;

/**
 * PersonDao
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
@Dao
public interface PersonDao {

    @Query("SELECT * FROM Person")
    LiveData<List<Person>> getAll();

    @Insert
    void insertAll(Collection<Person> persons);

    @Query("DELETE FROM Person")
    void deleteAll();
}
