package com.testtask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.testtask.di.AppViewModelFactory
import com.testtask.ui.theme.TableTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    internal lateinit var viewModelFactory: AppViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TableApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TableTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Text("Table")
                }
            }
        }
    }
}
