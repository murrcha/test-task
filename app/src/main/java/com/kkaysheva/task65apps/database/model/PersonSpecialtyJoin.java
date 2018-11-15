package com.kkaysheva.task65apps.database.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

/**
 * PersonSpecialtyJoin
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
@Entity(tableName = "person_specialty_join",
        primaryKeys = {"idPerson", "idSpecialty"},
        indices = {@Index(value = "idSpecialty")},
        foreignKeys = {
                @ForeignKey(entity = Person.class,
                        parentColumns = "id",
                        childColumns = "idPerson"),
                @ForeignKey(entity = Specialty.class,
                        parentColumns = "id",
                        childColumns = "idSpecialty")
        })
public class PersonSpecialtyJoin {

    public final int idPerson;
    public final int idSpecialty;

    public PersonSpecialtyJoin(final int idPerson, final int idSpecialty) {
        this.idPerson = idPerson;
        this.idSpecialty = idSpecialty;
    }
}
