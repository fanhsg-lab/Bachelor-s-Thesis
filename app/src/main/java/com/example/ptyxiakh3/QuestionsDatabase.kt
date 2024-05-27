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
                question_number = 1.1,
                question_text = "Στην Python, οι εντολές πρέπει να είναι σωστά εσοχείς για να εκτελεστούν σωστά.",
                question_text2 = "",
                quiz = 1.1,
                difficulty = 1,
                style = "SouLou",
                modules = listOf("Προγραμματισμός", "Python", "Σύνταξη"),
                possibleAnswers = listOf("Είναι αυτή η δήλωση αληθής;"),
                correctAnswers = listOf(1) // Αληθής
            )

            val question2 = Question(
                question_number = 1.2,
                question_text = "Στην Python, το τέλος κάθε γραμμής πρέπει να σηματοδοτείται με ελληνικό ερωτηματικό.",
                question_text2 = "",
                quiz = 1.2,
                difficulty = 1,
                style = "SouLou",
                modules = listOf("Προγραμματισμός", "Python", "Σύνταξη"),
                possibleAnswers = listOf("Είναι αυτή η δήλωση αληθής;"),
                correctAnswers = listOf(0) // Λανθασμένη
            )


            val question3 = Question(
                question_number = 1.3,
                question_text = "Στην Python, μπορούμε να χρησιμοποιούμε και τα εισαγωγικά μονά (' ') και τα διπλά ('\"') για να δηλώσουμε μια συμβολοσειρά.",
                question_text2 = "",
                quiz = 1.3,
                difficulty = 1,
                style = "SouLou",
                modules = listOf("Προγραμματισμός", "Python", "Σύνταξη"),
                possibleAnswers = listOf("Είναι αυτή η δήλωση αληθής;"),
                correctAnswers = listOf(1) // Αληθής
            )

            val question4 = Question(
                question_number = 2.1,
                question_text = "Μπορούμε να εκτελέσουμε ένα Python script χρησιμοποιώντας την εντολή `python script.py` στον τερματικό.",
                question_text2 = "",
                quiz = 2.1,
                difficulty = 1,
                style = "SouLou",
                modules = listOf("Προγραμματισμός", "Python", "Εκτέλεση"),
                possibleAnswers = listOf("Είναι αυτή η δήλωση αληθής;"),
                correctAnswers = listOf(1) // Αληθής
            )


            val question5 = Question(
                question_number = 2.2,
                question_text = "Τα Python scripts μπορούν να εκτελεστούν μόνο από τον τερματικό και όχι από IDE όπως το PyCharm ή το VSCode.",
                question_text2 = "",
                quiz = 2.2,
                difficulty = 1,
                style = "SouLou",
                modules = listOf("Προγραμματισμός", "Python", "Εκτέλεση"),
                possibleAnswers = listOf("Είναι αυτή η δήλωση αληθής;"),
                correctAnswers = listOf(0) // Λανθασμένη
            )

            val question6 = Question(
                question_number = 2.3,
                question_text = "Το αρχείο `script.txt` μπορεί να εκτελεστεί ως Python script.",
                question_text2 = "",
                quiz = 2.3,
                difficulty = 1,
                style = "SouLou",
                modules = listOf("Προγραμματισμός", "Python", "Εκτέλεση"),
                possibleAnswers = listOf("Είναι αυτή η δήλωση αληθής;"),
                correctAnswers = listOf(0) // Λανθασμένη
            )


            val question7 = Question(
                question_number = 1.4,
                question_text = "[____]('[____], [____]!')",
                question_text2 = "Γράψτε την εντολή για να εκτυπώσετε το 'Hello, World!' στην Python.",
                quiz = 1.4,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "Syntax"),
                possibleAnswers = listOf("print", "echo", "Hello", "World"),
                correctAnswers = listOf(134) // print('Hello, World!')
            )

            val question8 = Question(
                question_number = 1.5,
                question_text = "b [____] 20 [____]  4",
                question_text2 = "Ορίστε μια μεταβλητή 'b' και εκχωρήστε την τιμή 20 σε αυτή. Στη συνέχεια, μειώστε την τιμή της κατά 4 χρησιμοποιώντας μια αριθμητική πράξη.",
                quiz = 1.5,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "Variables"),
                possibleAnswers = listOf("=", "+", "-", "*", "/"),
                correctAnswers = listOf(13) // b = 20 - 4
            )

            val question9 = Question(
                question_number = 2.1,
                question_text = "[____]  script.py",
                question_text2 = "Μπορούμε να εκτελέσουμε ένα Python script χρησιμοποιώντας την εντολή `python script.py` στον τερματικό.",
                quiz = 2.1,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "Execution"),
                possibleAnswers = listOf("run", "python", "execute", "start"),
                correctAnswers = listOf(2) // python script.py
            )

            val question10 = Question(
                question_number = 2.2,
                question_text = "c [____] 15 [____] 3",
                question_text2 = "Ορίστε μια μεταβλητή 'c' και εκχωρήστε την τιμή 15 σε αυτή. Στη συνέχεια, πολλαπλασιάστε την τιμή της κατά 3 χρησιμοποιώντας μια αριθμητική πράξη.",
                quiz = 2.2,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "Variables"),
                possibleAnswers = listOf("=", "+", "-", "*", "/"),
                correctAnswers = listOf(14) // c = 15 * 3
            )

            val question11 = Question(
                question_number = 2.3,
                question_text = "[____]('[____]', '[____]')",
                question_text2 = "Γράψτε μια εντολή για να διαβάσετε ένα αρχείο με όνομα 'data.txt' στην Python.",
                quiz = 2.3,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "File Handling"),
                possibleAnswers = listOf("open", "read", "file", "data.txt", "r"),
                correctAnswers = listOf(145) // open('data.txt', 'r')
            )


            val question61 = Question(
                question_number = 1.6,
                question_text = "Μπορείτε να χρησιμοποιήσετε τη μέθοδο append() για να προσθέσετε ένα στοιχείο στο τέλος μιας λίστας στην Python.",
                question_text2 = "",
                quiz = 1.2,
                difficulty = 1,
                style = "SouLou",
                modules = listOf("Προγραμματισμός", "Python", "Δομές Δεδομένων"),
                possibleAnswers = listOf("Είναι αυτή η δήλωση αληθής;"),
                correctAnswers = listOf(1) // Αληθής
            )

            val question73 = Question(
                question_number = 1.7,
                question_text = "Ο τελεστής == στην Python χρησιμοποιείται για την ανάθεση τιμών σε μεταβλητές.",
                question_text2 = "",
                quiz = 1.2,
                difficulty = 1,
                style = "SouLou",
                modules = listOf("Προγραμματισμός", "Python", "Τελεστές"),
                possibleAnswers = listOf("Είναι αυτή η δήλωση αληθής;"),
                correctAnswers = listOf(0) // Ψευδής
            )

            val question83 = Question(
                question_number = 1.8,
                question_text = "Ένα tuple είναι μια μεταβλητή δομή δεδομένων στην Python.",
                question_text2 = "",
                quiz = 1.2,
                difficulty = 1,
                style = "SouLou",
                modules = listOf("Προγραμματισμός", "Python", "Τύποι Δεδομένων"),
                possibleAnswers = listOf("Είναι αυτή η δήλωση αληθής;"),
                correctAnswers = listOf(0) // Ψευδής
            )

            val question93 = Question(
                question_number = 1.9,
                question_text = "Η λέξη-κλειδί def χρησιμοποιείται στην Python για τον ορισμό μιας νέας συνάρτησης.",
                question_text2 = "",
                quiz = 1.2,
                difficulty = 1,
                style = "SouLou",
                modules = listOf("Προγραμματισμός", "Python", "Συναρτήσεις"),
                possibleAnswers = listOf("Είναι αυτή η δήλωση αληθής;"),
                correctAnswers = listOf(1) // Αληθής
            )

            val question103 = Question(
                question_number = 1.10,
                question_text = "Η Python σας επιτρέπει να χρησιμοποιείτε το σύμβολο # για σχόλια μιας γραμμής.",
                question_text2 = "",
                quiz = 1.2,
                difficulty = 1,
                style = "SouLou",
                modules = listOf("Προγραμματισμός", "Python", "Σύνταξη"),
                possibleAnswers = listOf("Είναι αυτή η δήλωση αληθής;"),
                correctAnswers = listOf(1) // Αληθής
            )




            val question12 = Question(
                question_number = 1.11,
                question_text = "Διάβασε είδος\n" +
                        "Επίλεξε [____]\n" +
                        "  Περίπτωση 1\n" +
                        "    κόστος = 0.085\n" +
                        "  Περίπτωση 2\n" +
                        "    κόστος = 0.67\n" +
                        "  Περίπτωση 3\n" +
                        "    κόστος = 0.93\n" +
                        "  Περίπτωση [____]\n" +
                        "    κόστος = 1.25\n" +
                        "Τέλος_επιλογών\n" +
                        "Αν [____] < κόστος τότε\n" +
                        "  Εκτύπωσε \"Οι μονάδες δεν επαρκούν, το διαθέσιμο υπόλοιπο είναι \", διαθέσιμο_υπόλοιπο\n" +
                        "Αλλιώς\n" +
                        "  διαθέσιμο_υπόλοιπο = διαθέσιμο_υπόλοιπο - [____]\n" +
                        "  Εκτύπωσε \"Το μήνυμα εστάλη...\"\n" +
                        "Τέλος_αν\n" +
                        "Αποτελέσματα // διαθέσιμο_υπόλοιπο //\n"
                ,
                question_text2 = "Συμπληρώστε τα κενά για τον υπολογισμό κόστους αποστολής μηνύματος",
                quiz = 1.3,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "Variables"),
                possibleAnswers = listOf("είδος", "Αλλιώς" ,"κόστος" ,"διαθέσιμο_υπόλοιπο", "κόστος"),
                correctAnswers = listOf(1234,1254) // a = 10 + 5
            )

            val question122 = Question(
                question_number = 1.12,
                question_text = "Create a list named 'fruits' containing 'apple', 'banana', and 'cherry'. Then, access the second item in the list.\n" +
                        "fruits = [____]('apple', 'banana', 'cherry')\n" +
                        "print(fruits[____])",
                question_text2 = "What is the second fruit in the list?",
                quiz = 1.3,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "Lists"),
                possibleAnswers = listOf("list", "1"),
                correctAnswers = listOf(12) // fruits = list('apple', 'banana', 'cherry'); print(fruits[1])
            )

            val question13 = Question(
                question_number = 1.13,
                question_text = "Write a Python statement that checks if the value '42' is greater than '24'.\n" +
                        "42 [____] 24",
                question_text2 = "Is 42 greater than 24?",
                quiz = 1.3,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "Conditional"),
                possibleAnswers = listOf(">"),
                correctAnswers = listOf(1) // 42 > 24
            )

            val question14 = Question(
                question_number = 1.14,
                question_text = "Define a function 'sum' that takes two parameters 'x' and 'y' and returns their sum.\n" +
                        "def [____](x, y):\n" +
                        "\treturn x [____] y",
                question_text2 = "Write the correct syntax to define this function.",
                quiz = 1.3,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "Functions"),
                possibleAnswers = listOf("sum", "+"),
                correctAnswers = listOf(12) // def sum(x, y): return x + y
            )

            val question15 = Question(
                question_number = 1.15,
                question_text = "Write a Python loop that prints numbers from 1 to 5.\n" +
                        "for i in range([____], [____]):\n" +
                        "\tprint([____])",
                question_text2 = "Fill in the blanks to correctly write the loop.",
                quiz = 1.3,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "Loops"),
                possibleAnswers = listOf("1", "6", "i"),
                correctAnswers = listOf(123) // for i in range(1, 6): print(i)
            )

            val question16 = Question(
                question_number = 1.16,
                question_text = "How do you add an item 'orange' to the end of the list 'fruits'?\n" +
                        "fruits.[____]('orange')",
                question_text2 = "What method should you use to add an item to the list?",
                quiz = 1.4,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "Lists"),
                possibleAnswers = listOf("append"),
                correctAnswers = listOf(1) // fruits.append('orange')
            )

            val question17 = Question(
                question_number = 1.17,
                question_text = "How do you remove an item 'banana' from the list 'fruits'?\n" +
                        "fruits.[____]('banana')",
                question_text2 = "What method should you use to remove an item from the list?",
                quiz = 1.4,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "Lists"),
                possibleAnswers = listOf("remove"),
                correctAnswers = listOf(1) // fruits.remove('banana')
            )

            val question18 = Question(
                question_number = 1.18,
                question_text = "Write a condition in Python that evaluates to True if 'a' is less than 'b' and 'c' is greater than 'd'.\n" +
                        "a [____] b and c [____] d",
                question_text2 = "Fill in the operators and variable names to complete the condition.",
                quiz = 1.4,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "Conditions"),
                possibleAnswers = listOf("<", ">"),
                correctAnswers = listOf(12) // a < b and c > d
            )

            val question19 = Question(
                question_number = 1.19,
                question_text = "Assign the result of the division of 10 by 2 to a variable 'result'.\n" +
                        "result [____] 10 [____] 2",
                question_text2 = "What symbols complete the assignment correctly?",
                quiz = 1.4,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "Operations"),
                possibleAnswers = listOf("=", "/"),
                correctAnswers = listOf(12) // result = 10 / 2
            )

            val question20 = Question(
                question_number = 1.20,
                question_text = "Check if the variable 'num' is equal to 100 using an if statement in Python.\n" +
                        "if num [____] 100:\n" +
                        "\tprint('num is 100')",
                question_text2 = "What operator should be used to compare 'num' with 100?",
                quiz = 1.4,
                difficulty = 1,
                style = "Kena",
                modules = listOf("Programming", "Python", "Conditional"),
                possibleAnswers = listOf("=="),
                correctAnswers = listOf(1) // if num == 100: print('num is 100')
            )


            val question21 = Question(
                question_number = 1.21,
                question_text =
                "num = 10\n " +
                        "αν num = 10:\n " +
                        "εκτύπωσε('Ο αριθμός είναι 10') \n" +
                        "αλλιώς: \n" +
                        "εκτύπωσε('Ο αριθμός δεν είναι 10')",
                question_text2="Click on the incorrect symbols or keywords in the code.",
                question_module=listOf("answer1", "answer2"),
                quiz = 1.5,
                difficulty = 3,
                style = "Mistakes",
                modules = listOf("Programming", "Python", "Syntax"),
                possibleAnswers = listOf("="), // '=' should be '=='
                correctAnswers = listOf(1) // Incorrect '='
            )

            val question22 = Question(
                question_number = 1.22,
                question_text =
                "for i in range(10) \n" +
                        "\tprint(i)\n",
                question_text2="Identify incorrect symbols or missing elements that should be correctable in the code.",
                question_module=listOf("answer1", "answer2"),
                quiz = 1.5,
                difficulty = 3,
                style = "Mistakes",
                modules = listOf("Programming", "Python", "Loops"),
                possibleAnswers = listOf("range(10)"), // The text 'range(10)' is correctly written but missing ':', suggesting where to look.
                correctAnswers = listOf(1) // Indicates the need for ':' after 'range(10)'
            )

            val question23 = Question(
                question_number = 1.23,
                question_text =
                "list = [1, 2, 3]\n" +
                        " print ( list [3] ) ",
                question_text2="Identify the incorrect index or keyword used.",
                question_module=listOf("answer1", "answer2"),
                quiz = 1.5,
                difficulty = 3,
                style = "Mistakes",
                modules = listOf("Programming", "Python", "Data Structures"),
                possibleAnswers = listOf("3"), // '3' is an out of range index for the list
                correctAnswers = listOf(1) // Incorrect index '3'
            )

            val question24 = Question(
                question_number = 1.24,
                question_text =
                "def calculate_sum(x, y):\n" +
                        "\treturn x + y\n" +
                        "result = calculate_sum( 5 )",
                question_text2="Review the function call for any errors.",
                question_module=listOf("answer1", "answer2"),
                quiz = 1.5,
                difficulty = 3,
                style = "Mistakes",
                modules = listOf("Programming", "Python", "Functions"),
                possibleAnswers = listOf("5"), // Function call with a single argument is incorrect
                correctAnswers = listOf(1) // Function called with '5' only, needs two arguments
            )

            val question25 = Question(
                question_number = 1.25,
                question_text =
                "import math\n" +
                        "print(math.power(2, 3))",
                question_text2="Examine the function name used from the math module.",
                question_module=listOf("pow"),
                quiz = 1.5,
                difficulty = 3,
                style = "Mistakes",
                modules = listOf("Programming", "Python", "Modules"),
                possibleAnswers = listOf("power"), // Incorrect function name 'power' should be 'pow'
                correctAnswers = listOf(1) // Incorrect function name 'power'
            )

            val question26 = Question(
                question_number = 1.26,
                question_text = "Select two built-in data types in Python.",
                question_text2=" ",
                quiz = 2.1,
                difficulty = 2,
                style = "multiple choice",
                modules = listOf("Programming", "Python", "Data Types"),
                possibleAnswers = listOf("string", "real", "tuple", "dictionary"),
                correctAnswers = listOf(0, 2) // "string", "tuple"
            )

            val question27 = Question(
                question_number = 1.27,
                question_text = "Which two keywords are used for defining a function in Python?",
                question_text2=" ",
                quiz = 2.1,
                difficulty = 2,
                style = "multiple choice",
                modules = listOf("Programming", "Python", "Functions"),
                possibleAnswers = listOf("func", "def", "lambda", "function"),
                correctAnswers = listOf(1, 2) // "def", "lambda"
            )

            val question28 = Question(
                question_number = 1.28,
                question_text = "Choose two types of loops available in Python.",
                question_text2=" ",
                quiz = 2.1,
                difficulty = 2,
                style = "multiple choice",
                modules = listOf("Programming", "Python", "Control Structures"),
                possibleAnswers = listOf("for", "while", "do-while", "repeat"),
                correctAnswers = listOf(0, 1) // "for", "while"
            )

            val question29 = Question(
                question_number = 1.29,
                question_text = "Identify two methods that can be used to handle exceptions in Python.",
                question_text2=" ",
                quiz = 2.1,
                difficulty = 2,
                style = "multiple choice",
                modules = listOf("Programming", "Python", "Error Handling"),
                possibleAnswers = listOf("try", "catch", "except", "finally"),
                correctAnswers = listOf(0, 2) // "try", "except"
            )

            val question30 = Question(
                question_number = 1.30,
                question_text = "Select two Python libraries commonly used for data analysis.",
                question_text2=" ",
                quiz = 2.1,
                difficulty = 2,
                style = "multiple choice",
                modules = listOf("Programming", "Python", "Libraries"),
                possibleAnswers = listOf("NumPy", "Pandas", "TensorFlow", "Swift"),
                correctAnswers = listOf(0, 1) // "NumPy", "Pandas"
            )

            val question355 = Question(
                question_number = 1.35,
                question_text =
                "2. define a class inheriting from unittest.TestCase\n" +
                "1. import unittest\n" +
                        "5. if __name__ == '__main__':\n" +
                        "3. define a test method inside the class\n" +
                        "4. use assert methods to test expected outcomes\n" +

                        "6. unittest.main()",
                question_text2="Arrange the following steps into their correct sequence to set up a basic unit test in Python using the unittest framework.",
                quiz = 2.2,
                difficulty = 2,
                style = "Queue",
                modules = listOf("Programming", "Python", "Testing"),
                possibleAnswers = listOf(
                    "define a class inheriting from unittest.TestCase", // 2
                    "import unittest",                                   // 1
                    "if __name__ == '__main__':",                        // 5
                    "define a test method inside the class",             // 3
                    "use assert methods to test expected outcomes",      // 4
                    "unittest.main()"                                    // 6
                ),
                correctAnswers = listOf(215346) // Correct sequence for setting up unit tests
            )


            val question35 = Question(
                question_number = 1.11,
                question_text = "Διάβασε είδος\n" +
                        "Επίλεξε [____]\n" +
                        "  Περίπτωση 1\n" +
                        "    κόστος = 0.085\n" +
                        "  Περίπτωση 2\n" +
                        "    κόστος = 0.67\n" +
                        "  Περίπτωση 3\n" +
                        "    κόστος = 0.93\n" +
                        "  Περίπτωση [____]\n" +
                        "    κόστος = 1.25\n" +
                        "Τέλος_επιλογών\n" +
                        "Αν [____] < κόστος τότε\n" +
                        "  Εκτύπωσε \"Οι μονάδες δεν επαρκούν, το διαθέσιμο υπόλοιπο είναι \", διαθέσιμο_υπόλοιπο\n" +
                        "Αλλιώς\n" +
                        "  διαθέσιμο_υπόλοιπο = διαθέσιμο_υπόλοιπο - [____]\n" +
                        "  Εκτύπωσε \"Το μήνυμα εστάλη...\"\n" +
                        "Τέλος_αν\n" +
                        "Αποτελέσματα // διαθέσιμο_υπόλοιπο //\n"
                ,
                question_text2 = "Συμπληρώστε τα κενά για τον υπολογισμό κόστους αποστολής μηνύματος",
                quiz = 1.3,
                difficulty = 1,
                style = "Queue",
                modules = listOf("Programming", "Python", "Variables"),
                possibleAnswers = listOf(
                    "Διάβασε είδος",
                    "Επίλεξε [____]",
                    "  Περίπτωση 1",
                    "    κόστος = 0.085",
                    "  Περίπτωση 2",
                    "    κόστος = 0.67",
                    "  Περίπτωση 3",
                    "    κόστος = 0.93",
                    "  Περίπτωση [____]",
                    "    κόστος = 1.25",
                    "Τέλος_επιλογών",
                    "Αν [____] < κόστος τότε",
                    "  Εκτύπωσε \"Οι μονάδες δεν επαρκούν, το διαθέσιμο υπόλοιπο είναι \", διαθέσιμο_υπόλοιπο",
                    "Αλλιώς",
                    "  διαθέσιμο_υπόλοιπο = διαθέσιμο_υπόλοιπο - [____]",
                    "  Εκτύπωσε \"Το μήνυμα εστάλη...\"",
                    "Τέλος_αν",
                    "Αποτελέσματα // διαθέσιμο_υπόλοιπο //"
                ),correctAnswers = listOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18) // a = 10 + 5
            )
            //Correct Question About Queue
            val question36 = Question(
                question_number = 1.36,
                question_text =
                "1. def factorial(n):\n" +
                "2. if n == 1:\n" +

                        "3. return 1\n" +
                        "5. else:\n"+
                        "4. return n * factorial(n - 1)\n" ,

                question_text2="Arrange the following lines of Python code into the correct sequence to define a recursive factorial function that calculates the factorial of a number.",
                quiz = 2.2,
                difficulty = 2,
                style = "Queue",
                modules = listOf("Programming", "Python", "Functions"),
                possibleAnswers = listOf(
                    "if n == 1:",
                "def factorial(n):",
            "return 1",
            "return n * factorial(n - 1)",
            "else:"
            ),
            correctAnswers = listOf(21354) // Correct sequence for defining a recursive factorial function
            )

            val question37 = Question(
                question_number = 1.37,
                question_text =
                "4. for i in range(20):\n" +

                "1. if i % 2 == 0:\n" +
                        "2. print('Even number:', i)\n" +
                        "5. else:\n"+
                        "3. print('Odd number:', i)\n" ,


                question_text2="Arrange the following lines of Python code into the correct sequence to print whether each number from 0 to 19 is even or odd.",
                quiz = 2.2,
                difficulty = 2,
                style = "Queue",
                modules = listOf("Programming", "Python", "Control Structures"),
                possibleAnswers = listOf(
                    "if i % 2 == 0:",
                "print('Even number:', i)",
            "print('Odd number:', i)",
            "for i in range(20):",
            "else:"
            ),
            correctAnswers = listOf(41253) // Correct sequence for a for-loop with
            )

            val     question38 = Question(
                question_number = 1.38,
                question_text =
                "3. def count_down(start):\n" +
                "1. while start > 0:\n" +
                        "2. print('Counting down:', start)\n" +
                        "6. start -= 1\n" +
                        "4. else:\n" +
                        "5. print('Lift off!')\n" ,

                question_text2="Arrange the following lines of Python code into the correct sequence to define a function that counts down from a given start number to zero, then prints 'Lift off!'.",
                quiz = 2.2,
                difficulty = 3,
                style = "Queue",
                modules = listOf("Programming", "Python", "Functions", "Loops"),
                possibleAnswers = listOf(
                    "while start > 0:",
                "print('Counting down:', start)",
            "def count_down(start):",
            "else:",
            "print('Lift off!')",
            "start -= 1"
            ),
            correctAnswers = listOf(312645) // Correct sequence for a countdown function
            )

            val question39 = Question(
                question_number = 1.39,
                question_text =
                "4. def filter_and_square(nums):\n" +
                        "2. nums = [1, 2, 3, 4, 5, 6]\n" +
                "1. return [x**2 for x in nums if x > 3]\n" +

                        "3. print(filter_and_square(nums))\n" ,

                question_text2="Arrange the following lines of Python code into the correct sequence to define a function that filters numbers greater than 3 from a list and returns their squares.",
                quiz = 2.2,
                difficulty = 2,
                style = "Queue",
                modules = listOf("Programming", "Python", "List Comprehension", "Functions"),
                possibleAnswers = listOf(
                    "return [x**2 for x in nums if x > 3]",
                "nums = [1, 2, 3, 4, 5, 6]",
            "print(filter_and_square(nums))",
            "def filter_and_square(nums):"
            ),
            correctAnswers = listOf(4213) // Correct sequence for a function with list comprehension
            )




            /*

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
                question_module=listOf("answer1", "answer2", "answer3"),
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
                correctAnswers = listOf(2,3) // "Carbon Dioxide", "Nitrogen"
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
                        "αν α = 0 τότε \n" +
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


             */

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

            questionsDao.addQuestion(question35)
            questionsDao.addQuestion(question36)
            questionsDao.addQuestion(question37)
            questionsDao.addQuestion(question38)
            questionsDao.addQuestion(question39)
            /*
            questionsDao.addQuestion(question31)
            questionsDao.addQuestion(question32)
            questionsDao.addQuestion(question33)
            questionsDao.addQuestion(question34)
            questionsDao.addQuestion(question35)
*/

        }
    }
}
