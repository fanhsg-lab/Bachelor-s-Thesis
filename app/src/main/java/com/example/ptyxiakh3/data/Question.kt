package com.example.ptyxiakh3.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Questions")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val question_id: Long = 0,
    val question_number:String="0.0",
    val question_text: String = "",
    val question_text2: String ="",
    val question_module: List<String> = emptyList(),
    val quiz: Double = 0.0,
    val difficulty: Int = 0,
    val style: String = "",
    val modules: List<String> = emptyList(),
    val possibleAnswers: List<String> = emptyList(), // List of possible answers
    val correctAnswers: List<Long> = emptyList(), // List of correct answers (assuming they are indices of possible answers)
    val image: Int = 0,
)


