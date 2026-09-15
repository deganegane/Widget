package com.dokraja.widget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class WidgetConfigActivity : ComponentActivity() {

    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_CANCELED)

        appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        setContent {
            MaterialTheme {
                Surface {
                    ConfigScreen(widgetId = appWidgetId, onDone = { finishConfig() })
                }
            }
        }
    }

    private fun finishConfig() {
        lifecycleScope.launch {
            DoKrajaWidget().updateAll(this@WidgetConfigActivity)
        }
        val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(RESULT_OK, resultValue)
        finish()
    }
}

@Composable
private fun ConfigScreen(widgetId: Int, onDone: () -> Unit) {
    val context = LocalContext.current

    var mode by remember { mutableStateOf(WidgetPrefs.getMode(context, widgetId)) }
    var preset by remember { mutableStateOf(WidgetPrefs.getPreset(context, widgetId)) }
    var endDate by remember { mutableStateOf(WidgetPrefs.getEndDate(context, widgetId)) }
    var seedPreview by remember {
        mutableStateOf(WidgetPrefs.getCustomSeed(context, widgetId)?.let { Color(it) })
    }

    val pickImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val activity = context as ComponentActivity
            activity.lifecycleScope.launch {
                val seed = extractSeedColor(context, uri)
                if (seed != null) {
                    WidgetPrefs.setCustomSeed(context, widgetId, seed)
                    mode = "custom"
                    seedPreview = Color(seed)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Postavke widgeta", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))

        Text("Zadnji dan nastave (GGGG-MM-DD)", style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = endDate,
            onValueChange = { endDate = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))
        Text("Boja", style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(8.dp))

        Row {
            FilterChip(selected = mode == "dynamic", onClick = { mode = "dynamic" }, label = { Text("Sustav") })
            Spacer(Modifier.width(8.dp))
            FilterChip(selected = mode == "preset", onClick = { mode = "preset" }, label = { Text("Presetovi") })
            Spacer(Modifier.width(8.dp))
            FilterChip(selected = mode == "custom", onClick = { mode = "custom" }, label = { Text("Iz slike") })
        }

        Spacer(Modifier.height(16.dp))

        if (mode == "preset") {
            Row {
                PRESET_PALETTES.keys.forEach { key ->
                    val swatchColor = PRESET_PALETTES.getValue(key).light.primary
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(swatchColor)
                            .border(
                                width = if (preset == key) 3.dp else 0.dp,
                                color = MaterialTheme.colorScheme.onSurface,
                                shape = CircleShape
                            )
                            .clickable { preset = key }
                    )
                }
            }
        }

        if (mode == "custom") {
            Button(onClick = { pickImage.launch("image/*") }) {
                Text("Odaberi sliku")
            }
            seedPreview?.let {
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(it)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Izvučena boja iz slike", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                WidgetPrefs.setEndDate(context, widgetId, endDate)
                WidgetPrefs.setMode(context, widgetId, mode)
                WidgetPrefs.setPreset(context, widgetId, preset)
                onDone()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Spremi")
        }
    }
}
