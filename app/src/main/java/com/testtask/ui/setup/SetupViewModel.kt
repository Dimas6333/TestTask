package com.testtask.ui.setup

import androidx.lifecycle.ViewModel
import com.testtask.domain.model.TableSize
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
            is SetupScreenEvent.RowsChanged -> _uiState.update { it.copy(rowsInput = event.value) }
            is SetupScreenEvent.ColumnsChanged -> _uiState.update { it.copy(columnsInput = event.value) }
            SetupScreenEvent.CreateTableClick -> {
                val current = _uiState.value
                tableRequests.trySend(TableSize(current.rowsInput.toInt(), current.columnsInput.toInt()))
            }
        }
    }
}
