package com.neeraj.assignment.data.local

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neeraj.assignment.data.remote.dto.ExclusionDto
import java.lang.reflect.Type


class Converters {

    fun fromString(value: String?): List<ExclusionDto>? {
        val listType: Type = object : TypeToken<List<ExclusionDto>?>() {}.type
        return Gson().fromJson(value, listType)
    }


    fun fromArrayList(list: List<Any?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}