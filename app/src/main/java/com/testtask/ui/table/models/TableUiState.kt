package com.testtask.ui.table.models

import com.testtask.domain.model.CellId
import com.testtask.domain.model.TableSize

data class TableUiState(
    val size: TableSize,
    val isLoading: Boolean = true,
    val rows: List<List<CellUiModel>> = emptyList(),
    val editor: CellEditorUiModel? = null,
) {
    data class CellUiModel(
        val id: CellId,
        val value: String,
        val highlighted: Boolean,
    )

    data class CellEditorUiModel(
        val cellId: CellId,
        val value: String,
    )
}
