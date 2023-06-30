package com.neeraj.assignment.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Option(
    val icon: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val facilityId: String
)
