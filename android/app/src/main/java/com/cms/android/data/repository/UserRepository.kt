package com.cms.android.data.repository

import com.cms.android.data.api.RetrofitClient
import com.cms.android.data.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for user-related API operations.
 */
class UserRepository {

    private val api = RetrofitClient.apiService

    /** Fetch the currently authenticated user's profile from the backend. */
    suspend fun getCurrentUser(): Result<UserProfile> = withContext(Dispatchers.IO) {
        runCatching { api.getCurrentUser() }
    }
}
