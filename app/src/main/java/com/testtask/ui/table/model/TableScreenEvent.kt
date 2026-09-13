package com.testtask.ui.table.model

import com.testtask.domain.model.CellId

sealed interface TableScreenEvent {
    data object Back : TableScreenEvent
    data class CellClick(val cellId: CellId) : TableScreenEvent
    data class CellDoubleClick(val cellId: CellId) : TableScreenEvent
    data class EditorValueChanged(val value: String) : TableScreenEvent
    data object EditDismissed : TableScreenEvent
    data object EditConfirmed : TableScreenEvent
}
