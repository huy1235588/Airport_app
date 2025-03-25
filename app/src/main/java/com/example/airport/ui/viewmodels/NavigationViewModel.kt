package com.example.airport.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airport.data.models.User
import com.example.airport.data.repository.AuthRepository
import com.example.airport.utils.PreferenceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NavigationViewModel(
    private val authRepository: AuthRepository,
    private val preferenceHelper: PreferenceHelper
) : ViewModel() {
    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo: StateFlow<User?> = _userInfo

    init {
        loadUserInfo()
    }

    fun loadUserInfo() {
        viewModelScope.launch {
            val email = getSavedEmail()
            if (email != null) {
                val user = withContext(Dispatchers.IO) {
                    authRepository.getUserByEmail(email)
                }
                _userInfo.value = user
            }
        }
    }

    private fun getSavedEmail(): String? {
        // Lấy từ Shared Preferences
        val emailSaved = preferenceHelper.getEmail()
        if (emailSaved != null) {
            return emailSaved
        }
        return null
    }
}