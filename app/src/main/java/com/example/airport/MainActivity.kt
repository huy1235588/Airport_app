package com.example.airport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import com.example.airport.ui.theme.AirportTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.airport.data.db.DatabaseHelper
import com.example.airport.data.repository.AuthRepository
import com.example.airport.utils.PreferenceHelper

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AirportTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = this
                    val navController = rememberNavController()
                    val databaseHelper = DatabaseHelper()
                    val authRepository = AuthRepository(databaseHelper)
                    val preferenceHelper = PreferenceHelper(context)
                    NavigationGraph(navController, authRepository, preferenceHelper)
                }
            }
        }

    }
}