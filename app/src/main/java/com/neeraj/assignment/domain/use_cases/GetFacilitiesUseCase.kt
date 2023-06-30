package com.neeraj.assignment.domain.use_cases

import com.neeraj.assignment.domain.model.Data
import com.neeraj.assignment.domain.repository.FacilitiesRepository
import io.reactivex.rxjava3.core.Single

class GetFacilitiesUseCase(
    private val repository: FacilitiesRepository
) {

    operator fun invoke(): Single<Data> = repository.getFacilities()
}