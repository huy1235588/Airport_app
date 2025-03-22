package com.example.airport

import LoginScreen
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.airport.ui.theme.AirportTheme
import io.github.cdimascio.dotenv.Dotenv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager

// Dotenv
private val dotenv = Dotenv.configure()
    .directory("/assets")
    .filename("env")
    .load()

// Kết nối đến cơ sở dữ liệu SQL Server
private val CONNECTION_STRING = dotenv["CONNECTION_STRING"]

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

/**
 * Hàm kết nối đến cơ sở dữ liệu
 * @return Connection? Kết nối đến cơ sở dữ liệu, nếu kết nối thành công trả về Connection, ngược lại trả về null
 */
private fun connectToDatabase(): Connection? {
    return try {
        DriverManager.getConnection(CONNECTION_STRING).also {
            Log.i("MainActivity", "Connected to database")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("MainActivity", "Failed to connect to database")
        null
    }
}

/**
 * Hàm kiểm tra thông tin đăng nhập
 * @param username Tên đăng nhập
 * @param password Mật khẩu
 * @return true nếu thông tin đăng nhập hợp lệ, ngược lại trả về false
 */
suspend fun login(username: String, password: String): Boolean = withContext(Dispatchers.IO) {
    var isValid = false
    connectToDatabase()?.use { connection ->
        val query = "SELECT 1 FROM Users WHERE email = ? AND password = ?"
        connection.prepareStatement(query).use { statement ->
            statement.setString(1, username)
            statement.setString(2, password)
            statement.executeQuery().use { resultSet ->
                isValid = resultSet.next()
            }
        }
    }
    isValid
}


@Composable
fun LoginScreenDemo() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

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
                error = null
                // Thực hiện đăng nhập
                coroutineScope.launch {
                    val isValid = login(username, password)
                    if (isValid) {
                        error = "Đăng nhập thành công"

                    } else {
                        error = "Invalid username or password"
                    }
                }
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