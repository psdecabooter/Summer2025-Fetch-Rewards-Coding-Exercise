package com.example.fetchrewardscodingexercise

import retrofit2.Call
import retrofit2.http.GET

/**
 * RetrofitAPI defines the http requests we will be doing
 */
interface RetrofitAPI {

    /**
     * Performs a GET request at the extension hiring.json
     *
     * @return a list of HiringListModals
     */
    @GET("hiring.json")
    fun getHiringData(): Call<ArrayList<HiringListModal>>
}