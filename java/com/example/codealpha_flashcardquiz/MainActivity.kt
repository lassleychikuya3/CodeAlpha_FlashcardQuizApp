package com.example.codealpha_flashcardquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.OutlinedTextField
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FlashcardScreen()
        }
    }
}

@Composable
fun FlashcardScreen() {

    val flashcards = remember {
        mutableStateListOf(
            Pair("What is Java?", "A programming language"),
            Pair("What is Flutter?", "Google's UI toolkit"),
            Pair("What is SQL?", "Structured Query Language"),
            Pair("What is Android?", "A mobile operating system")
        )
    }

    var currentIndex by remember { mutableStateOf(0) }
    var showAnswer by remember { mutableStateOf(false) }
    var newQuestion by remember { mutableStateOf("") }
    var newAnswer by remember { mutableStateOf("") }

    val card = flashcards[currentIndex]
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = card.first,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(20.dp))
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                showAnswer = !showAnswer
            }
        ) {
            Text(
                if (showAnswer) "Hide Answer"
                else "Show Answer"
            )
        }

        if (showAnswer) {
            Text(
                text = card.second
            )
        }

        Row {

            Button(
                onClick = {
                    if (currentIndex > 0) {
                        currentIndex--
                        showAnswer = false
                    }
                }
            ) {
                Text("Previous")
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    if (currentIndex < flashcards.size-1) {
                        currentIndex++
                        showAnswer = false
                    }
                }
            ){
                Text("Next")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newQuestion,
            onValueChange = { newQuestion = it },
            placeholder = { Text("Enter Question") }
        )

        Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = newAnswer,
                onValueChange = { newAnswer = it },
                placeholder = { Text("Enter Answer") }
            )
            Button(
                onClick = {
                    if (newQuestion.isNotBlank() && newAnswer.isNotBlank()) {
                        flashcards.add(
                            Pair(newQuestion, newAnswer)
                        )
                        currentIndex = flashcards.size - 1
                        showAnswer = false
                        newQuestion = ""
                        newAnswer = ""
                    }
                }
            ) {
                Text("Add Flashcard")
            }
        Button(
            onClick = {
                flashcards[currentIndex] =
                    Pair(newQuestion, newAnswer)

                newQuestion = ""
                newAnswer = ""
            }
        ) {
            Text("Update Flashcard")
        }
        Button(
            onClick = {
                if (flashcards.isNotEmpty()) {

                    flashcards.removeAt(currentIndex)

                    if (currentIndex >= flashcards.size) {
                        currentIndex = flashcards.size - 1
                    }

                    showAnswer = false
                }
            }
        ) {
            Text("Delete Flashcard")
        }
    }

}