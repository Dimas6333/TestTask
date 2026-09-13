package com.testtask.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.testtask.domain.repository.TableRepository
import com.testtask.domain.usecase.CreateTableUseCase
import com.testtask.ui.setup.SetupViewModel
import com.testtask.ui.table.TableViewModel
import javax.inject.Inject

internal class AppViewModelFactory @Inject constructor(
    private val tableRepository: TableRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val viewModel = when {
            modelClass.isAssignableFrom(SetupViewModel::class.java) -> SetupViewModel()
            modelClass.isAssignableFrom(TableViewModel::class.java) ->
                TableViewModel(
                    extras.createSavedStateHandle(),
                    CreateTableUseCase(tableRepository),
                )
            else -> error("Unknown ViewModel ${modelClass.name}")
        }
        @Suppress("UNCHECKED_CAST")
        return viewModel as T
    }
}
