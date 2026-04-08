package com.cms.android.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = CmsPrimary,
    onPrimary = CmsOnPrimary,
    primaryContainer = CmsPrimaryContainer,
    onPrimaryContainer = CmsOnPrimaryContainer,
    secondary = CmsSecondary,
    onSecondary = CmsOnSecondary,
    secondaryContainer = CmsSecondaryContainer,
    onSecondaryContainer = CmsOnSecondaryContainer,
    tertiary = CmsTertiary,
    onTertiary = CmsOnTertiary,
    tertiaryContainer = CmsTertiaryContainer,
    onTertiaryContainer = CmsOnTertiaryContainer,
    error = CmsError,
    onError = CmsOnError,
    errorContainer = CmsErrorContainer,
    onErrorContainer = CmsOnErrorContainer,
    background = CmsBackground,
    onBackground = CmsOnBackground,
    surface = CmsSurface,
    onSurface = CmsOnSurface,
    surfaceVariant = CmsSurfaceVariant,
    onSurfaceVariant = CmsOnSurfaceVariant,
    outline = CmsOutline,
)

private val DarkColorScheme = darkColorScheme(
    primary = CmsPrimaryDark,
    onPrimary = CmsOnPrimaryDark,
    primaryContainer = CmsPrimaryContainerDark,
    onPrimaryContainer = CmsOnPrimaryContainerDark,
    secondary = CmsSecondaryDark,
    onSecondary = CmsOnSecondaryDark,
    secondaryContainer = CmsSecondaryContainerDark,
    onSecondaryContainer = CmsOnSecondaryContainerDark,
    background = CmsBackgroundDark,
    onBackground = CmsOnBackgroundDark,
    surface = CmsSurfaceDark,
    onSurface = CmsOnSurfaceDark,
)

@Composable
fun CmsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
