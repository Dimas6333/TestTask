package com.testtask.domain.usecase

import com.testtask.domain.model.Table
import com.testtask.domain.model.TableSize
import com.testtask.domain.repository.TableRepository

class CreateTableUseCase(
    private val tableRepository: TableRepository,
) {
    suspend operator fun invoke(size: TableSize): Table =
        tableRepository.createTable(size)
}
