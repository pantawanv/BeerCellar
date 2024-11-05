package com.example.beercellar.model

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.beercellar.repository.BeersRepository


class BeersViewModel : ViewModel() {
    private val repository = BeersRepository()
    val beersFlow: State<List<Beer>> = repository.beersFlow
    val errorMessageFlow: State<String> = repository.errorMessageFlow
    val reloadingFlow: State<Boolean> = repository.isLoadingBeers

    init {
        reload()
    }

    fun reload() {
        repository.getBeers()
    }

    fun add(beer: Beer) {
        repository.add(beer)
    }

    fun update(beerId: Int, beer: Beer) {
        repository.update(beerId, beer)
    }

    fun remove(beer: Beer) {
        repository.delete(beer.id)
    }

    // TODO sorting + filtering
    fun sortBeersByName(ascending: Boolean) {
        repository.sortBeersByName(ascending)
    }

    fun sortBeersByABV(ascending: Boolean) {
        repository.sortBeersByABV(ascending)
    }

    fun filterByName(nameFragment: String) {
        repository.filterByName(nameFragment)
    }

    fun filterByBrewery(breweryFragment: String) {
        repository.filterByBrewery(breweryFragment)
    }

}