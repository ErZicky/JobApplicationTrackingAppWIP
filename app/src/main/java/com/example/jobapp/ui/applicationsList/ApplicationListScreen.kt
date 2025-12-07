package com.example.jobapp.ui.applicationsList

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import com.example.jobapp.data.model.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ApplicationListScreen(
    contentPadding: PaddingValues?, viewModel: ApplicationListViewModel = viewModel(factory = ApplicationListViewModel.Factory), onApplicationClick: (Application) -> Unit = {})
{

    val applications by viewModel.applicationsList.collectAsState()



   //Ui:

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        topBar = {
           // TopAppBar(title = { Text("Job Applications") })
        }
    ) {

        paddingValues ->

        if (contentPadding != null) {
            LazyColumn(
                contentPadding = contentPadding,
                modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds()
                    .padding(paddingValues)
            ) {
                items(applications)
                {
                        app -> ApplicationCard(app = app, onClick = onApplicationClick)
                }
            }
        }
    }

}