import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ptyxiakh3.QuestionsDatabase
import com.example.ptyxiakh3.QuestionsRepository
import com.example.ptyxiakh3.data.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionsViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Question>>
    private val repository: QuestionsRepository

    init {
        val questionsDao = QuestionsDatabase.getDatabase(application, viewModelScope).questionsDao()
        repository = QuestionsRepository(questionsDao)
        readAllData = repository.readAllData
    }

    fun getQuestionsByQuizNumber(quizNumber: Double): LiveData<List<Question>> {
        return repository.getQuestionsByQuizNumber(quizNumber)
    }

    fun getQuestionsByModule(module: String): LiveData<List<Question>> {
        return repository.getQuestionsByModule(module)
    }


    fun addQuestion(question: Question) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addQuestion(question)
        }
    }

    fun deleteQuestion(question: Question) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteQuestion(question)
        }
    }

    fun deleteAllQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllQuestions()
        }
    }

    fun getQuestionById(id: Long): LiveData<Question> {
        return repository.getQuestionById(id)
    }



    fun getQuestionsByModuleAndDifficulty(module: String, difficulty: Int): LiveData<List<Question>> {
        return repository.getQuestionsByModuleAndDifficulty(module, difficulty)
    }



}
