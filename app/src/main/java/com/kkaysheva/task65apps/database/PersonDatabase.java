package com.kkaysheva.task65apps.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kkaysheva.task65apps.database.model.Person;
import com.kkaysheva.task65apps.database.model.PersonSpecialtyJoin;
import com.kkaysheva.task65apps.database.model.Specialty;

/**
 * PersonDatabase
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
@Database(entities = {Person.class, Specialty.class, PersonSpecialtyJoin.class}, version = 1)
public abstract class PersonDatabase extends RoomDatabase {

    private static volatile PersonDatabase instance;

    public abstract PersonDao getPersonDao();
    public abstract SpecialtyDao getSpecialtyDao();
    public abstract PersonSpecialtyDao getPersonSpecialtyDao();

    public static PersonDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (PersonDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            PersonDatabase.class, "person.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
