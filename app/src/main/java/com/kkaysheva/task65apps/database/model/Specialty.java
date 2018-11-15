package com.kkaysheva.task65apps.database.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Specialty
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
@Entity(tableName = "specialty")
public class Specialty {

    @PrimaryKey
    @SerializedName("specialty_id")
    private Integer id;

    @SerializedName("name")
    private String name;

    public Specialty() {
    }

    @Ignore
    public Specialty(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + " " + name;
    }
}
