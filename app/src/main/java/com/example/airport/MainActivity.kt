package com.example.airport

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.airport.ui.theme.AirportTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AirportTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreenDemo()
                }
            }
        }
    }
}

@Composable
fun LoginScreenDemo() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LoginScreen(
        username = username,
        onUsernameChange = { username = it },
        password = password,
        onPasswordChange = { password = it },
        rememberMe = rememberMe,
        onRememberMeChange = { rememberMe = it },
        onLoginClick = {
            if (username.isEmpty() || password.isEmpty()) {
                error = "Please enter username and password"
            } else {
                error = null  // Xử lý đăng nhập ở đây
            }
        },
        error = error
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    AirportTheme {
        LoginScreenDemo()
    }
}