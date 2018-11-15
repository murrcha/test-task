package com.kkaysheva.task65apps.database.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Person
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
@Entity(tableName = "person")
public class Person implements Serializable {

    @PrimaryKey
    private Integer id;

    @SerializedName("f_name")
    private String firstName;

    @SerializedName("l_name")
    private String lastName;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("avatr_url")
    private String avatarUrl;

    @Ignore
    @SerializedName("specialty")
    private List<Specialty> specialities = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<Specialty> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Specialty> specialities) {
        this.specialities = specialities;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
