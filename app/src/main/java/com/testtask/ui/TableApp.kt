package com.testtask.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.testtask.ui.setup.SetupScreen
import com.testtask.ui.theme.TableTheme

private const val SETUP_ROUTE = "setup"

internal val LocalViewModelFactory = staticCompositionLocalOf<ViewModelProvider.Factory> {
    error("ViewModelFactory is not provided")
}

@Composable
fun TableApp(viewModelFactory: ViewModelProvider.Factory) {
    CompositionLocalProvider(LocalViewModelFactory provides viewModelFactory) {
        TableTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = SETUP_ROUTE,
                ) {
                    composable(SETUP_ROUTE) {
                        SetupScreen(onOpenTable = { })
                    }
                }
            }
        }
    }
}
