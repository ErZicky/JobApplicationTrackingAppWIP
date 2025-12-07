package com.example.jobapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobapp.ui.applicationsList.ApplicationListScreen
import com.example.jobapp.ui.createapplication.ApplicationCreationScreen
import com.example.jobapp.ui.navbar.NavBarScreen
import com.example.jobapp.ui.navbar.NavBarViewModel
import com.example.jobapp.ui.theme.JobAppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            JobAppTheme() {
                Surface (modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }

    }

    @Composable
    fun MainScreen( navViewModel : NavBarViewModel = viewModel()) {

        val selectedIndex = navViewModel.selectedIndex.collectAsState().value

        val currentScreen: @Composable (Modifier, PaddingValues) -> Unit = { modifier, padding ->
            when (selectedIndex) {

                0 -> ApplicationListScreen(padding)
                else -> ApplicationListScreen(padding)

            }
        }

        Scaffold(
            bottomBar = {
                NavBarScreen() // La tua Bottom Bar animata
            }
        ) { paddingValues ->
            // Il contenuto principale (la lista delle applicazioni)
           /* ApplicationListScreen(
                contentPadding = paddingValues
            )*/

            currentScreen(Modifier.fillMaxSize(), paddingValues)

        }
    }



}