package com.example.composetests.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composetests.viewmodel.ObservableViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ObservableScreen(navController: NavHostController, viewModel: ObservableViewModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Log.i("LOG_TAG", "ObservableScreen: Called")
    val liveDataText = viewModel.liveData.observeAsState()
    val stateFlowText = viewModel.stateFlow.collectAsState()
    val flowText  = viewModel.triggerFlow().collectAsState(initial = "Hello yea")
    val sharedFlowText  = viewModel.shareFlow.collectAsState(initial = "Hello blah")
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope, scaffoldState = scaffoldState, "Observable") },
        drawerContent = { DrawerContent(navController) }) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(), contentAlignment = Alignment.TopCenter
        )
        {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = liveDataText.value?:"")
                Button(onClick = { viewModel.triggerLiveData() }) {
                    Text(text = "LiveData")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stateFlowText.value)
                Button(onClick = { viewModel.triggerStateFlow() }) {
                    Text(text = "StateFlow")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = flowText.value)
                Button(onClick = {}) {
                    Text(text = "Flow ya")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = sharedFlowText.value)
                Button(onClick = {
                    viewModel.triggerSharedFlow()
                }) {
                    Text(text = "Shared Flow")
                }
            }

        }

    }


}

