package com.singular.manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.singular.manager.ui.theme.SingularManagerTheme
import com.singular.manager.di.AppModule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.singular.manager.presentation.dashboard.DashboardScreen
import com.singular.manager.presentation.profile_manager.ProfileManagerScreen
import com.singular.manager.presentation.logger.LoggerScreen
import com.singular.manager.presentation.logger.LoggerViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppModule.init(applicationContext)

        setContent {
            SingularManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val loggerViewModel: LoggerViewModel = viewModel { AppModule.provideLoggerViewModel() }

                    NavHost(navController = navController, startDestination = "dashboard") {
                        composable("dashboard") {
                            DashboardScreen(navController = navController, loggerViewModel = loggerViewModel)
                        }
                        composable("profile_manager") {
                            ProfileManagerScreen(navController = navController, loggerViewModel = loggerViewModel)
                        }
                        composable("logger") {
                            LoggerScreen(loggerViewModel = loggerViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SingularManagerTheme {
        // Preview content for the main activity
    }
}
