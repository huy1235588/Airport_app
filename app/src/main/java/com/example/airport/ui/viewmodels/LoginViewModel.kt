package com.example.airport.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airport.data.repository.AuthRepository
import com.example.airport.utils.PreferenceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val preferenceHelper: PreferenceHelper
) : ViewModel() {
    private val _loginResult = MutableStateFlow<LoginResult>(LoginResult.Initial)
    val loginResult: StateFlow<LoginResult> = _loginResult

    fun autoLogin() {
        viewModelScope.launch {
            val savedEmail = getSavedEmail()
            val savedPassword = getSavedPassword()
            if (savedEmail != null && savedPassword != null) {
                login(savedEmail, savedPassword, true)
            }
        }
    }

    fun login(email: String, password: String, remember: Boolean) {
        viewModelScope.launch {
            _loginResult.value = LoginResult.Loading
            try {
                val user = withContext(Dispatchers.IO) {
                    authRepository.login(email, password)
                }
                if (user != null) {
                    if (remember) {
                        saveCredentials(email, password)
                    } else {
                        clearCredentials()
                    }
                    _loginResult.value = LoginResult.Success
                } else {
                    _loginResult.value = LoginResult.Failure("Invalid email or password")
                }
            } catch (e: Exception) {
                _loginResult.value = LoginResult.Failure(e.message ?: "Unknown error")
            }
        }
    }

    private fun saveCredentials(email: String, password: String) {
        // Lưu vào Shared Preferences
        preferenceHelper.saveEmail(email)
        preferenceHelper.savePassword(password)
    }

    private fun getSavedEmail(): String? {
        // Lấy từ Shared Preferences
        val emailSaved = preferenceHelper.getEmail()
        if (emailSaved != null) {
            return emailSaved
        }
        return null
    }

    private fun getSavedPassword(): String? {
        // Lấy từ Shared Preferences
        val passwordSaved = preferenceHelper.getPassword()
        if (passwordSaved != null) {
            return passwordSaved
        }
        return null
    }

    private fun clearCredentials() {
        // Xóa từ Shared Preferences
        preferenceHelper.clearCredentials()
    }
}

sealed class LoginResult {
    object Initial : LoginResult()
    object Loading : LoginResult()
    object Success : LoginResult()
    data class Failure(val error: String) : LoginResult()
}