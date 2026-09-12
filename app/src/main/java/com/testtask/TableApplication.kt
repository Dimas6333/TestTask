package com.testtask

import android.app.Application
import com.testtask.data.DataComponent
import com.testtask.di.AppComponent
import com.testtask.di.DaggerAppComponent

class TableApplication : Application() {
    internal lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(DataComponent.create())
    }
}
