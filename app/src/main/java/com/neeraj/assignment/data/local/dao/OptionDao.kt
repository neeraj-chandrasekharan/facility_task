package com.neeraj.assignment.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.neeraj.assignment.data.local.model.Option
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface OptionDao {

    @Upsert
    fun upsertOption(option: Option): Completable

    @Upsert
    fun upsertAll(options: List<Option>): Completable

    @Delete
    fun deleteOption(option: Option): Completable

    @Query("SELECT * FROM option ORDER BY id ASC")
    fun getOptionsOrderedById(): Single<List<Option>>

    @Query("SELECT * FROM option WHERE facilityId = (:facilityId) ORDER BY id ASC")
    fun getOptionsOrderedByIdForFacility(facilityId: String): Single<List<Option>>
}