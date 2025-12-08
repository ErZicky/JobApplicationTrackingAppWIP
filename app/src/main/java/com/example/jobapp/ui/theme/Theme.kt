package com.example.jobapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = CPrimary,
    onPrimary = CText,

    secondary = CSecondary,
    onSecondary = CSecondary,

    tertiary = CAccent,
    onTertiary = CText,


    background = CBackgroundColor,
    onBackground = CText,

    surface = CSurface,
    onSurface = CText,

    error = CError,
    onError = Color.White,

    outline = CDividers,


)

// --- FUNZIONE COMPONIBILE DEL TEMA ---

@Composable
fun JobAppTheme(
    darkTheme: Boolean = false, // This app will have just the white theme for now
    // TODO: decide about dynamic colors
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // 1. Priorità al Colore Dinamico (se abilitato e disponibile)
        dynamicColor -> {
            val context = LocalContext.current
            dynamicLightColorScheme(context)
        }
        // 2. Fallback: Usa sempre il tuo schema di colori Light personalizzato
        else -> LightColorScheme
    }
    val view = LocalView.current
    SideEffect {

        val window = (view.context as? Activity)?.window ?: return@SideEffect

        WindowCompat.getInsetsController(window, view)?.let { controller ->

           //status bar icons (i want them dark so true because they are on the light theme)
            controller.isAppearanceLightStatusBars = true

            //navigation bar/buttons
            controller.isAppearanceLightNavigationBars = false
        }
    }


    // Questa funzione MaterialTheme applica i colori, la tipografia e le forme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Definito in Typography.kt
        content = content
    )
}