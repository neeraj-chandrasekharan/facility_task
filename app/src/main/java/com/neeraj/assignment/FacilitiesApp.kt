package com.neeraj.assignment

import android.app.Application
import com.neeraj.assignment.di.appModule
import com.neeraj.assignment.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class FacilitiesApp: Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@FacilitiesApp)
            modules(appModule, networkModule)
        }
    }
}