package com.singular.manager.presentation.profile_manager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.singular.manager.di.AppModule
import com.singular.manager.domain.model.Profile
import com.singular.manager.presentation.common.BottomNavigationBar
import com.singular.manager.presentation.logger.LoggerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileManagerScreen(
    navController: NavController,
    loggerViewModel: LoggerViewModel,
    viewModel: ProfileManagerViewModel = viewModel { AppModule.provideProfileManagerViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Profile Manager") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.showAddEditProfileDialog() }) {
                Icon(Icons.Default.Add, "Add new profile")
            }
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
            Button(onClick = { viewModel.buildProfileFromDevice() }) {
                Text("Build from Device")
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                Text("Loading profiles...")
            } else if (uiState.error != null) {
                Text("Error: ${uiState.error}", color = Color.Red)
            } else if (uiState.profiles.isEmpty()) {
                Text("No profiles found. Add a new one or build from device.")
            } else {
                LazyColumn {
                    items(uiState.profiles) {
                        ProfileItem(profile = it, viewModel = viewModel)
                    }
                }
            }
        }

        if (uiState.showProfileDialog) {
            ProfileDialog(
                profile = uiState.dialogProfile,
                onDismiss = { viewModel.dismissProfileDialog() },
                onConfirm = { viewModel.saveProfile(it) }
            )
        }
    }
}

@Composable
fun ProfileItem(profile: Profile, viewModel: ProfileManagerViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { viewModel.selectProfile(profile) },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = profile.name, style = MaterialTheme.typography.titleMedium)
                profile.gaid?.let { Text(text = "GAID: $it", style = MaterialTheme.typography.bodySmall) }
                profile.email?.let { Text(text = "Email: $it", style = MaterialTheme.typography.bodySmall) }
                profile.uid?.let { Text(text = "UID: $it", style = MaterialTheme.typography.bodySmall) }
            }
            IconButton(onClick = { viewModel.showAddEditProfileDialog(profile) }) {
                Icon(Icons.Default.Edit, "Edit profile")
            }
            IconButton(onClick = { viewModel.deleteProfile(profile) }) {
                Icon(Icons.Default.Delete, "Delete profile", tint = Color.Red)
            }
        }
    }
}

@Composable
fun ProfileDialog(profile: Profile?, onDismiss: () -> Unit, onConfirm: (Profile) -> Unit) {
    var name by remember { mutableStateOf(profile?.name ?: "") }
    var gaid by remember { mutableStateOf(profile?.gaid ?: "") }
    var email by remember { mutableStateOf(profile?.email ?: "") }
    var uid by remember { mutableStateOf(profile?.uid ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (profile == null) "Add New Profile" else "Edit Profile") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Profile Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = gaid,
                    onValueChange = { gaid = it },
                    label = { Text("GAID (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = uid,
                    onValueChange = { uid = it },
                    label = { Text("UID (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val newProfile = (profile ?: Profile(name = "", gaid = null, email = null, uid = null, createdAt = System.currentTimeMillis()))
                    .copy(name = name, gaid = gaid.ifEmpty { null }, email = email.ifEmpty { null }, uid = uid.ifEmpty { null })
                onConfirm(newProfile)
            }) { Text("Save") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
