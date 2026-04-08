package com.cms.android.auth

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cms.android.data.model.UserProfile
import com.cms.android.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Possible states of the authentication flow. */
sealed interface AuthState {
    data object Idle : AuthState
    data object Loading : AuthState
    data class Authenticated(val profile: UserProfile) : AuthState
    data class Error(val message: String) : AuthState
}

/**
 * ViewModel managing authentication state and user profile data.
 * Bridges [AuthManager] (AppAuth) with the app's UI layer.
 */
class AuthViewModel(
    private val authManager: AuthManager,
) : ViewModel() {

    private val userRepository = UserRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    /** Create a login intent to launch in an ActivityResultLauncher. */
    fun createLoginIntent(): Intent = authManager.createLoginIntent()

    /** Process the result returned from the Keycloak login browser flow. */
    fun handleLoginResult(result: ActivityResult) {
        _authState.value = AuthState.Loading

        authManager.handleLoginResult(
            result = result,
            onSuccess = { _ ->
                fetchUserProfile()
            },
            onError = { exception ->
                _authState.value = AuthState.Error(
                    exception?.errorDescription ?: "Authentication failed"
                )
            },
        )
    }

    /** Create a logout intent. Returns null if no session is active. */
    fun createLogoutIntent(): Intent? = authManager.createLogoutIntent()

    /** Sign out and clear all session state. */
    fun logout() {
        authManager.clearSession()
        _authState.value = AuthState.Idle
    }

    /** Fetch the user profile from the backend API. */
    private fun fetchUserProfile() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            userRepository.getCurrentUser()
                .onSuccess { profile ->
                    _authState.value = AuthState.Authenticated(profile)
                }
                .onFailure { error ->
                    _authState.value = AuthState.Error(
                        error.message ?: "Failed to load user profile"
                    )
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        authManager.dispose()
    }
}
