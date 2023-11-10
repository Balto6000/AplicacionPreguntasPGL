package com.example.aplicaciondepreguntas

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aplicaciondepreguntas.ui.theme.AplicacionDePreguntasTheme

data class Question(val text: String, val answer: Boolean, val images: Int)

class QuestionsApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplicacionDePreguntasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QuestionsApp()
                }
            }
        }
    }
}


@Composable
fun QuestionsApp(navController: NavHostController) {
    val context = LocalContext.current

    val questions = remember { mutableStateListOf(
        Question("El color verde en hexadecimal es #008000.", true, R.drawable.verde),
        Question("El número binario 1010100001 en octal es 1241.", true, R.drawable.binaryoctal),
        Question("El número π (pi) es la relación entre la longitud de una hipotenusa y su diámetro.", false, R.drawable.hipotenusa),
        Question("Un palíndromo es una sílaba tónica compuesta por dos vocales unidas.", false, R.drawable.palindromo),
        Question("El lémur ratón de Berthe es el primate más pequeño.", true, R.drawable.lemur)
    ) }

    var currentIndex by remember { mutableStateOf(0) }
    var feedback by remember { mutableStateOf("") }

    fun onAnswerSelected(userAnswer: Boolean, correctAnswer: Boolean) {
        feedback = if (userAnswer == correctAnswer) "Correcto" else "Incorrecto"

        val sharedPreferences = context.getSharedPreferences("StatsPreferences", Context.MODE_PRIVATE)
        val totalCorrect = sharedPreferences.getInt("totalCorrect", 0)
        val totalIncorrect = sharedPreferences.getInt("totalIncorrect", 0)
        val totalClicks = sharedPreferences.getInt("totalClicks", 0)

        val editor = sharedPreferences.edit()
        editor.putInt("totalClicks", totalClicks + 1)
        if (userAnswer == correctAnswer) {
            editor.putInt("totalCorrect", totalCorrect + 1)
        } else {
            editor.putInt("totalIncorrect", totalIncorrect + 1)
        }
        editor.apply()
    }

    fun showRandomQuestion() {
        currentIndex = (0 until questions.size).random()
        feedback = ""
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = questions[currentIndex].text,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = questions[currentIndex].images),
            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )

        Row {
            Button(
                onClick = { onAnswerSelected(true, questions[currentIndex].answer) },
                modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (feedback == "Correcto" && questions[currentIndex].answer == true) {
                        Color.Green
                    } else if (feedback == "Incorrecto" && questions[currentIndex].answer == false) {
                        Color.Red
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
            ) {
                Text("TRUE")
            }

            Button(
                onClick = { onAnswerSelected(false, questions[currentIndex].answer) },
                modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (feedback == "Correcto" && questions[currentIndex].answer == false) {
                        Color.Green
                    } else if (feedback == "Incorrecto" && questions[currentIndex].answer == true) {
                        Color.Red
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
            ) {
                Text("FALSE")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (feedback.isNotEmpty()) {
            Text(
                text = feedback,
                color = if (feedback == "Correcto") Color.Green else Color.Red
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = {
                    currentIndex = (currentIndex - 1 + questions.size) % questions.size
                    feedback = ""
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                Text("PREV")
            }

            Button(
                onClick = {
                    currentIndex = (currentIndex + 1) % questions.size
                    feedback = ""
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                Text("NEXT")
            }
        }

        Button(
            onClick = { showRandomQuestion() },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("RANDOM")
        }

        Button(
            onClick = {
                navController.navigate("stats")
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Estadísticas")
        }
    }
}