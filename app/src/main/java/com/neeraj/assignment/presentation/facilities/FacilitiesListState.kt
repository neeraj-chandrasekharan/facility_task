package com.neeraj.assignment.presentation.facilities

import com.neeraj.assignment.domain.model.Data

data class FacilitiesListState(
    val isLoading: Boolean = false,
    val facilities: List<Data.Facility> = emptyList(),
    val error: String = "",
    val exclusionPairs: List<Pair<Data.Facility.Option?, Data.Facility.Option?>> = emptyList(),
    var selectedOptions: Map<String, Data.Facility.Option> = emptyMap()
)
