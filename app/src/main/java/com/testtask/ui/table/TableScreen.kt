package com.testtask.ui.table

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testtask.R
import com.testtask.ui.LocalViewModelFactory
import com.testtask.ui.table.model.TableScreenEvent
import com.testtask.ui.table.models.TableUiState
import com.testtask.ui.table.models.TableUiState.CellUiModel

private val CellHeight = 48.dp

@Composable
fun TableScreen(
    onBack: () -> Unit,
    viewModel: TableViewModel = viewModel(factory = LocalViewModelFactory.current),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.back.collect { onBack() }
    }
    TableScreen(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableScreen(
    state: TableUiState,
    onEvent: (TableScreenEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            R.string.table_title,
                            state.size.rows,
                            state.size.columns,
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onEvent(TableScreenEvent.Back) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.table_back),
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(count = state.rows.size, key = { it }) { rowIndex ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(CellHeight),
                        ) {
                            state.rows[rowIndex].forEach { cell ->
                                TableCellView(
                                    cell = cell,
                                    onEvent = onEvent,
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TableCellView(
    cell: CellUiModel,
    onEvent: (TableScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme
    Surface(
        modifier = modifier,
        color = if (cell.highlighted) colorScheme.tertiaryContainer else colorScheme.surface,
        border = BorderStroke(1.dp, colorScheme.outlineVariant),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onEvent(TableScreenEvent.CellClick(cell.id)) }
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = cell.value,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        }
    }
}
