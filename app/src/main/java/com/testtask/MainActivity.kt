package com.testtask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.testtask.di.AppViewModelFactory
import com.testtask.ui.TableApp
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    internal lateinit var viewModelFactory: AppViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TableApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TableApp(viewModelFactory = viewModelFactory)
        }
    }
}
