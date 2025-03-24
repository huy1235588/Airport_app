package com.example.airport.data.repositories

import com.example.airport.data.db.DatabaseHelper
import com.example.airport.data.models.User
import java.sql.ResultSet
import kotlin.String

class AuthRepository(private val dbHelper: DatabaseHelper) {
    // Kiểm tra thông tin đăng nhập
    fun login(email: String, password: String): User? {
        // Kết nối đến cơ sở dữ liệu
        if (!dbHelper.connect()) {
            return null
        }

        // Thực thi truy vấn để lấy thông tin user
        val query = "SELECT * FROM Users WHERE email = ? AND password = ?"
        val params = arrayOf(email, password)
        val result = dbHelper.executeQueryWithParameters(query, params)

        // Nếu có kết quả trả về thì lấy thông tin user từ ResultSet
        return if (result?.next() == true) {
            getUserFromResult(result)
        } else {
            null
        }
    }

    // Lấy thông tin user từ ResultSet
    private fun getUserFromResult(result: ResultSet): User {
        return User(
            id = result.getInt("ID"),
            roleId = result.getInt("RoleID"),
            email = result.getString("email"),
            password = result.getString("password"),
            firstName = result.getString("FirstName"),
            lastName = result.getString("LastName"),
            officeId = result.getInt("OfficeID"),
            birthDate = result.getString("BirthDate"),
            active = result.getBoolean("active")
        )
    }
}