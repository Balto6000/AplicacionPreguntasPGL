package com.example.aplicaciondepreguntas

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.aplicaciondepreguntas.ui.theme.AplicacionDePreguntasTheme

class StatsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplicacionDePreguntasTheme {
                StatsScreen()
            }
        }
    }
}

@Composable
fun StatsScreen() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("StatsPreferences", Context.MODE_PRIVATE)
    val totalCorrect = sharedPreferences.getInt("totalCorrect", 0)
    val totalIncorrect = sharedPreferences.getInt("totalIncorrect", 0)
    val totalClicks = sharedPreferences.getInt("totalClicks", 0)
    val totalQuestions = totalCorrect + totalIncorrect

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Estadísticas del Usuario")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Aciertos totales: $totalCorrect")
        Text("Fallos totales: $totalIncorrect")
        Text("Porcentaje de aciertos: ${(totalCorrect.toFloat() / totalQuestions * 100)}%")
        Text("Porcentaje de fallos: ${(totalIncorrect.toFloat() / totalQuestions * 100)}%")
        Text("Número de clicks totales: $totalClicks")
    }
}

