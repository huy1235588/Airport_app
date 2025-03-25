package com.example.airport.ui.screens

import com.example.airport.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import com.example.airport.data.repository.AuthRepository
import com.example.airport.ui.viewmodels.NavigationViewModel
import com.example.airport.utils.PreferenceHelper

@Composable
fun NavigationScreen(
    onNavigate: (String) -> Unit,
    authRepository: AuthRepository,
    preferenceHelper: PreferenceHelper
) {
    val viewModel: NavigationViewModel =
        remember { NavigationViewModel(authRepository, preferenceHelper) }
    val userInfo by viewModel.userInfo.collectAsState()

    Column {
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = "Avatar"
        )
        Text(text = userInfo?.firstName + " " + userInfo?.lastName)
        Text(text = userInfo?.email ?: "Không có email")

        Button(onClick = { onNavigate("flight_schedules") }) {
            Text("Lịch trình chuyến bay")
        }
        Button(onClick = { onNavigate("revenue_statistics") }) {
            Text("Thống kê doanh thu")
        }
        Button(onClick = { onNavigate("settings") }) {
            Text("Cài đặt")
        }
        Button(onClick = {
            // Xóa thông tin đăng nhập
            preferenceHelper.clearCredentials()
            onNavigate("logout")
        }) {
            Text("Đăng xuất")
        }
    }
}