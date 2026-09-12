package com.testtask.ui.setup.model

sealed interface SetupScreenEvent {
    data class RowsChanged(val value: String) : SetupScreenEvent
    data class ColumnsChanged(val value: String) : SetupScreenEvent
    data object CreateTableClick : SetupScreenEvent
}
