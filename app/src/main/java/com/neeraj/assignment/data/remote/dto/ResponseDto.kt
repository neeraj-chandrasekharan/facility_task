package com.neeraj.assignment.data.remote.dto

import com.neeraj.assignment.domain.model.Data

data class ResponseDto(
    val exclusions: List<List<ExclusionDto>>, val facilities: List<FacilityDto>
)

fun ResponseDto.mapToData(): Data {
    val facilities = this.facilities.map {
        it.mapDtoToModel()
    }
    val options = facilities.flatMap {
        it.options.map { option ->
            option
        }
    }
    val exclusionPairs = this.exclusions.map {
        it.map { dto ->
            dto.mapDtoToModel()
        }
    }.map { listItem ->
        options.find {
            listItem[0].optionsId == it.id
        } to options.find {
            listItem[1].optionsId == it.id
        }
    }
    return Data(
        exclusions = exclusionPairs, facilities = facilities
    )
}