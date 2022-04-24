package com.example.composetests.ui.screens

import android.icu.text.CaseMap
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composetests.R
import com.example.composetests.ui.navigation.Screens
import com.example.composetests.ui.theme.ComposeTestsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

@Composable
fun MainScreen(navController: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                scope,
                scaffoldState = scaffoldState,
                context.getString(R.string.app_name)
            )
        },
        drawerContent = { DrawerContent(navController) }) {
        Greeting(name = "")
    }

}

@Composable
fun DrawerContent(navController: NavHostController) {
    val screens = listOf(Screens.MainScreen, Screens.ObservableScreen)
    Column() {
        Image(
            imageVector = Icons.Default.Person,
            contentDescription = "Icon",
            modifier = Modifier.size(200.dp, 200.dp)
        )
        LazyColumn {
            items(screens) {
                Row(Modifier.clickable { navController.navigate(it.name) }) {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(8.dp)
                    )
                    Divider()
                }

            }
        }
    }

}

@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState, title: String) {

    TopAppBar(title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Default.Menu, contentDescription = "Icon")
            }
        }
    )
}


@Composable
fun Greeting(name: String) {
    Column(Modifier.fillMaxSize().padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        var timerDuration by remember {
            mutableStateOf(1000L)
        }
        Button(onClick = { timerDuration -= 1000 }) {
            Text(text = "-1 Sec")
        }
        Text(timerDuration.toString())
        Button(onClick = { timerDuration += 1000 }) {
            Text(text = "+1 Sec")
        }
        Timer(timerDuration = timerDuration)
    }

}

@Composable
fun Timer(timerDuration: Long) {

    val callback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //
            }
        }
    }



    LaunchedEffect(key1 = timerDuration) {
        try {
            startTimer(timerDuration) {
                println("Timer ended")
            }
        } catch (e: Exception) {
            println("Timer cancelled")
        }
    }
}

suspend fun startTimer(timerDuration: Long, onTimerEnd: () -> Unit) {
    delay(timeMillis = timerDuration)
    onTimerEnd()
}

@Composable
fun YourComposable() {
    val focusRequester = FocusRequester()


    Column() {
        OutlinedTextField("Hello", {}, label = {
            Text(text = "Label")
        }, modifier = Modifier.focusRequester(focusRequester))
        OutlinedTextField("hello2", {})
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
//        this.coroutineContext.job.invokeOnCompletion {
//
//        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTestsTheme {
        Greeting("Android")
    }
}