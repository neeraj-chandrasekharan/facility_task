package com.neeraj.assignment.data.remote

import com.neeraj.assignment.common.Constants
import com.neeraj.assignment.data.remote.dto.ResponseDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface FacilitiesApi {

    @GET(Constants.EndPoints.FACILITIES)
    fun getFacilities(): Single<ResponseDto>

}