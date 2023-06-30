package com.neeraj.assignment.data.repository

import android.content.SharedPreferences
import com.neeraj.assignment.domain.repository.SharedPrefRepository

class SharedPrefRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : SharedPrefRepository {

    override fun putLong(key: String, value: Long) {
        sharedPreferences.edit().also {
            it.putLong(key, value)
        }.apply()
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }
}