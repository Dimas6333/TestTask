package com.testtask.ui.setup.models

import com.testtask.ui.setup.model.FieldError

data class SetupUiState(
    val rowsInput: String = "",
    val columnsInput: String = "",
    val rowsError: FieldError? = null,
    val columnsError: FieldError? = null,
)
