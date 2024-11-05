package com.example.beercellar.screens

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.beercellar.model.Beer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerAdd(
    modifier: Modifier = Modifier,
    addBeer: (Beer) -> Unit = {},
    navigateBack: () -> Unit = {}
) {
    var user by remember { mutableStateOf("") }
    var brewery by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var style by remember { mutableStateOf("") }
    var abvStr by remember { mutableStateOf("") }
    var volumeStr by remember { mutableStateOf("") }
    var pictureUrl by remember { mutableStateOf("") }
    var howManyStr by remember { mutableStateOf("") }

    var userIsError by remember { mutableStateOf(false) }
    var breweryIsError by remember { mutableStateOf(false) }
    var nameIsError by remember { mutableStateOf(false) }
    var styleIsError by remember { mutableStateOf(false) }
    var abvIsError by remember { mutableStateOf(false) }
    var volumeIsError by remember { mutableStateOf(false) }
    var pictureUrlIsError by remember { mutableStateOf(false) }
    var howManyIsError by remember { mutableStateOf(false) }

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Add a beer") })
        }) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            // TODO show error message
            val orientation = LocalConfiguration.current.orientation
            val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
            // TODO refactor duplicated code: component MyTextField?
            if (isPortrait) {
                OutlinedTextField(onValueChange = { user = it },
                    value = user,
                    isError = userIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "User") })

                OutlinedTextField(onValueChange = { brewery = it },
                    value = brewery,
                    isError = breweryIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Brewery") })

                OutlinedTextField(onValueChange = { name = it },
                    value = name,
                    isError = nameIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Name") })

                OutlinedTextField(onValueChange = { style = it },
                    value = style,
                    isError = styleIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Style") })

                OutlinedTextField(onValueChange = { abvStr = it },
                    value = abvStr,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = abvIsError,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "ABV") })

                OutlinedTextField(onValueChange = { volumeStr = it },
                    value = volumeStr,
                    isError = volumeIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Volume") })

                OutlinedTextField(onValueChange = { pictureUrl = it },
                    value = pictureUrl,
                    isError = pictureUrlIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Picture") })

                OutlinedTextField(onValueChange = { howManyStr = it },
                    value = howManyStr,
                    isError = howManyIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Amount") })

            } else {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedTextField(onValueChange = { user = it },
                        value = user,
                        isError = userIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.weight(1f),
                        label = { Text(text = "User") })

                    OutlinedTextField(onValueChange = { brewery = it },
                        value = brewery,
                        isError = breweryIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.weight(1f),
                        label = { Text(text = "Brewery") })

                    OutlinedTextField(onValueChange = { name = it },
                        value = name,
                        isError = nameIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.weight(1f),
                        label = { Text(text = "Name") })

                    OutlinedTextField(onValueChange = { style = it },
                        value = style,
                        isError =styleIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.weight(1f),
                        label = { Text(text = "Style") })

                    OutlinedTextField(onValueChange = { abvStr = it },
                        value = abvStr,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = abvIsError,
                        modifier = Modifier.weight(1f),
                        label = { Text(text = "ABV") })

                    OutlinedTextField(onValueChange = { volumeStr = it },
                        value = volumeStr,
                        isError = volumeIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        label = { Text(text = "Volume") })

                    OutlinedTextField(onValueChange = { pictureUrl = it },
                        value = pictureUrl,
                        isError = pictureUrlIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.weight(1f),
                        label = { Text(text = "Picture") })

                    OutlinedTextField(onValueChange = { howManyStr = it },
                        value = howManyStr,
                        isError = howManyIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        label = { Text(text = "Amount") })
                }
            }
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { navigateBack() }) {
                    Text("Back")
                }
                Button(onClick = {
                    if (user.isEmpty()) {
                        userIsError = true
                        return@Button
                    }
                    if (brewery.isEmpty()) {
                        breweryIsError = true
                        return@Button
                    }
                    if (name.isEmpty()) {
                        nameIsError = true
                        return@Button
                    }
                    if (style.isEmpty()) {
                        styleIsError = true
                        return@Button
                    }
                    if (abvStr.isEmpty()) {
                        abvIsError = true
                        return@Button
                    }
                    val abv = abvStr.toDoubleOrNull()
                    if (abv == null) {
                        abvIsError = true
                        return@Button
                    }
                    if (volumeStr.isEmpty()) {
                        volumeIsError = true
                        return@Button
                    }
                    val volume = volumeStr.toDoubleOrNull()
                    if (volume == null) {
                        volumeIsError = true
                        return@Button
                    }
                    if (pictureUrl.isEmpty()) {
                        pictureUrlIsError = true
                        return@Button
                    }
                    if (howManyStr.isEmpty()) {
                        howManyIsError = true
                        return@Button
                    }
                    val howMany = howManyStr.toIntOrNull()
                    if (howMany == null) {
                        howManyIsError = true
                        return@Button
                    }

                    val beer = Beer(user = user, brewery = brewery, name = name,
                        style = style, abv = abv, volume = volume, pictureUrl = pictureUrl,
                        howMany  = howMany)

                    addBeer(beer)
                    navigateBack()
                }) {
                    Text("Add")
                }
            }
        }
    }
}

@Preview
@Composable
fun BeerAddPreview() {
    BeerAdd()
}