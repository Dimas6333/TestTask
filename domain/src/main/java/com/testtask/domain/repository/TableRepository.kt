package com.testtask.domain.repository

import com.testtask.domain.model.Table
import com.testtask.domain.model.TableSize

interface TableRepository {
    suspend fun createTable(size: TableSize): Table
}
