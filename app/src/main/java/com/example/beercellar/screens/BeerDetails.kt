package com.example.beercellar.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.beercellar.model.Beer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerDetails(
    beer: Beer,
    modifier: Modifier = Modifier,
    onUpdate: (Int, Beer) -> Unit = { id: Int, data: Beer -> },
    onNavigateBack: () -> Unit = {}
) {
    var user by remember { mutableStateOf(beer.user) }
    var brewery by remember { mutableStateOf(beer.brewery) }
    var name by remember { mutableStateOf(beer.name) }
    var style by remember { mutableStateOf(beer.style) }
    var abvStr by remember { mutableStateOf(beer.abv.toString()) }
    var volumeStr by remember { mutableStateOf(beer.volume.toString()) }
    var pictureUrl by remember { mutableStateOf(beer.pictureUrl) }
    var howManyStr by remember { mutableStateOf(beer.howMany.toString()) }

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Beer Details") })
        }) { innerPadding ->


        Column(modifier = modifier.padding(innerPadding)) {
            OutlinedTextField(onValueChange = { user = it },
                value = user,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "User") })

            OutlinedTextField(onValueChange = { brewery = it },
                value = brewery,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Brewery") })

            OutlinedTextField(onValueChange = { name = it },
                value = name,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Name") })

            OutlinedTextField(onValueChange = { style = it },
                value = style,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Style") })

            OutlinedTextField(onValueChange = { abvStr = it },
                value = abvStr,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "ABV") })

            OutlinedTextField(onValueChange = { volumeStr = it },
                value = volumeStr,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Volume") })

            OutlinedTextField(onValueChange = { pictureUrl = it },
                value = if (pictureUrl == null) "No Picture"
                else pictureUrl,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Picture") })


            OutlinedTextField(onValueChange = { howManyStr = it },
                value = howManyStr,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Amount") })

            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { onNavigateBack() }) {
                    Text("Back")
                }
                Button(onClick = {
                    val data = Beer(user = user, brewery = brewery, name = name, style = style,
                        abv = abvStr.toDouble(), volume = volumeStr.toDouble(), pictureUrl = pictureUrl,
                        howMany = howManyStr.toInt())
                    onUpdate(beer.id, data)
                    onNavigateBack()
                }) {
                    Text("Update")
                }
            }

        }


    }
}

@Preview
@Composable
fun BeerDetailsPreview() {
    BeerDetails(
        beer = Beer(user = "Test", brewery = "Test", name = "Test",
            style = "Test", abv = 10.0, volume = 33.5, pictureUrl = "Test",
            howMany = 1)
    )
}