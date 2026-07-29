package com.singular.manager.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.singular.manager.di.AppModule
import com.singular.manager.presentation.common.BottomNavigationBar
import com.singular.manager.presentation.logger.LoggerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    loggerViewModel: LoggerViewModel,
    viewModel: DashboardViewModel = viewModel { AppModule.provideDashboardViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Control Room (Dashboard)") })
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = "Data Health Stats", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                Text("Loading data health stats...")
            } else if (uiState.error != null) {
                Text("Error: ${uiState.error}", color = Color.Red)
            } else {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Profiles missing GAID: ${uiState.profilesMissingGaid}")
                        Text("Duplicate Emails: ${uiState.duplicateEmails.size}")
                        Text("Duplicate UIDs: ${uiState.duplicateUids.size}")
                    }
                }
            }
        }
    }
}
                        Text("Duplicate Emails: ", style = MaterialTheme.typography.titleMedium)
                        if (uiState.duplicateEmails.isNotEmpty()) {
                            uiState.duplicateEmails.forEach { email ->
                                Text(email, color = Color.Red)
                            }
                        } else {
                            Text("No duplicate emails found.", color = Color.Green)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Duplicate UIDs: ", style = MaterialTheme.typography.titleMedium)
                        if (uiState.duplicateUids.isNotEmpty()) {
                            uiState.duplicateUids.forEach { uid ->
                                Text(uid, color = Color.Red)
                            }
                        } else {
                            Text("No duplicate UIDs found.", color = Color.Green)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Games without defined event tokens: ", style = MaterialTheme.typography.titleMedium)
                        if (uiState.gamesWithoutEventTokens.isNotEmpty()) {
                            uiState.gamesWithoutEventTokens.forEach { game ->
                                Text(game.name, color = Color.Red)
                            }
                        } else {
                            Text("All games have event tokens defined.", color = Color.Green)
                        }
                    }
                }
            }
        }
    }
}
