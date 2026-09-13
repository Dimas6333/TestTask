package com.testtask.ui.table.model

sealed interface TableScreenEvent {
    data object Back : TableScreenEvent
}
