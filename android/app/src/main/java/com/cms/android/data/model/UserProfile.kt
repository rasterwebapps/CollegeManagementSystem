package com.cms.android.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents user profile data returned from the /api/v1/me endpoint.
 */
@Serializable
data class UserProfile(
    val sub: String,
    val name: String,
    val email: String? = null,
    val roles: List<String> = emptyList(),
    val timestamp: String? = null,
) {
    /** Display-friendly name, falling back to subject ID. */
    val displayName: String
        get() = name.ifBlank { sub }

    /** Primary CMS role for determining the dashboard view. */
    val primaryRole: CmsRole
        get() = roles.firstNotNullOfOrNull { CmsRole.fromString(it) } ?: CmsRole.UNKNOWN
}

/** Roles supported by the CMS backend. */
enum class CmsRole(val roleString: String, val displayName: String) {
    ADMIN("ROLE_ADMIN", "Administrator"),
    FACULTY("ROLE_FACULTY", "Faculty / Teacher"),
    STUDENT("ROLE_STUDENT", "Student"),
    PARENT("ROLE_PARENT", "Parent"),
    DRIVER("ROLE_DRIVER", "Driver"),
    LAB_INCHARGE("ROLE_LAB_INCHARGE", "Lab In-Charge"),
    TECHNICIAN("ROLE_TECHNICIAN", "Technician"),
    UNKNOWN("", "Unknown");

    companion object {
        fun fromString(role: String): CmsRole? =
            entries.firstOrNull { it.roleString == role }
    }
}
