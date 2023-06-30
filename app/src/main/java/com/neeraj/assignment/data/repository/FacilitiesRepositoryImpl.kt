package com.neeraj.assignment.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.neeraj.assignment.data.local.Converters
import com.neeraj.assignment.data.local.dao.ExclusionDao
import com.neeraj.assignment.data.local.dao.FacilityDao
import com.neeraj.assignment.data.local.dao.OptionDao
import com.neeraj.assignment.data.local.model.Exclusion
import com.neeraj.assignment.data.local.model.Facility
import com.neeraj.assignment.data.local.model.Option
import com.neeraj.assignment.data.remote.FacilitiesApi
import com.neeraj.assignment.data.remote.dto.ExclusionDto
import com.neeraj.assignment.data.remote.dto.FacilityDto
import com.neeraj.assignment.data.remote.dto.OptionDto
import com.neeraj.assignment.data.remote.dto.ResponseDto
import com.neeraj.assignment.data.remote.dto.mapToData
import com.neeraj.assignment.domain.model.Data
import com.neeraj.assignment.domain.repository.FacilitiesRepository
import com.neeraj.assignment.domain.repository.SharedPrefRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class FacilitiesRepositoryImpl(
    private val api: FacilitiesApi,
    private val facilityDao: FacilityDao,
    private val optionDao: OptionDao,
    private val exclusionDao: ExclusionDao,
    private val converters: Converters,
    private val sharedPreferencesRepo: SharedPrefRepository
) : FacilitiesRepository {
    override fun getFacilities(): Single<Data> {
        return facilityDao.getFacilitiesOrderedById().flatMap {
            val lastUpdateTime =
                sharedPreferencesRepo.getLong("last_update")
            val currentTime = System.currentTimeMillis()
            val shouldUpdate = currentTime - lastUpdateTime > (24 * 60 * 60 * 1000)
            if (it.isEmpty() || shouldUpdate) {
                api.getFacilities().doAfterSuccess { response ->
                    sharedPreferencesRepo.putLong("last_update", System.currentTimeMillis())
                    facilityDao.upsertAll(response.facilities.map {
                        Facility(facilityId = it.facilityId, name = it.name)
                    }).subscribeOn(Schedulers.io()).subscribe()
                    val options = response.facilities.flatMap { facility ->
                        facility.options.map {
                            Option(
                                icon = it.icon,
                                id = it.id,
                                name = it.name,
                                facilityId = facility.facilityId
                            )
                        }
                    }
                    optionDao.upsertAll(options).subscribeOn(Schedulers.io()).subscribe()
                    exclusionDao.upsertAll(response.exclusions.mapIndexed { index, it ->
                        Exclusion(id = index, data = converters.fromArrayList(it))
                    }).subscribeOn(Schedulers.io()).subscribe()
                }.map { it.mapToData() }
            } else {
                Single.just(
                    ResponseDto(
                        facilities = it.map {
                            FacilityDto(
                                facilityId = it.facilityId,
                                name = it.name,
                                options = optionDao.getOptionsOrderedByIdForFacility(it.facilityId)
                                    .blockingGet()
                                    .map {
                                        OptionDto(it.icon, it.id, it.name)
                                    },
                            )
                        },
                        exclusions = exclusionDao.getAll().blockingGet().map {
                            converters.fromString(it.data)?.toList() ?: emptyList()
                        }
                    )
                ).map { it.mapToData() }
            }
        }
    }
}