package com.testtask.di

import com.testtask.MainActivity
import com.testtask.data.DataComponent
import com.testtask.domain.repository.TableRepository
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [AppModule::class])
internal interface AppComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance dataComponent: DataComponent): AppComponent
    }
}

@Module
internal object AppModule {
    @Provides
    fun tableRepository(dataComponent: DataComponent): TableRepository =
        dataComponent.tableRepository()
}
