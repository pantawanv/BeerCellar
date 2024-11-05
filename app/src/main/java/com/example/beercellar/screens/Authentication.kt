package com.example.beercellar.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.google.firebase.auth.FirebaseUser
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beercellar.R


@Composable
fun Authentication(
    user: FirebaseUser? = null,
    message: String,
    signIn: (email: String, password: String) -> Unit = { _, _ -> },
    register: (email: String, password: String) -> Unit = { _, _ -> },
    navigateToBeerList: () -> Unit = {}

) {
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    val SageGreen = Color(0xFFa4b5ca)

    if (user != null && !isLoading) {
        LaunchedEffect(Unit) {
            isLoading = true
            navigateToBeerList()
        }
    }
    val emailStart = ""
    val passwordStart = ""
    var email by remember { mutableStateOf(emailStart)}
    var password by remember { mutableStateOf(passwordStart)}
    var emailIsError by remember { mutableStateOf(false)}
    var passwordIsError by remember { mutableStateOf(false )}
    var showPassword by remember { mutableStateOf(false)}



    Scaffold (
        containerColor = SageGreen
    )

    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ){
           Image(
               painter = painterResource(id = R.drawable.toast),
               contentDescription = "Beer Image",
               modifier = Modifier
                   .size(150.dp)
                   .padding(bottom = 16.dp)
               )

            Text(
                text = "Welcome to Beer Buddy",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 30.sp
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(75.dp)
                    .padding(bottom = 15.dp),
                value = email,
                onValueChange = {email = it},
                label = { Text("Email")},
                isError = emailIsError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),

            )
            if (emailIsError) {
                Text("Invalid email", color = MaterialTheme.colorScheme.error)
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(75.dp)
                    .padding(bottom = 15.dp),
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation =
                if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                isError = passwordIsError,
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        if (showPassword) {
                            Icon(Icons.Filled.Visibility, contentDescription = "Hide password")
                            // icons: large packet of icons loaded in gradle file
                        } else {
                            Icon(Icons.Filled.VisibilityOff, contentDescription = "Show password")
                        }
                    }
                }
            )
            if (passwordIsError) {
                Text("Invalid password", color = MaterialTheme.colorScheme.error)
            }
            if (message.isNotEmpty()) {
                Text(message, color = MaterialTheme.colorScheme.error)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        register(email, password)
                        showSuccessDialog = true
                    }
                },
                    modifier = Modifier.padding(end = 9.dp)
                    ) {
                    Text("Register")
                }
                Button(onClick = {
                    email = email.trim()
                    if (email.isEmpty() || !validateEmail(email)) {
                        emailIsError = true
                        return@Button
                    } else {
                        emailIsError = false
                    }
                    password = password.trim()
                    if (password.isEmpty()) {
                        passwordIsError = true
                        return@Button
                    } else {
                        passwordIsError = false
                    }
                    signIn(email, password)
                }) {
                    Text("Sign in")
                }
            }
        }
    }
    if (showSuccessDialog && message.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false},
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        navigateToBeerList()
                    }
                ) {
                    Text("OK")
                }
            },
            title = { Text(message) }
            )
    }
}


private fun validateEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

