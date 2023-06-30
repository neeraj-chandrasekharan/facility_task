package com.neeraj.assignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.neeraj.assignment.data.local.dao.ExclusionDao
import com.neeraj.assignment.data.local.dao.FacilityDao
import com.neeraj.assignment.data.local.dao.OptionDao
import com.neeraj.assignment.data.local.model.Exclusion
import com.neeraj.assignment.data.local.model.Facility
import com.neeraj.assignment.data.local.model.Option

@Database(
    entities = [Facility::class, Option::class, Exclusion::class],
    version = 1
)
abstract class LocalDatabase : RoomDatabase() {

    abstract val facilityDao: FacilityDao
    abstract val optionDao: OptionDao
    abstract val exclusionDao: ExclusionDao

    companion object {
        const val DATABASE_NAME = "facilities_db"
    }
}