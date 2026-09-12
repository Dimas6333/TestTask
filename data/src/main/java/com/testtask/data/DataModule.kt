package com.testtask.data

import com.testtask.data.repository.TableRepositoryImpl
import com.testtask.domain.repository.TableRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlin.random.Random

@Module
internal object DataModule {

    @Provides
    @Singleton
    fun tableRepository(random: Random): TableRepository = TableRepositoryImpl(random)

    @Provides
    fun random(): Random = Random.Default
}
