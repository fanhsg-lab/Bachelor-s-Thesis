package com.example.duolingo.Models

class ProfileModel(
    var name: String,
    var email: String,
    var bookmarksCount: Int,
    var qHistory: String,
    val quizs: MutableList<String> = mutableListOf(),
    var score: Int
)
