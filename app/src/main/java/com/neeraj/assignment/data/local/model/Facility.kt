package com.neeraj.assignment.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Facility(
    @PrimaryKey(autoGenerate = false)
    val facilityId: String,
    val name: String
)
