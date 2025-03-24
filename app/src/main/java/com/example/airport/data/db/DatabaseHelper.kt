package com.example.airport.data.db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class DatabaseHelper {
    private var connection: Connection? = null
    private var connected = false
    private val connectionUrl =
        "jdbc:jtds:sqlserver://192.168.1.13:1433/Airport_1;user=sa;password=sa"

    // Kết nối đến cơ sở dữ liệu
    fun connect(): Boolean {
        if (connected) {
            return true
        }
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connection = DriverManager.getConnection(connectionUrl)
            connected = true
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    // Thực thi câu lệnh truy vấn
    fun executeQuery(query: String): ResultSet? {
        return try {
            val statement = connection?.createStatement()
            statement?.executeQuery(query)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Thực thi câu lệnh truy vấn với tham số
    fun executeQueryWithParameters(query: String, params: Array<String>): ResultSet? {
        return try {
            val statement = connection?.prepareStatement(query)
            params.forEachIndexed { index, param ->
                statement?.setString(index + 1, param)
            }
            statement?.executeQuery()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}