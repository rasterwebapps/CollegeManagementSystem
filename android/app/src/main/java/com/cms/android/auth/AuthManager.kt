package com.cms.android.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import com.cms.android.BuildConfig
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenResponse

/**
 * Manages OAuth2/OpenID Connect authentication with Keycloak via the AppAuth library.
 *
 * Uses the Authorization Code flow with PKCE, which is the recommended approach
 * for native mobile applications.
 */
class AuthManager(private val context: Context) {

    private val authService = AuthorizationService(context)

    private val serviceConfig = AuthorizationServiceConfiguration(
        Uri.parse("${BuildConfig.KEYCLOAK_URL}/realms/${BuildConfig.KEYCLOAK_REALM}/protocol/openid-connect/auth"),
        Uri.parse("${BuildConfig.KEYCLOAK_URL}/realms/${BuildConfig.KEYCLOAK_REALM}/protocol/openid-connect/token"),
        null,
        Uri.parse("${BuildConfig.KEYCLOAK_URL}/realms/${BuildConfig.KEYCLOAK_REALM}/protocol/openid-connect/logout")
    )

    private val redirectUri = Uri.parse("com.cms.android://oauth2callback")

    private var accessToken: String? = null
    private var refreshToken: String? = null

    /** Returns the current access token, or null if not authenticated. */
    fun getAccessToken(): String? = accessToken

    /** Returns true if the user has an access token. */
    fun isAuthenticated(): Boolean = accessToken != null

    /**
     * Creates an Intent that starts the Keycloak login flow in a Custom Tab / browser.
     * The caller should launch this intent via an ActivityResultLauncher.
     */
    fun createLoginIntent(): Intent {
        val authRequest = AuthorizationRequest.Builder(
            serviceConfig,
            BuildConfig.KEYCLOAK_CLIENT_ID,
            ResponseTypeValues.CODE,
            redirectUri
        )
            .setScopes("openid", "profile", "email")
            .build()

        return authService.getAuthorizationRequestIntent(authRequest)
    }

    /**
     * Handles the result from the login flow. Exchanges the authorization code for tokens.
     *
     * @param result The ActivityResult from launching the login intent.
     * @param onSuccess Called with the [TokenResponse] when token exchange succeeds.
     * @param onError Called with the [AuthorizationException] when something fails.
     */
    fun handleLoginResult(
        result: ActivityResult,
        onSuccess: (TokenResponse) -> Unit,
        onError: (AuthorizationException?) -> Unit,
    ) {
        val data = result.data ?: run {
            onError(null)
            return
        }

        val response = AuthorizationResponse.fromIntent(data)
        val exception = AuthorizationException.fromIntent(data)

        if (response != null) {
            authService.performTokenRequest(response.createTokenExchangeRequest()) { tokenResponse, tokenException ->
                if (tokenResponse != null) {
                    accessToken = tokenResponse.accessToken
                    refreshToken = tokenResponse.refreshToken
                    onSuccess(tokenResponse)
                } else {
                    onError(tokenException)
                }
            }
        } else {
            onError(exception)
        }
    }

    /**
     * Creates an Intent that starts the Keycloak logout flow.
     */
    fun createLogoutIntent(): Intent? {
        val token = accessToken ?: return null
        val endSessionRequest = EndSessionRequest.Builder(serviceConfig)
            .setIdTokenHint(token)
            .setPostLogoutRedirectUri(redirectUri)
            .build()

        return authService.getEndSessionRequestIntent(endSessionRequest)
    }

    /** Clears the local token state. */
    fun clearSession() {
        accessToken = null
        refreshToken = null
    }

    /** Release resources when no longer needed. */
    fun dispose() {
        authService.dispose()
    }
}
