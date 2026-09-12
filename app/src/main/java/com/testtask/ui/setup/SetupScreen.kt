package com.testtask.ui.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testtask.R
import com.testtask.domain.model.TableSize
import com.testtask.ui.LocalViewModelFactory
import com.testtask.ui.setup.model.SetupScreenEvent
import com.testtask.ui.setup.models.SetupUiState

@Composable
fun SetupScreen(
    onOpenTable: (TableSize) -> Unit,
    viewModel: SetupViewModel = viewModel(factory = LocalViewModelFactory.current),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.requestedTables.collect(onOpenTable)
    }
    SetupScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun SetupScreen(
    uiState: SetupUiState,
    onEvent: (SetupScreenEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 520.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.setup_title),
                style = MaterialTheme.typography.headlineMedium,
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.rowsInput,
                onValueChange = { onEvent(SetupScreenEvent.RowsChanged(it)) },
                label = { Text(stringResource(R.string.setup_rows)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.columnsInput,
                onValueChange = { onEvent(SetupScreenEvent.ColumnsChanged(it)) },
                label = { Text(stringResource(R.string.setup_columns)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onEvent(SetupScreenEvent.CreateTableClick) },
            ) {
                Text(stringResource(R.string.setup_create_table))
            }
        }
    }
}
