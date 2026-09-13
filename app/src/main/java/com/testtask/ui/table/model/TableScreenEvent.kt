package com.testtask.ui.table.model

import com.testtask.domain.model.CellId

sealed interface TableScreenEvent {
    data object Back : TableScreenEvent
    data class CellClick(val cellId: CellId) : TableScreenEvent
}
