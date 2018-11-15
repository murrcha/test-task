package com.kkaysheva.task65apps.database.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * PersonList
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class PersonList {

    @SerializedName("response")
    private List<Person> persons = null;

    public PersonList() {

    }

    public PersonList(List<Person> persons) {
        this.persons = persons;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
