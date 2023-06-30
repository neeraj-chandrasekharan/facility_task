package com.neeraj.assignment.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.neeraj.assignment.domain.model.Data

data class ExclusionDto(
    @SerializedName("facility_id")
    val facilityId: String,
    @SerializedName("options_id")
    val optionsId: String
)

fun ExclusionDto.mapDtoToModel() : Data.Exclusion {
    return Data.Exclusion(facilityId = this.facilityId, optionsId = this.optionsId)
}