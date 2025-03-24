package com.example.airport.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airport.data.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var savePassword by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun login() {
        viewModelScope.launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    authRepository.login(email, password)
                }
                if (user != null) {
                    // Điều hướng đến màn hình tiếp theo
                } else {
                    // Hiển thị lỗi
                    error = "Invalid email or password"
                }
            } catch (e: Exception) {
                // Xử lý lỗi
                error = "An error occurred"
            }
        }
    }
}