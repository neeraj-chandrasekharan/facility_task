package com.neeraj.assignment.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.neeraj.assignment.data.local.Converters
import com.neeraj.assignment.data.local.LocalDatabase
import com.neeraj.assignment.data.remote.FacilitiesApi
import com.neeraj.assignment.data.repository.FacilitiesRepositoryImpl
import com.neeraj.assignment.data.repository.SharedPrefRepositoryImpl
import com.neeraj.assignment.domain.repository.FacilitiesRepository
import com.neeraj.assignment.domain.repository.SharedPrefRepository
import com.neeraj.assignment.domain.use_cases.GetFacilitiesUseCase
import com.neeraj.assignment.presentation.facilities.FacilitiesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::SharedPrefRepositoryImpl) { bind<SharedPrefRepository>() }
    singleOf(::provideFacilitiesRepository)
    singleOf(::provideSharedPreferences)
    viewModelOf(::FacilitiesViewModel)
    singleOf(::GetFacilitiesUseCase)
    singleOf(::provideLocalDatabase)
    singleOf(::Converters)
}

fun provideLocalDatabase(app: Application): LocalDatabase {
    return Room.databaseBuilder(
        app,
        LocalDatabase::class.java,
        LocalDatabase.DATABASE_NAME
    ).build()
}

fun provideSharedPreferences(app: Application): SharedPreferences {
    return app.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
}

fun provideFacilitiesRepository(
    facilitiesApi: FacilitiesApi,
    converters: Converters,
    sharedPrefRepository: SharedPrefRepository,
    db: LocalDatabase
): FacilitiesRepository {
    return FacilitiesRepositoryImpl(
        facilitiesApi,
        db.facilityDao,
        db.optionDao,
        db.exclusionDao,
        converters,
        sharedPrefRepository
    )
}