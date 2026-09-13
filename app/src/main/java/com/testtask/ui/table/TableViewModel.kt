package com.testtask.ui.table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testtask.domain.model.CellId
import com.testtask.domain.model.Table
import com.testtask.domain.model.TableSize
import com.testtask.domain.usecase.CreateTableUseCase
import com.testtask.ui.table.model.TableScreenEvent
import com.testtask.ui.table.models.TableUiState
import com.testtask.ui.table.models.TableUiState.CellUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TableViewModel(
    createTableUseCase: CreateTableUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        TableUiState(size = checkNotNull(pendingSize))
    )
    val uiState: StateFlow<TableUiState> = _uiState.asStateFlow()

    private val backEvents = Channel<Unit>(Channel.CONFLATED)
    val back: Flow<Unit> = backEvents.receiveAsFlow()

    init {
        viewModelScope.launch {
            val table = createTableUseCase(_uiState.value.size)
            _uiState.update { it.copy(isLoading = false, rows = table.toCellRows()) }
        }
    }

    fun onEvent(event: TableScreenEvent) {
        when (event) {
            TableScreenEvent.Back -> backEvents.trySend(Unit)
        }
    }

    companion object {
        var pendingSize: TableSize? = null
    }
}

private fun Table.toCellRows() = List(size.rows) { row ->
    List(size.columns) { column ->
        val id = CellId(row, column)
        CellUiModel(id = id, value = values.getValue(id), highlighted = false)
    }
}
