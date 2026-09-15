package com.dokraja.widget

import android.content.Context
import androidx.core.content.edit

object WidgetPrefs {
    private const val PREFS_NAME = "do_kraja_widget_prefs"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // mode: "dynamic" | "preset" | "custom"
    fun getMode(context: Context, widgetId: Int): String =
        prefs(context).getString("mode_$widgetId", "dynamic") ?: "dynamic"

    fun setMode(context: Context, widgetId: Int, mode: String) {
        prefs(context).edit { putString("mode_$widgetId", mode) }
    }

    fun getPreset(context: Context, widgetId: Int): String =
        prefs(context).getString("preset_$widgetId", "purple") ?: "purple"

    fun setPreset(context: Context, widgetId: Int, preset: String) {
        prefs(context).edit { putString("preset_$widgetId", preset) }
    }

    fun getCustomSeed(context: Context, widgetId: Int): Int? {
        val value = prefs(context).getInt("custom_seed_$widgetId", 0)
        return if (value == 0) null else value
    }

    fun setCustomSeed(context: Context, widgetId: Int, colorInt: Int) {
        prefs(context).edit { putInt("custom_seed_$widgetId", colorInt) }
    }

    fun getEndDate(context: Context, widgetId: Int): String =
        prefs(context).getString("end_date_$widgetId", "2027-01-01") ?: "2027-01-01"

    fun setEndDate(context: Context, widgetId: Int, date: String) {
        prefs(context).edit { putString("end_date_$widgetId", date) }
    }

    fun clear(context: Context, widgetId: Int) {
        prefs(context).edit {
            remove("mode_$widgetId")
            remove("preset_$widgetId")
            remove("custom_seed_$widgetId")
            remove("end_date_$widgetId")
        }
    }
}
