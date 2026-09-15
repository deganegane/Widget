package com.dokraja.widget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Do kraja", style = MaterialTheme.typography.headlineMedium)
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "Widget dodaješ dugim pritiskom na prazan dio početnog zaslona → Widgeti → Do kraja.\n\n" +
                            "Postavke (datum i boja) otvaraju se pri dodavanju, a kasnije dugim pritiskom na sam widget → Uredi.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
