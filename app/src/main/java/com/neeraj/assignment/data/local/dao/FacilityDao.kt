package com.neeraj.assignment.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.neeraj.assignment.data.local.model.Facility
import com.neeraj.assignment.data.local.model.Option
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface FacilityDao{

    @Upsert
    fun upsertFacility(facility: Facility): Completable

    @Upsert
    fun upsertAll(facilities: List<Facility>): Completable

    @Delete
    fun deleteFacility(facility: Facility): Completable

    @Query("SELECT * FROM facility ORDER BY facilityId ASC")
    fun getFacilitiesOrderedById(): Single<List<Facility>>
}
