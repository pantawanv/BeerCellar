package com.example.beercellar.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beercellar.R
import com.example.beercellar.model.Beer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import okio.blackholeSink


@OptIn(ExperimentalMaterial3Api:: class)
@Composable
fun BeerList(
    beers: List<Beer>,
    errorMessage: String,
    modifier: Modifier = Modifier,
    onBeerSelected: (Beer) -> Unit = {},
    onBeerDeleted: (Beer) -> Unit = {},
    onAdd: () -> Unit = {},
    sortByName: (up: Boolean) -> Unit = {},
    sortByABV: (up: Boolean) -> Unit = {},
    filterByName: (String) -> Unit = {},
    filterByBrewery: (String) -> Unit = {},

    user: FirebaseUser? = null,
    signOut: () -> Unit = {},
    navigateToAuthentication: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("") },
                actions = {
                    IconButton(onClick = {onAdd() }) {
                        Icon(Icons.Filled.AddCircleOutline, contentDescription = "Add")

                    }
                    IconButton(onClick = { signOut() }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Log out")

                    }
                }

            )

        }

    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (user == null) {
                navigateToAuthentication()
            } else {
                Text(
                    text = "Welcome",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 38.sp
                )
                val userEmail = user.email ?: "unknown"
                val username = userEmail.substringBefore("@")

                Text(
                    text = username,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
            }


        BeerListPanel(
            beers = beers,
            modifier = Modifier.padding(innerPadding),
            errorMessage = errorMessage,
            sortByName = sortByName,
            sortByABV = sortByABV,
            onBeerSelected = onBeerSelected,
            onBeerDeleted = onBeerDeleted,
            onFilterByName = filterByName,
            onFilterByBrewery = filterByBrewery
        )

        }

}
}

@Composable
private fun BeerListPanel(
    beers: List<Beer>,
    modifier: Modifier = Modifier,
    errorMessage: String,
    sortByName: (up: Boolean) -> Unit,
    sortByABV: (up: Boolean) -> Unit,
    onBeerSelected: (Beer) -> Unit,
    onBeerDeleted: (Beer) -> Unit,
    onFilterByName: (String) -> Unit,
    onFilterByBrewery: (String) -> Unit

) {
    Column (modifier = modifier) {
        if (errorMessage.isNotEmpty()) {
            Text(text = "Problem: $errorMessage")
        }
        val nameUp = "Name \u2191"
        val nameDown = "Name \u2193"
        val abvUp = "ABV \u2191"
        val abvDown = "ABV \u2193"
        var sortNameAscending by remember { mutableStateOf(true) }
        var sortABVByAscending by remember { mutableStateOf(true) }
        var nameFragment by remember { mutableStateOf("")}
        var breweryFragment by remember { mutableStateOf("")}


        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = nameFragment,
                onValueChange = {nameFragment = it},
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("Search")},
                singleLine = true
            )
            IconButton(
                onClick = {
                    val queryParts = nameFragment.split(",").map { it.trim()}
                    val nameQuery = queryParts.getOrNull(0) ?: ""
                    val breweryQuery = queryParts.getOrNull(1) ?: ""

                    onFilterByName(nameQuery)
                    onFilterByBrewery(breweryQuery)
                },
                modifier = Modifier.size(56.dp)
            ) {

                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    modifier = Modifier.size(34.dp)
                )

            }
        }

        Row {
            TextButton(onClick = {
                sortByName(sortNameAscending)
                sortNameAscending = !sortNameAscending
            }) {
                Text(text = if (sortNameAscending) nameDown else nameUp)
            }
            TextButton(onClick = {
                sortByABV(sortABVByAscending)
                sortABVByAscending = !sortABVByAscending
            }) {
                Text(text = if (sortABVByAscending) abvDown else abvUp)
            }
        }
        val orientation = LocalConfiguration.current.orientation
        val columns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            //modifier = modifier.fillMaxSize()
        ) {
            items(beers) { beer ->
                BeerItem(
                    beer,
                    onBeerSelected = onBeerSelected,
                    onBeerDeleted = onBeerDeleted
                )

            }
        }

    }
}

@Composable
private fun BeerItem(
    beer: Beer,
    modifier: Modifier = Modifier,
    onBeerSelected: (Beer) -> Unit = {},
    onBeerDeleted: (Beer) -> Unit = {}
) {
    Card(modifier = modifier
        .padding(4.dp)
        .fillMaxSize(), onClick = { onBeerSelected(beer) }) {
        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                modifier = Modifier.padding(8.dp)
                    .weight(1f),
                text = "| " + beer.brewery + " | " + beer.name + " " +beer.abv.toString() + "%"

            )
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Remove",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onBeerDeleted(beer) }
            )
        }
    }
}



@Preview
@Composable
fun BeerListPreview() {
    BeerList(
        beers = listOf(
            Beer(user = "Test", brewery = "Test", name = "Test",
                style = "Test", abv = 10.0, volume = .5, pictureUrl = "Test",
                howMany = 1),
            Beer(user = "Test", brewery = "Test", name = "Test",
                style = "Test", abv = 10.0, volume = .5, pictureUrl = "Test",
                howMany = 1),

        ),
        errorMessage = "Some error message"
    )
}



















