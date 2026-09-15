package com.dokraja.widget

import android.content.Context
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.material3.ColorProviders
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

private val DEFAULT_END_DATE: LocalDate = LocalDate.of(2027, 1, 1)

// Praznici za Međimursku županiju, u ISO formatu (yyyy-MM-dd)
private val HOLIDAYS: Set<String> = setOf(
    "2026-11-02", "2026-11-03",
    "2026-11-18",
    "2026-12-24", "2026-12-25", "2026-12-28", "2026-12-29", "2026-12-30", "2026-12-31",
    "2027-01-01", "2027-01-04", "2027-01-05", "2027-01-06",
    "2027-02-22", "2027-02-23", "2027-02-24", "2027-02-25", "2027-02-26",
    "2027-03-25", "2027-03-26", "2027-03-29", "2027-03-30", "2027-03-31", "2027-04-01", "2027-04-02",
    "2027-05-01",
    "2027-05-27",
    "2027-06-22"
)

private data class Stats(val total: Long, val school: Int, val saturdays: Int)

private fun calculateStats(endDate: LocalDate): Stats {
    val today = LocalDate.now()
    if (!today.isBefore(endDate)) return Stats(0, 0, 0)

    val total = ChronoUnit.DAYS.between(today, endDate)

    var school = 0
    var saturdays = 0
    var current = today
    while (current.isBefore(endDate)) {
        val dow = current.dayOfWeek
        val isWeekend = dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY
        val isHoliday = HOLIDAYS.contains(current.toString())
        if (!isWeekend && !isHoliday) school++
        if (dow == DayOfWeek.SATURDAY) saturdays++
        current = current.plusDays(1)
    }
    return Stats(total, school, saturdays)
}

private fun paletteToColorProviders(palette: ThemePalette): ColorProviders {
    return ColorProviders(
        light = lightColorScheme(
            primary = palette.light.primary,
            onPrimary = palette.light.onPrimary,
            primaryContainer = palette.light.primaryContainer,
            onPrimaryContainer = palette.light.onPrimaryContainer,
            secondaryContainer = palette.light.secondaryContainer,
            onSecondaryContainer = palette.light.onSecondaryContainer,
            tertiaryContainer = palette.light.tertiaryContainer,
            onTertiaryContainer = palette.light.onTertiaryContainer
        ),
        dark = darkColorScheme(
            primary = palette.dark.primary,
            onPrimary = palette.dark.onPrimary,
            primaryContainer = palette.dark.primaryContainer,
            onPrimaryContainer = palette.dark.onPrimaryContainer,
            secondaryContainer = palette.dark.secondaryContainer,
            onSecondaryContainer = palette.dark.onSecondaryContainer,
            tertiaryContainer = palette.dark.tertiaryContainer,
            onTertiaryContainer = palette.dark.onTertiaryContainer
        )
    )
}

class DoKrajaWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val widgetId = GlanceAppWidgetManager(context).getAppWidgetId(id)
        val mode = WidgetPrefs.getMode(context, widgetId)
        val endDate = try {
            LocalDate.parse(WidgetPrefs.getEndDate(context, widgetId))
        } catch (e: DateTimeParseException) {
            DEFAULT_END_DATE
        }

        provideContent {
            when (mode) {
                "preset" -> {
                    val key = WidgetPrefs.getPreset(context, widgetId)
                    val palette = PRESET_PALETTES[key] ?: PRESET_PALETTES.getValue("purple")
                    GlanceTheme(colors = paletteToColorProviders(palette)) {
                        WidgetContent(endDate)
                    }
                }
                "custom" -> {
                    val seed = WidgetPrefs.getCustomSeed(context, widgetId)
                    val palette = seed?.let { schemeFromSeed(it) } ?: PRESET_PALETTES.getValue("purple")
                    GlanceTheme(colors = paletteToColorProviders(palette)) {
                        WidgetContent(endDate)
                    }
                }
                else -> {
                    // "dynamic": prazan GlanceTheme koristi prave Material You
                    // boje sa sustava (iz wallpapera) na Androidu 12+.
                    GlanceTheme {
                        WidgetContent(endDate)
                    }
                }
            }
        }
    }

    @Composable
    private fun WidgetContent(endDate: LocalDate) {
        val stats = calculateStats(endDate)
        Row(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(GlanceTheme.colors.background)
                .cornerRadius(24.dp)
                .padding(8.dp)
        ) {
            StatCard(
                value = stats.total.toString(),
                label = "Ukupno",
                background = GlanceTheme.colors.primaryContainer,
                textColor = GlanceTheme.colors.onPrimaryContainer,
                modifier = GlanceModifier.defaultWeight()
            )
            Spacer(modifier = GlanceModifier.width(6.dp))
            StatCard(
                value = stats.school.toString(),
                label = "Školskih",
                background = GlanceTheme.colors.tertiaryContainer,
                textColor = GlanceTheme.colors.onTertiaryContainer,
                modifier = GlanceModifier.defaultWeight()
            )
            Spacer(modifier = GlanceModifier.width(6.dp))
            StatCard(
                value = stats.saturdays.toString(),
                label = "Subota",
                background = GlanceTheme.colors.secondaryContainer,
                textColor = GlanceTheme.colors.onSecondaryContainer,
                modifier = GlanceModifier.defaultWeight()
            )
        }
    }

    @Composable
    private fun StatCard(
        value: String,
        label: String,
        background: ColorProvider,
        textColor: ColorProvider,
        modifier: GlanceModifier = GlanceModifier
    ) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .background(background)
                .cornerRadius(18.dp)
                .padding(8.dp),
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            Text(
                text = value,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            )
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 10.sp,
                    color = textColor
                )
            )
        }
    }
}
