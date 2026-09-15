package com.dokraja.widget

import android.content.Context
import android.graphics.ImageDecoder
import android.graphics.Color as AndroidColor
import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Izvlači jednu "seed" boju iz slike koju korisnik odabere, koristeći
 * službeni androidx Palette API (vibrant/dominant/muted swatch, tim
 * redoslijedom prioriteta).
 *
 * Napomena: ovo NIJE isti HCT algoritam koji koristi pravi Android Material
 * You sustav (taj dio već pokriva "Sustav" način preko GlanceTheme).
 * Ovo je pragmatična aproksimacija preko dokumentiranog Palette API-ja,
 * dovoljno dobra za "boja iz moje slike" opciju.
 */
suspend fun extractSeedColor(context: Context, uri: Uri): Int? = withContext(Dispatchers.IO) {
    runCatching {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        val bitmap = ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
            decoder.setTargetSampleSize(4)
        }
        val palette = Palette.from(bitmap).generate()
        palette.vibrantSwatch?.rgb
            ?: palette.dominantSwatch?.rgb
            ?: palette.mutedSwatch?.rgb
    }.getOrNull()
}

private fun tone(hue: Float, sat: Float, value: Float): Color {
    val rgb = AndroidColor.HSVToColor(floatArrayOf(hue, sat.coerceIn(0f, 1f), value.coerceIn(0f, 1f)))
    return Color(rgb)
}

/** Generira punu paletu (light + dark, primary/secondary/tertiary container) iz jedne seed boje. */
fun schemeFromSeed(seedColorInt: Int): ThemePalette {
    val hsv = FloatArray(3)
    AndroidColor.colorToHSV(seedColorInt, hsv)
    val hue = hsv[0]
    val hue2 = (hue + 40f) % 360f
    val hue3 = (hue + 320f) % 360f

    val light = SchemeColors(
        primary = tone(hue, 0.55f, 0.45f),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = tone(hue, 0.18f, 0.96f),
        onPrimaryContainer = tone(hue, 0.55f, 0.28f),
        secondaryContainer = tone(hue2, 0.15f, 0.93f),
        onSecondaryContainer = tone(hue2, 0.35f, 0.25f),
        tertiaryContainer = tone(hue3, 0.15f, 0.93f),
        onTertiaryContainer = tone(hue3, 0.35f, 0.25f)
    )
    val dark = SchemeColors(
        primary = tone(hue, 0.4f, 0.85f),
        onPrimary = tone(hue, 0.55f, 0.22f),
        primaryContainer = tone(hue, 0.35f, 0.32f),
        onPrimaryContainer = tone(hue, 0.15f, 0.92f),
        secondaryContainer = tone(hue2, 0.25f, 0.30f),
        onSecondaryContainer = tone(hue2, 0.12f, 0.90f),
        tertiaryContainer = tone(hue3, 0.25f, 0.30f),
        onTertiaryContainer = tone(hue3, 0.12f, 0.90f)
    )
    return ThemePalette(light, dark)
}
