package com.kkaysheva.task65apps.network;

import com.kkaysheva.task65apps.database.model.PersonList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Gitlab65AppsApi
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public interface Gitlab65AppsApi {

    @GET("testTask.json")
    Call<PersonList> getPersonList();
}
