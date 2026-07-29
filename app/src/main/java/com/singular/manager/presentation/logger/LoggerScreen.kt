package com.singular.manager.presentation.logger

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singular.manager.di.AppModule

@Composable
fun LoggerScreen(
    loggerViewModel: LoggerViewModel = viewModel { AppModule.provideLoggerViewModel() }
) {
    val logMessages by loggerViewModel.logMessages.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Real-time Logger") })
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Button(onClick = { loggerViewModel.clearLogs() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Clear Logs")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(logMessages) {\n                    log ->
                    LogMessageItem(logMessage = log)
                }
            }
        }
    }
}

@Composable
fun LogMessageItem(logMessage: LogMessage) {
    val textColor = when (logMessage.level) {
        LogLevel.SEND -> Color.Green
        LogLevel.DEBUG -> Color.Blue
        LogLevel.ERROR -> Color.Red
    }
    Text(
        text = "[${logMessage.level}] ${logMessage.message}",
        color = textColor,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
    )
}
