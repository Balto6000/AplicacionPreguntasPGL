package com.example.aplicaciondepreguntas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplicaciondepreguntas.ui.theme.AplicacionDePreguntasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplicacionDePreguntasTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "mainMenu") {
                    composable("mainMenu") {
                        MainMenu(navController)
                    }
                    composable("exam") {
                        ExamApp(navController)
                    }
                    composable("practice") {
                        QuestionsApp(navController)
                    }
                    composable("stats") {
                        StatsScreen()
                    }
                    composable("addQuestion") {
                        AddQuestion(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun MainMenu(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navController.navigate("exam")
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Modo Examen")
        }

        Button(
            onClick = {
                navController.navigate("practice")
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Modo Práctica")
        }
        Button(
            onClick = {
                navController.navigate("addQuestion")
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Agregar Pregunta")
        }

    }
}
