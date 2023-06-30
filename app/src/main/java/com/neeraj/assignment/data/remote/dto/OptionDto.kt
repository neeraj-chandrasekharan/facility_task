package com.neeraj.assignment.data.remote.dto

import com.neeraj.assignment.R
import com.neeraj.assignment.domain.model.Data

data class OptionDto(
    val icon: String, val id: String, val name: String
)

fun OptionDto.mapDtoToModel(facilityId: String): Data.Facility.Option {
    return Data.Facility.Option(
        icon = this.icon.let {
            when {
                it.contains("apartment") -> R.drawable.ic_apartment
                it.contains("condo") -> R.drawable.ic_condo
                it.contains("boat") -> R.drawable.ic_boat
                it.contains("land") -> R.drawable.ic_land
                it.contains("swimming") -> R.drawable.ic_swimming
                it.contains("garden") -> R.drawable.ic_garden
                it.contains("garage") -> R.drawable.ic_garage
                it.contains("no-room") -> R.drawable.ic_no_room
                it.contains("rooms") -> R.drawable.ic_rooms
                else -> R.drawable.ic_no_room
            }
        },
        id = this.id,
        name = this.name,
        facilityId = facilityId,
    )
}