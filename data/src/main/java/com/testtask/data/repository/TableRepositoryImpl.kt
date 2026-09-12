package com.testtask.data.repository

import com.testtask.domain.model.CellId
import com.testtask.domain.model.Table
import com.testtask.domain.model.TableSize
import com.testtask.domain.repository.TableRepository
import kotlin.random.Random

internal class TableRepositoryImpl(
    private val random: Random,
) : TableRepository {

    override suspend fun createTable(size: TableSize): Table {
        val values = HashMap<CellId, String>(size.rows * size.columns)
        for (row in 0 until size.rows) {
            for (column in 0 until size.columns) {
                values[CellId(row, column)] = randomValue()
            }
        }
        return Table(size, values)
    }

    private fun randomValue(): String {
        val letters = List(LETTERS_IN_VALUE) { LETTERS.random(random) }.joinToString("")
        return letters + random.nextInt(MIN_NUMBER_IN_VALUE, MAX_NUMBER_IN_VALUE + 1)
    }

    private companion object {
        val LETTERS = 'a'..'z'
        const val LETTERS_IN_VALUE = 5
        const val MIN_NUMBER_IN_VALUE = 10
        const val MAX_NUMBER_IN_VALUE = 99
    }
}
