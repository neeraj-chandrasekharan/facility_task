package com.neeraj.assignment.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.neeraj.assignment.data.local.model.Exclusion
import com.neeraj.assignment.data.local.model.Option
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ExclusionDao {

    @Upsert
    fun upsertAll(exclusions: List<Exclusion>): Completable

    @Query("SELECT * FROM exclusion ORDER BY id ASC")
    fun getAll(): Single<List<Exclusion>>
}