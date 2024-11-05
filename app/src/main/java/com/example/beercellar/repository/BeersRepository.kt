package com.example.beercellar.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.beercellar.model.Beer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BeersRepository {
    private val baseUrl = "https://anbo-restbeer.azurewebsites.net/api/"

    private val beerStoreService: BeerStoreService
    val beersFlow: MutableState<List<Beer>> = mutableStateOf(listOf())
    val isLoadingBeers = mutableStateOf(false)
    val errorMessageFlow = mutableStateOf("")


    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        beerStoreService = build.create(BeerStoreService::class.java)
        getBeers()
    }

    fun getBeers() {
        isLoadingBeers.value = true
        beerStoreService.getAllBeers().enqueue(object : Callback<List<Beer>> {
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                isLoadingBeers.value = false
                if (response.isSuccessful) {
                    val beerList: List<Beer>? = response.body()
                    beersFlow.value = beerList ?: emptyList()
                    errorMessageFlow.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                isLoadingBeers.value = false
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
                Log.d("APPLE", message)
            }
        })
    }

    fun add(beer: Beer) {
        beerStoreService.saveBeer(beer).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Added: " + response.body())
                    getBeers()
                    errorMessageFlow.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
                Log.d("APPLE", message)
            }

        })
    }

    fun delete(id: Int) {
        Log.d("APPLE", "Delete: $id")
        beerStoreService.deleteBeer(id).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Delete: " + response.body())
                    errorMessageFlow.value = ""
                    getBeers()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                    Log.d("APPLE", "Not deleted: $message")
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
                Log.d("APPLE", "Not deleted $message")
            }

        })
    }

    fun update(beerId: Int, beer: Beer) {
        Log.d("APPLE", "Update: $beerId $beer")
        beerStoreService.updateBeer(beerId, beer).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Updated: " + response.body())
                    errorMessageFlow.value = ""
                    Log.d("APPLE", "Update Successful")
                    getBeers()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                    Log.d("APPLE", "Update $message")
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
                Log.d("APPLE", "Update $message")
            }
        })
    }

    fun sortBeersByName(ascending: Boolean) {
        Log.d("APPLE", "Sort by name")
        if (ascending)
            beersFlow.value = beersFlow.value.sortedBy { it.name }
        else
            beersFlow.value = beersFlow.value.sortedByDescending { it.name }
    }

    fun sortBeersByABV(ascending: Boolean) {
        Log.d("APPLE", "Sort by ABV")
        if (ascending)
            beersFlow.value = beersFlow.value.sortedBy { it.abv }
        else
            beersFlow.value = beersFlow.value.sortedByDescending { it.abv }
    }

    fun filterByName(nameFragment: String) {
        if (nameFragment.isEmpty()) {
            getBeers()
            return
        }
        beersFlow.value =
            beersFlow.value.filter {
                it.name.contains(nameFragment, ignoreCase = true)
            }
    }

    fun filterByBrewery(breweryFragment: String) {
        if (breweryFragment.isEmpty()) {
            getBeers()
            return
        }
        beersFlow.value =
            beersFlow.value.filter {
                it.brewery.contains(breweryFragment, ignoreCase = true)
            }
    }




}
















