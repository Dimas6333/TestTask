package com.testtask.ui.table

import com.testtask.domain.model.TableSize

object TableRoute {
    const val ROWS_ARG = "rows"
    const val COLUMNS_ARG = "columns"
    const val PATTERN = "table/{$ROWS_ARG}/{$COLUMNS_ARG}"

    fun path(size: TableSize) = "table/${size.rows}/${size.columns}"
}
