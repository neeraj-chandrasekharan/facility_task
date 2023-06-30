package com.neeraj.assignment.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.neeraj.assignment.domain.model.Data

data class FacilityDto(
    @SerializedName("facility_id")
    val facilityId: String,
    val name: String,
    val options: List<OptionDto>
)

fun FacilityDto.mapDtoToModel(): Data.Facility {
    return Data.Facility(
        facilityId = this.facilityId,
        name = this.name,
        options = this.options.map {
            it.mapDtoToModel(this.facilityId)
        }
    )
}