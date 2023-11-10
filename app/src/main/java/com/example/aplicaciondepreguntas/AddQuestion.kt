package com.example.aplicaciondepreguntas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aplicaciondepreguntas.ui.theme.AplicacionDePreguntasTheme
import java.io.FileOutputStream
import java.io.IOException

class AddQuestion : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplicacionDePreguntasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddQuestion()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuestion(navController: NavHostController) {
    val context = LocalContext.current
    var questionText by remember { mutableStateOf("") }
    var answerText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = questionText,
            onValueChange = { questionText = it },
            label = { Text("Enunciado de la pregunta") },
            modifier = Modifier.padding(16.dp)
        )

        TextField(
            value = answerText,
            onValueChange = { answerText = it },
            label = { Text("Respuesta (TRUE o FALSE)") },
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = {
                saveQuestion(questionText, answerText)
                Toast.makeText(
                    context,
                    "Pregunta guardada exitosamente",
                    Toast.LENGTH_SHORT
                ).show()
                navController.popBackStack()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Guardar Pregunta")
        }
    }
}

private fun saveQuestion(questionText: String, answerText: String) {
    try {
        val fileName = "questions.txt"
        val fileOutputStream = FileOutputStream(fileName, true)
        val questionLine = "$questionText,$answerText\n"
        fileOutputStream.write(questionLine.toByteArray())
        fileOutputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
