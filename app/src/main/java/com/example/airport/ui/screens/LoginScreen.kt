package com.example.airport.ui.screens

import com.example.airport.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.airport.data.repository.AuthRepository
import com.example.airport.ui.viewmodels.LoginResult
import com.example.airport.ui.viewmodels.LoginViewModel
import com.example.airport.utils.PreferenceHelper

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    authRepository: AuthRepository,
    preferenceHelper: PreferenceHelper
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    val viewModel: LoginViewModel = remember { LoginViewModel(authRepository, preferenceHelper) }

    // Kiểm tra tự động đăng nhập khi khởi động
    LaunchedEffect(Unit) {
        viewModel.autoLogin()
        if (viewModel.loginResult.value is LoginResult.Success) {
            onLoginSuccess()
        }
    }

    // Main column to center all elements
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Spacer(modifier = Modifier.height(64.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Email input field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),

            )
        Spacer(modifier = Modifier.height(8.dp))

        // Password input field with password masking
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Row for remember me checkbox and label
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it },
            )
            Text("Remember Password")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Login button
        val loginResult by viewModel.loginResult.collectAsState()
        Button(
            onClick = {
                viewModel.login(email, password, rememberMe)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("LOGIN")
        }

        // React to changes in loginResult
        LaunchedEffect(loginResult) {
            if (loginResult is LoginResult.Success) {
                onLoginSuccess()
            }
        }

        // Display error message if any
        when (loginResult) {
            is LoginResult.Failure -> {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = (loginResult as LoginResult.Failure).error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            else -> {} // Do nothing for Success or Initial state
        }
    }
}