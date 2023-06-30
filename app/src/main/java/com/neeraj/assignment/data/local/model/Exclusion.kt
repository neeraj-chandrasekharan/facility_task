package com.neeraj.assignment.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exclusion(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val data: String
)