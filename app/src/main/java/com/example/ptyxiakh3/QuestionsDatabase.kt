package com.example.ptyxiakh3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ptyxiakh3.data.Question

import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Question::class], version = 1, exportSchema = false)
@TypeConverters(StringListTypeConverter::class, LongListTypeConverter::class)
abstract class QuestionsDatabase : RoomDatabase() {
    abstract fun questionsDao(): QuestionsDao

    companion object {
        @Volatile
        private var INSTANCE: QuestionsDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): QuestionsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuestionsDatabase::class.java,
                    "questions_database"
                )
                    .addCallback(QuestionsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class QuestionsDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.questionsDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(questionsDao: QuestionsDao) {
            // Dummy data
            val question1 = Question(
                question_number =1.1,
                question_text =
                        "διάβασε α , β\n" +
                        "αν α * 0 τότε\n" +
                        "\t\t\t\tχ <---- β / α\n" +
                        "\t\t\t\tγράψε χ\n" +
                        "αλλιώς ! η εξίσωση έχει τη μορφή 0χ+β=0\n" +
                        "\t\t\t\tαν β # 0 τότε\n" +
                        "\t\t\t\t\t\t\t\tγράψε ‘αδύνατη’\n" +
                        "\t\t\t\tαλλιώς ! η εξίσωση έχει τη μορφή 0χ+0=0\n" +
                        "\t\t\t\t\t\t\t\tγράψε ‘αόριστη’ \n" +
                        "\t\t\t\tτέλοςαν\n" +
                        "τέλοςαν" ,
                question_text2=" ",
                quiz = 1.1,
                difficulty = 2,
                style = "SouLou",
                modules = listOf("Geography", "Europe","Chapter1"),
                possibleAnswers = listOf("Bρίσκει αυτός ο αλγόριθμος το αποτέλεσμα πρωτοβάθμιας εκπαίδευσης?"),
                correctAnswers = listOf(1) // Paris
            )
            val question2 = Question(
                question_number =1.2,
                question_text = "διάβασε α , β\n" +
                        "αν α * 0 τότε \n" +
                        "\t\t\t\t[____] [____] [____]\n" +
                        "\t\t\t\tγράψε χ\n" +
                        "αλλιώς \n" +
                        "\t\t\t\t[____] [____] [____]\n" +
                        "\t\t\t\t\t\t\t\tγράψε ‘αδύνατη’\n" +
                        "\t\t\t\tαλλιώς \n" +
                        "\t\t\t\t\t\t\t\tγράψε ‘αόριστη’ \n" +
                        "\t\t\t\tτέλοςαν\n" +
                        "τέλοςαν",
                question_text2="Bρίσκει αυτός ο αλγόριθμος το αποτέλεσμα πρωτοβάθμιας εκπαίδευσης? ",
                quiz = 1.1,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Mathematics","Chapter1", "Multiplication"),
                possibleAnswers = listOf("x", "<--", "b*a" ,"an","b<>0","tote"),
                correctAnswers = listOf(123456) // 9
            )


            val question3 = Question(
                question_number =1.3,
                question_text =
                "διάβασε α , β \n" +
                        "αν α * 0 τότε \n" +
                        "\t\t\t\t χ <---- β * α \n" +
                        "\t\t\t\t γράψε χ \n" +
                        "αλλιώς ! η εξίσωση έχει τη μορφή 0χ+β=0 \n" +
                        "\t\t\t\t αν β # 0 τότε \n" +
                        "\t\t\t\t\t\t\t\t γράψε ‘αόριστη’ \n" +
                        "\t\t\t\t αλλιώς ! η εξίσωση έχει τη μορφή 0χ+0=0 \n" +
                        "\t\t\t\t\t\t\t\t γράψε ‘αδύνατη’ \n" +
                        "\t\t\t\t τέλοςαν \n" +
                        "τέλοςαν" ,
                question_text2="Βρες τα λάθη για την πρωτομαθμια εκπαίδευση",
                quiz = 1.1,
                difficulty = 3,
                style = "Mistakes",
                modules = listOf("History", "Wars","Chapter1"),
                possibleAnswers = listOf("αδύνατη", "αόριστη", "*"),
                correctAnswers = listOf(1) // 1945
            )

            val question4 = Question(
                question_number =1.4,
                question_text = "Choose two of the most important things",
                question_text2=" ",
                quiz = 1.1,
                difficulty = 2,
                style = "multiple choice",
                modules = listOf("Science", "Chemistry","Chapter1"),
                possibleAnswers = listOf("Oxygen", "Hydrogen", "Carbon Dioxide", "Nitrogen"),
                correctAnswers = listOf(3) // Nitrogen
            )




            val question5 = Question(
                question_number =2.1,
                question_text = "Question with ID 5",
                question_text2=" ",
                quiz = 1.1,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("Literature", "Books","Chapter2"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question6 = Question(
                question_number =2.2,
                question_text =
                "διάβασε α , β \n" +
                        "αν α * 0 τότε \n" +
                        "\t\t\t\t χ <---- β * α \n" +
                        "\t\t\t\t γράψε χ \n" +
                        "αλλιώς ! η εξίσωση έχει τη μορφή 0χ+β=0 \n" +
                        "\t\t\t\t αν β # 0 τότε \n" +
                        "\t\t\t\t\t\t\t\t γράψε ‘αόριστη’ \n" +
                        "\t\t\t\t αλλιώς \n" +
                        "\t\t\t\t\t\t\t\t γράψε ‘αδύνατη’ \n" +
                        "\t\t\t\t τέλοςαν \n" +
                        "τέλοςαν" ,
                question_text2="φτιάξε τον αλγόριθμο της ",
                quiz = 1.2,
                difficulty = 2,
                style = "Queue",
                modules = listOf("Geography", "Europe","Chapter2"),
                possibleAnswers = listOf("αν α * 0 τότε","διάβασε α , β",  "γράψε χ", "αλλιώς ","αν β # 0 τότε" ,"γράψε ‘αόριστη’","8","9","10","11","12","13"), correctAnswers = listOf(14235678910111213) // Paris
            )

            val question7 = Question(
                question_number =2.3,
                question_text = "Question with ID 7",
                question_text2=" ",
                quiz = 1.2,
                difficulty = 1,
                style = "multiple choice",
                modules = listOf("Mathematics", "Multiplication","Chapter2"),
                possibleAnswers = listOf("6", "9", "12", "15"),
                correctAnswers = listOf(1) // 9
            )




            val question8 = Question(
                question_number =3.1,
                question_text = "Question with ID 8",
                question_text2=" ",
                quiz = 1.2,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("History", "Wars","Chapter3"),
                possibleAnswers = listOf("1941", "1945", "1949", "1955"),
                correctAnswers = listOf(1) // 1945
            )

            val question9 = Question(
                question_number =3.2,
                question_text = "Question with ID 9",
                question_text2=" ",
                quiz = 1.2,
                difficulty = 2,
                style = "multiple choice",
                modules = listOf("Science", "Chemistry","Chapter3"),
                possibleAnswers = listOf("Oxygen", "Hydrogen", "Carbon Dioxide", "Nitrogen"),
                correctAnswers = listOf(3) // Nitrogen
            )

            val question10 = Question(
                question_number =3.3,
                question_text = "Question with ID 10",
                question_text2=" ",
                quiz = 1.2,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("Literature", "Books","Chapter3"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question11 = Question(
                question_number =3.4,
                question_text = "Question with ID 11",
                question_text2=" ",
                quiz = 1.3,
                difficulty = 1,
                style = "multiple choice",
                modules = listOf("for","if","Chapter1"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question12 = Question(
                question_number =3.5,
                question_text = "Question with ID 12",
                question_text2=" ",
                quiz = 1.3,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","Chapter2"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More","Chapter2"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question13 = Question(
                question_number =3.6,
                question_text = "Question with ID 13",
                question_text2=" ",
                quiz = 1.3,
                difficulty = 1,
                style = "multiple choice",
                modules = listOf("if","Chapter1"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )


            val question14 = Question(
                question_number =4.1,
                question_text = "Question with ID 14",
                question_text2=" ",
                quiz = 1.3,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if","Chapter1"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question15 = Question(
                question_number =4.2,
                question_text = "Question with ID 15",
                question_text2=" ",
                quiz = 1.3,
                difficulty = 1,
                style = "multiple choice",
                modules = listOf("for","if","Chapter3"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question16 = Question(
                question_number =4.3,
                question_text = "Question with ID 16",
                question_text2=" ",
                difficulty = 1,
                style = "multiple choice",
                modules = listOf("for","if","Chapter3"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question17 = Question(
                question_number =4.4,
                question_text = "Question with ID 17",
                question_text2=" ",
                quiz = 1.4,
                difficulty = 1,
                style = "multiple choice",
                modules = listOf("for","if","Chapter3"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question18 = Question(
                question_number =4.5,
                question_text = "Question with ID 18",
                question_text2=" ",
                quiz = 1.4,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if","Chapter3"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question19 = Question(
                question_number =5.1,
                question_text = "Question with ID 19",
                question_text2=" ",
                quiz = 1.4,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if","Chapter3"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question20 = Question(
                question_number =5.2,
                question_text = "Question with ID 20",
                question_text2=" ",
                quiz = 1.5,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if","Chapter2"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question21 = Question(
                question_number =5.3,
                question_text = "Question with ID 21",
                question_text2=" ",
                quiz = 1.5,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if","Chapter2"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question22 = Question(
                question_number =5.4,
                question_text = "Question with ID 22",
                question_text2=" ",
                quiz = 1.5,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if","Chapter2"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question23 = Question(
                question_number =5.6,
                question_text = "Question with ID 23",
                question_text2=" ",
                quiz = 1.5,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if","Chapter2"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question24 = Question(
                question_number =6.1,
                question_text = "Question with ID 24",
                question_text2=" ",
                quiz = 1.5,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if","Chapter2"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question25 = Question(
                question_number =3.4,
                question_text = "Question with ID 25",
                question_text2=" ",
                quiz = 2.1,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question26 = Question(
                question_number =3.4,
                question_text = "Question with ID 26",
                question_text2=" ",
                quiz = 2.1,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question27 = Question(
                question_number =3.4,
                question_text = "Question with ID 27",
                question_text2=" ",
                quiz = 2.1,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question28 = Question(
                question_number =3.4,
                question_text = "Question with ID 28",
                question_text2=" ",
                quiz = 2.1,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question29 = Question(
                question_number =3.4,
                question_text = "Question with ID 29",
                question_text2=" ",
                quiz = 2.2,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question30 = Question(
                question_number =3.4,
                question_text = "Question with ID 30",
                question_text2=" ",
                quiz = 2.2,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question31 = Question(
                question_number =3.4,
                question_text = "Question with ID 31",
                question_text2=" ",
                quiz = 2.2,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question32 = Question(
                question_number =3.4,
                question_text = "Question with ID 32",
                question_text2=" ",
                quiz = 2.2,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question33 = Question(
                question_number =3.4,
                question_text = "Question with ID 33",
                question_text2=" ",
                quiz = 2.3,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question34 = Question(
                question_number =3.4,
                question_text = "Question with ID 34",
                question_text2=" ",
                quiz = 2.3,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            val question35 = Question(
                question_number =3.4,
                question_text = "Question with ID 35",
                question_text2=" ",
                quiz = 2.3,
                difficulty = 3,
                style = "multiple choice",
                modules = listOf("for","if"),
                possibleAnswers = listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "Thomas More"),
                correctAnswers = listOf(1) // George Orwell
            )

            // Insert the data into the database
            questionsDao.addQuestion(question1)
            questionsDao.addQuestion(question2)
            questionsDao.addQuestion(question3)
            questionsDao.addQuestion(question4)
            questionsDao.addQuestion(question5)
            questionsDao.addQuestion(question6)
            questionsDao.addQuestion(question7)
            questionsDao.addQuestion(question8)
            questionsDao.addQuestion(question9)
            questionsDao.addQuestion(question10)
            questionsDao.addQuestion(question11)
            questionsDao.addQuestion(question12)
            questionsDao.addQuestion(question13)
            questionsDao.addQuestion(question14)
            questionsDao.addQuestion(question15)
            questionsDao.addQuestion(question16)
            questionsDao.addQuestion(question17)
            questionsDao.addQuestion(question18)
            questionsDao.addQuestion(question19)
            questionsDao.addQuestion(question20)
            questionsDao.addQuestion(question21)
            questionsDao.addQuestion(question22)
            questionsDao.addQuestion(question23)
            questionsDao.addQuestion(question24)
            questionsDao.addQuestion(question25)
            questionsDao.addQuestion(question26)
            questionsDao.addQuestion(question27)
            questionsDao.addQuestion(question28)
            questionsDao.addQuestion(question29)
            questionsDao.addQuestion(question30)
            questionsDao.addQuestion(question31)
            questionsDao.addQuestion(question32)
            questionsDao.addQuestion(question33)
            questionsDao.addQuestion(question34)
            questionsDao.addQuestion(question35)


        }
    }
}
