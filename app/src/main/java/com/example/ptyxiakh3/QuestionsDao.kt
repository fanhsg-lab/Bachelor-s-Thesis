package com.example.ptyxiakh3
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ptyxiakh3.data.Question


@Dao
interface QuestionsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addQuestion(question: Question)

    @Delete
    suspend fun deleteQuestion(question: Question)

    @Query("DELETE FROM Questions")
    suspend fun deleteAllQuestions()

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun addQuestions(questions: List<Question>)


    @Query("SELECT * FROM Questions ORDER BY question_id ASC")
    fun readAllData(): LiveData<List<Question>>

    @Query("SELECT * FROM Questions WHERE quiz = :quizNumber")
    fun getQuestionsByQuizNumber(quizNumber: Double): LiveData<List<Question>>

    @Query("SELECT * FROM Questions WHERE modules LIKE '%' || :module || '%'")
    fun getQuestionsByModule(module: String): LiveData<List<Question>>

    @Query("SELECT * FROM Questions WHERE modules LIKE '%' || :module || '%' AND difficulty = :difficulty")
    fun getQuestionsByModuleAndDifficulty(module: String, difficulty: Int): LiveData<List<Question>>

    @Query("SELECT * FROM Questions WHERE question_id = :id")
    fun getQuestionById(id: Long): LiveData<Question>



}
