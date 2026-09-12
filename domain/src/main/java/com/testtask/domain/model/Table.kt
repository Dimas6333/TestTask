package com.testtask.domain.model

data class Table(
    val size: TableSize,
    val values: Map<CellId, String>,
)
