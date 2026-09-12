package com.testtask.ui.setup

import androidx.lifecycle.ViewModel
import com.testtask.domain.model.TableSize
import com.testtask.ui.setup.model.FieldError
import com.testtask.ui.setup.model.SetupScreenEvent
import com.testtask.ui.setup.models.SetupUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class SetupViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SetupUiState())
    val uiState: StateFlow<SetupUiState> = _uiState.asStateFlow()

    private val tableRequests = Channel<TableSize>(Channel.CONFLATED)
    val requestedTables: Flow<TableSize> = tableRequests.receiveAsFlow()

    fun onEvent(event: SetupScreenEvent) {
        when (event) {
            is SetupScreenEvent.RowsChanged -> onRowsChanged(event.value)
            is SetupScreenEvent.ColumnsChanged -> onColumnsChanged(event.value)
            SetupScreenEvent.CreateTableClick -> onCreateTableClick()
        }
    }

    private fun onRowsChanged(value: String) {
        if (!fitsLimit(value, MAX_ROWS)) return
        _uiState.update { it.copy(rowsInput = value, rowsError = null) }
    }

    private fun onColumnsChanged(value: String) {
        if (!fitsLimit(value, MAX_COLUMNS)) return
        _uiState.update { it.copy(columnsInput = value, columnsError = null) }
    }

    private fun onCreateTableClick() {
        val current = _uiState.value
        val rowsError = errorFor(current.rowsInput, MIN_ROWS, MAX_ROWS)
        val columnsError = errorFor(current.columnsInput, MIN_COLUMNS, MAX_COLUMNS)
        _uiState.update { it.copy(rowsError = rowsError, columnsError = columnsError) }
        if (rowsError == null && columnsError == null) {
            tableRequests.trySend(TableSize(current.rowsInput.toInt(), current.columnsInput.toInt()))
        }
    }

    private fun fitsLimit(value: String, max: Int) =
        value.length <= max.toString().length && value.all { it.isDigit() }

    private fun errorFor(input: String, min: Int, max: Int) = when {
        input.isEmpty() -> FieldError.Empty
        input.toInt() !in min..max -> FieldError.OutOfRange
        else -> null
    }

    companion object {
        const val MIN_ROWS = 1
        const val MAX_ROWS = 1000
        const val MIN_COLUMNS = 1
        const val MAX_COLUMNS = 1000
    }
}
