package com.neeraj.assignment.domain.repository

interface SharedPrefRepository {

    fun getLong(key: String, defaultValue: Long = 0): Long
    fun putLong(key: String, value: Long)

}