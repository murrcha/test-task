package com.kkaysheva.task65apps.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kkaysheva.task65apps.database.model.Specialty;

import java.util.Collection;
import java.util.List;

/**
 * SpecialtyDao
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
@Dao
public interface SpecialtyDao {

    @Query("SELECT * FROM specialty")
    LiveData<List<Specialty>> getAll();

    @Insert
    void insertAll(Collection<Specialty> specialties);

    @Query("DELETE FROM specialty")
    void deleteAll();
}
