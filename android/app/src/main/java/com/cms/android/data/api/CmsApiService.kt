package com.cms.android.data.api

import com.cms.android.data.model.UserProfile
import retrofit2.http.GET

/**
 * Retrofit interface for the CMS backend REST API.
 * Base URL is configured via BuildConfig.API_BASE_URL.
 */
interface CmsApiService {

    /** Get the current authenticated user's profile and roles. */
    @GET("me")
    suspend fun getCurrentUser(): UserProfile
}
