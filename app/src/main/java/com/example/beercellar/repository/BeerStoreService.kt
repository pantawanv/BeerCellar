package com.example.beercellar.repository

import com.example.beercellar.model.Beer
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface BeerStoreService {
    @GET("beers")
    fun getAllBeers(): Call<List<Beer>>

    @GET("beers/{beerId}")
    fun getBeerById(@Path("beerId") beerId: Int): Call<Beer>

    @POST("beers")
    fun saveBeer(@Body beer: Beer): Call<Beer>

    @DELETE("beers/{id}")
    fun deleteBeer(@Path("id") id: Int): Call<Beer>

    @PUT("beers/{id}")
    fun updateBeer(@Path("id") id: Int, @Body beer: Beer): Call<Beer>

}
