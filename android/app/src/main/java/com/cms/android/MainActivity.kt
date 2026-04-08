package com.cms.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.cms.android.auth.AuthManager
import com.cms.android.auth.AuthState
import com.cms.android.auth.AuthViewModel
import com.cms.android.data.api.RetrofitClient
import com.cms.android.ui.dashboard.DashboardScreen
import com.cms.android.ui.login.LoginScreen
import com.cms.android.ui.theme.CmsTheme

/**
 * Single-activity host for the CMS Android app.
 *
 * Manages the Keycloak login flow using AppAuth and displays
 * role-based UI via Jetpack Compose after authentication.
 */
class MainActivity : ComponentActivity() {

    private lateinit var authManager: AuthManager
    private lateinit var authViewModel: AuthViewModel

    private val loginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        authViewModel.handleLoginResult(result)
    }

    private val logoutLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { _ ->
        authViewModel.logout()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        authManager = AuthManager(this)
        authViewModel = AuthViewModel(authManager)

        // Wire up Retrofit to use the current access token
        RetrofitClient.init { authManager.getAccessToken() }

        setContent {
            CmsTheme {
                val authState by authViewModel.authState.collectAsState()

                when (val state = authState) {
                    is AuthState.Authenticated -> {
                        DashboardScreen(
                            profile = state.profile,
                            onLogout = {
                                val logoutIntent = authViewModel.createLogoutIntent()
                                if (logoutIntent != null) {
                                    logoutLauncher.launch(logoutIntent)
                                } else {
                                    authViewModel.logout()
                                }
                            },
                        )
                    }
                    else -> {
                        LoginScreen(
                            authState = authState,
                            onLoginClick = {
                                loginLauncher.launch(authViewModel.createLoginIntent())
                            },
                        )
                    }
                }
            }
        }
    }
}
