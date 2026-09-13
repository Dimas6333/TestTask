package com.testtask.ui.table

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testtask.domain.model.CellId
import com.testtask.domain.model.Table
import com.testtask.domain.model.TableSize
import com.testtask.domain.usecase.CreateTableUseCase
import com.testtask.ui.table.model.TableScreenEvent
import com.testtask.ui.table.models.TableUiState
import com.testtask.ui.table.models.TableUiState.CellEditorUiModel
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
    savedStateHandle: SavedStateHandle,
    createTableUseCase: CreateTableUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        TableUiState(
            size = TableSize(
                rows = checkNotNull(savedStateHandle.get<Int>(TableRoute.ROWS_ARG)),
                columns = checkNotNull(savedStateHandle.get<Int>(TableRoute.COLUMNS_ARG)),
            ),
        )
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
            is TableScreenEvent.CellClick -> toggleHighlight(event.cellId)
            is TableScreenEvent.CellDoubleClick -> openEditor(event.cellId)
            is TableScreenEvent.EditorValueChanged -> updateEditorValue(event.value)
            TableScreenEvent.EditDismissed -> closeEditor()
            TableScreenEvent.EditConfirmed -> confirmEdit()
        }
    }

    private fun toggleHighlight(cellId: CellId) {
        _uiState.update { state ->
            state.copy(rows = state.rows.mapCells { cell ->
                if (cell.id == cellId) cell.copy(highlighted = !cell.highlighted) else cell
            })
        }
    }

    private fun openEditor(cellId: CellId) {
        _uiState.update { state ->
            val cell = state.rows[cellId.row][cellId.column]
            state.copy(editor = CellEditorUiModel(cellId = cell.id, value = cell.value))
        }
    }

    private fun updateEditorValue(value: String) {
        _uiState.update { state ->
            state.copy(editor = state.editor?.copy(value = value))
        }
    }

    private fun closeEditor() {
        _uiState.update { it.copy(editor = null) }
    }

    private fun confirmEdit() {
        _uiState.update { state ->
            val editor = checkNotNull(state.editor)
            state.copy(
                rows = state.rows.mapCells { cell ->
                    if (cell.id == editor.cellId) cell.copy(value = editor.value) else cell
                },
                editor = null,
            )
        }
    }
}

private fun Table.toCellRows() = List(size.rows) { row ->
    List(size.columns) { column ->
        val id = CellId(row, column)
        CellUiModel(id = id, value = values.getValue(id), highlighted = false)
    }
}

private fun List<List<CellUiModel>>.mapCells(
    transform: (CellUiModel) -> CellUiModel,
) = map { row -> row.map(transform) }
