package com.testtask.data

import com.testtask.domain.repository.TableRepository
import dagger.Component
import javax.inject.Singleton

interface DataComponent {
    fun tableRepository(): TableRepository

    companion object {
        fun create(): DataComponent = DaggerDataComponentImpl.create()
    }
}

@Singleton
@Component(modules = [DataModule::class])
internal interface DataComponentImpl : DataComponent
