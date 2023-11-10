package com.example.aplicaciondepreguntas

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aplicaciondepreguntas.ui.theme.AplicacionDePreguntasTheme

data class ExamQuestion(val text: String, val answer: Boolean, val images: Int, var responded: Boolean = false, var userAnswer: Boolean? = null
)

class ExamApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplicacionDePreguntasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExamApp()
                }
            }
        }
    }
}

@Composable
fun ExamApp(navController: NavHostController) {
    var quizFinished by remember { mutableStateOf(false) }
    val questions = remember { mutableStateListOf(
        ExamQuestion("El color verde en hexadecimal es #008000.", true, R.drawable.verde),
        ExamQuestion("El número binario 1010100001 en octal es 1241.", true, R.drawable.binaryoctal),
        ExamQuestion("El número π (pi) es la relación entre la longitud de una hipotenusa y su diámetro.", false, R.drawable.hipotenusa),
        ExamQuestion("Un palíndromo es una sílaba tónica compuesta por dos vocales unidas.", false, R.drawable.palindromo),
        ExamQuestion("El lémur ratón de Berthe es el primate más pequeño.", true, R.drawable.lemur)
    ) }
    var currentQuestionIndex by remember { mutableStateOf(0) }

    fun onAnswerSelected(index: Int, userAnswer: Boolean) {
        if (!questions[index].responded) {
            questions[index].responded = true
            questions[index].userAnswer = userAnswer

            if (questions.all { it.responded }) {
                quizFinished = true
            }
        }
    }

    fun resetQuiz() {
        questions.forEach { question ->
            question.responded = false
            question.userAnswer = null
        }
        currentQuestionIndex = 0
        quizFinished = false
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = questions[currentQuestionIndex].text,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = questions[currentQuestionIndex].images),
            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )

        Row {
            Button(
                onClick = { onAnswerSelected(currentQuestionIndex, true) },
                modifier = Modifier.padding(8.dp),
            ) {
                Text("TRUE")
            }

            Button(
                onClick = { onAnswerSelected(currentQuestionIndex, false) },
                modifier = Modifier.padding(8.dp),
            ) {
                Text("FALSE")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = {
                    currentQuestionIndex = maxOf(currentQuestionIndex - 1, 0)
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                Text("PREV")
            }

            Button(
                onClick = {
                    currentQuestionIndex = minOf(currentQuestionIndex + 1, questions.size - 1)
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                Text("NEXT")
            }
        }

        if (quizFinished) {
            val totalAnsweredCorrectly = questions.count { it.responded && it.userAnswer == it.answer }
            val totalQuestions = questions.size
            val message = when {
                totalAnsweredCorrectly == totalQuestions -> "¡Felicidades! Has respondido correctamente a todas las preguntas."
                totalAnsweredCorrectly >= 3 -> "Has aprobado el examen."
                else -> "Has suspendido el examen."
            }

            AlertDialog(
                onDismissRequest = { },
                title = { Text("Resultado del Examen") },
                text = { Text(message) },
                confirmButton = {
                    Button(
                        onClick = {
                            resetQuiz()
                        }
                    ) {
                        Text("Reiniciar el Examen")
                    }
                }
            )
        }
    }
}
