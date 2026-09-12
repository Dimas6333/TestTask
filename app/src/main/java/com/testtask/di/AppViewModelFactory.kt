package com.testtask.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.testtask.domain.repository.TableRepository
import javax.inject.Inject

internal class AppViewModelFactory @Inject constructor(
    private val tableRepository: TableRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        error("Unknown ViewModel ${modelClass.name}")
    }
}
