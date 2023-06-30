package com.neeraj.assignment.domain.model

import androidx.annotation.DrawableRes

data class Data(
    val exclusions: List<Pair<Facility.Option?, Facility.Option?>>,
    val facilities: List<Facility>
) {
    data class Exclusion(
        val facilityId: String,
        val optionsId: String
    )

    data class Facility(
        val facilityId: String,
        val name: String,
        val options: List<Option>
    ) {
        data class Option(
            @DrawableRes
            val icon: Int,
            val id: String,
            val facilityId: String,
            val name: String,
        )
    }
}
