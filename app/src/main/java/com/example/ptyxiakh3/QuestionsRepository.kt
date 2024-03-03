package com.example.ptyxiakh3


import androidx.lifecycle.LiveData
import com.example.ptyxiakh3.data.Question

class QuestionsRepository(private val questionsDao: QuestionsDao) {

    val readAllData: LiveData<List<Question>> = questionsDao.readAllData()

    suspend fun addQuestion(question: Question){
        questionsDao.addQuestion(question)
    }

    suspend fun deleteQuestion (question: Question){
        questionsDao.deleteQuestion(question)
    }

    suspend fun deleteAllQuestions(){
        questionsDao.deleteAllQuestions()
    }

    fun getQuestionsByQuizNumber(quizNumber: Double): LiveData<List<Question>> {
        return questionsDao.getQuestionsByQuizNumber(quizNumber)
    }

    fun getQuestionsByModule(module: String): LiveData<List<Question>> {
        return questionsDao.getQuestionsByModule(module)
    }

    fun getQuestionsByModuleAndDifficulty(module: String, difficulty: Int): LiveData<List<Question>> {
        return questionsDao.getQuestionsByModuleAndDifficulty(module, difficulty)
    }

    fun getQuestionById(id: Long): LiveData<Question> {
        return questionsDao.getQuestionById(id)
    }

}