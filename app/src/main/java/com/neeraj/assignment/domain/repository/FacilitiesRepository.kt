package com.neeraj.assignment.domain.repository

import com.neeraj.assignment.domain.model.Data
import io.reactivex.rxjava3.core.Single

interface FacilitiesRepository {

    fun getFacilities(): Single<Data>
}