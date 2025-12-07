package com.example.jobapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


//reference to default android typgraphy
val DefaultTypography = Typography()

// per il momento usiamo il font predefinito (default) di Android.
// Se hai font personalizzati, devi definirli qui prima (ad esempio: val MyFont = FontFamily(Font(R.font.my_font_file))).
val AppFontFamily = FontFamily.Default

// Definisci lo stile della Tipografia
val Typography = Typography(
    // Titolo più grande per schermi grandi
    displayLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),

    // Titoli principali (es. nella barra superiore)
    headlineLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold, // Puoi impostare il grassetto qui
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),

    // Testo del corpo principale
    bodyLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    // Testo per bottoni e tab
    labelLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )




)