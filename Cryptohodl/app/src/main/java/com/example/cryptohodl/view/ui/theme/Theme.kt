package com.example.cryptohodl.view.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class CustomColors(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val secondaryVariant: Color,
    val surfaceA: Color,
    val surfaceB: Color,
    val gainDefault: Color,
    val gainGood: Color,
    val gainBad: Color
)

@Immutable
data class CustomTypography(
    val small: TextStyle,
    val medium: TextStyle,
    val big: TextStyle
)

val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        primary = Color.Unspecified,
        primaryVariant = Color.Unspecified,
        secondary = Color.Unspecified,
        secondaryVariant = Color.Unspecified,
        surfaceA = Color.Unspecified,
        surfaceB = Color.Unspecified,
        gainDefault = Color.Unspecified,
        gainGood = Color.Unspecified,
        gainBad = Color.Unspecified
    )
}
val LocalCustomTypography = staticCompositionLocalOf {
    CustomTypography(
        small = TextStyle.Default,
        medium = TextStyle.Default,
        big = TextStyle.Default
    )
}

@Composable
fun CryptohodlTheme(content: @Composable() () -> Unit) {
    val customColors = CustomColors(
        primary = blue,
        primaryVariant = blueLight,
        secondary = purple,
        secondaryVariant = purpleLight,
        surfaceA = black,
        surfaceB = blackSurface,
        gainDefault = gray,
        gainGood = green,
        gainBad = red,
    )
    val customTypography = CustomTypography(
        small = TextStyle(fontSize = 14.sp, fontFamily = Montserrat),
        medium = TextStyle(fontSize = 16.sp, fontFamily = Montserrat),
        big = TextStyle(fontSize = 18.sp, fontFamily = Montserrat, fontWeight = FontWeight.Bold),
    )
    CompositionLocalProvider(
        LocalCustomColors provides customColors,
        LocalCustomTypography provides customTypography,
        content = content
    )
}

object CustomTheme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current
    val typography: CustomTypography
        @Composable
        get() = LocalCustomTypography.current
}