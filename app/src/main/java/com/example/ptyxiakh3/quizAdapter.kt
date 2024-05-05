package com.example.ptyxiakh3

import QueueAdapter
import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ptyxiakh3.DbQuery.myProfile
import com.example.ptyxiakh3.data.Question
import com.google.android.flexbox.FlexboxLayout
import org.w3c.dom.Text

class quizAdapter(
    private val context: Context,
    private val quizModels: ArrayList<Question>,
    private val navController: NavController,
    var layoutChangeListener: (() -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val buttonClickOrder = mutableListOf<Int>()

    companion object {
        const val TYPE_ONE = 0
        const val TYPE_TWO = 1
        const val TYPE_THREE = 2
        const val TYPE_FOUR = 3
        const val TYPE_FIVE = 4

    }

    override fun getItemViewType(position: Int): Int {
        // Example condition based on the 'style' attribute of the Question
        return when (quizModels[position].style) {
            "multiple choice" -> TYPE_ONE
            "SouLou" -> TYPE_TWO
            "Kena"-> TYPE_THREE
            "Queue"-> TYPE_FOUR
            "Mistakes"-> TYPE_FIVE
            else -> TYPE_ONE // Default case or you might want to handle it differently
        }
    }
    fun getQuestionAtPosition(position: Int): Question? {
        return if (position >= 0 && position < itemCount) {
            quizModels[position] // Use quizModels here
        } else null
    }

    fun changeLayout() {
        layoutChangeListener?.invoke()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_ONE -> ViewHolderTypeOne(inflater.inflate(R.layout.quiz_item_layout_multiple, parent, false))
            TYPE_TWO -> ViewHolderTypeTwo(inflater.inflate(R.layout.quiz_item_layout_soulou, parent, false))
            TYPE_THREE -> ViewHolderTypeThree(inflater.inflate(R.layout.quiz_item_layout_kena, parent, false))
            TYPE_FOUR -> ViewHolderTypeFour(inflater.inflate(R.layout.quiz_item_layout_queue, parent, false), context)
            TYPE_FIVE -> ViewHolderTypeFive(inflater.inflate(R.layout.quiz_item_layout_mistakes, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val question = quizModels[position]
        when (holder) {
            is ViewHolderTypeOne -> holder.bind(question)
            is ViewHolderTypeTwo -> holder.bind(question)
            is ViewHolderTypeThree -> holder.bind(question)
            is ViewHolderTypeFour -> holder.bind(question)
            is ViewHolderTypeFive -> holder.bind(question)
        }
        Log.d("quizAdapter2", "mphka")
    }

    override fun getItemCount(): Int = quizModels.size

    // ViewHolder for type one
    class ViewHolderTypeOne(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val questionTextView1: CheckBox = itemView.findViewById(R.id.text1multiplechoice)
        private val questionTextView2: CheckBox = itemView.findViewById(R.id.text2multiplechoice)
        private val questionTextView3: CheckBox = itemView.findViewById(R.id.text3multiplechoice)
        private val questionTextView4: CheckBox = itemView.findViewById(R.id.text4multiplechoice)
        private val questionTextView5: CheckBox = itemView.findViewById(R.id.text5multiplechoice)

        private val questionText: TextView = itemView.findViewById(R.id.quetextmultiplechoice)

        val questionTextViews = listOf<TextView>(questionTextView1, questionTextView2, questionTextView3,questionTextView4,questionTextView5)



        fun bind(question: Question) {
            Log.d("quizAdapter2", "mphka1")
            // Bind data for type one
            questionText.text =question.question_text
            question.possibleAnswers.forEachIndexed { index, answer ->
                if (index < questionTextViews.size) {
                    questionTextViews[index].text = answer
                    questionTextViews[index].visibility = View.VISIBLE
                }
            }
        }



    }



    // ViewHolder for type two
    // ViewHolder for type two
    // ViewHolder for type two
    class ViewHolderTypeTwo(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val questionTextView: TextView = itemView.findViewById(R.id.souloutext)
        private val Titlos: TextView = itemView.findViewById(R.id.titlossoulou)
        private val falsebut: Button = itemView.findViewById(R.id.falsebut_image)
        private val truebut: Button = itemView.findViewById(R.id.truebut_image)

        private val nextButtonDrawableId = R.drawable.next_button_drawable
        private val tfButtonsClickedDrawableId = R.drawable.tf_buttons_clicked
        private val startingBackground = R.drawable.next_button_drawable

        var isTrueButtonClicked = false

        init {


            falsebut.setOnClickListener {
                // Check if the true button is already clicked

                    truebut.setTextColor(Color.parseColor("white"))
                    truebut.setBackgroundResource(nextButtonDrawableId)


                falsebut.setTextColor(Color.parseColor("#353a4f"))
                falsebut.setBackgroundResource(tfButtonsClickedDrawableId)

                // Update the state
                isTrueButtonClicked = false
            }

            truebut.setOnClickListener {
                // Check if the false button is already clicked

                    falsebut.setTextColor(Color.parseColor("white"))
                    falsebut.setBackgroundResource(nextButtonDrawableId)


                truebut.setTextColor(Color.parseColor("#353a4f"))
                truebut.setBackgroundResource(tfButtonsClickedDrawableId)

                // Update the state
                isTrueButtonClicked = true
            }
        }

        fun getCurrentState(): Boolean {
            return isTrueButtonClicked
        }

        fun bind(question: Question) {
            Log.d("quizAdapter2", "mphka2")
            // Bind data for type one
            questionTextView.text = question.question_text
            Titlos.text=question.possibleAnswers[0]
            truebut.setTextColor(Color.parseColor("#353a4f"))
            truebut.setBackgroundResource(startingBackground)
            falsebut.setTextColor(Color.parseColor("#353a4f"))
            falsebut.setBackgroundResource(startingBackground)
        }
    }









    inner class ViewHolderTypeThree(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val Paragraph: TextView = itemView.findViewById(R.id.paragraphTextView)
        private val titlos: TextView = itemView.findViewById(R.id.titloskena)
        private var initialFlexboxHeight: Int = 0


        fun bind(question: Question) {


            val flexboxLayout = itemView.findViewById<FlexboxLayout>(R.id.flexboxLayout)
            if (initialFlexboxHeight == 0) {
                // Make sure the layout is laid out before measuring
                flexboxLayout.post {
                    initialFlexboxHeight = flexboxLayout.height
                    flexboxLayout.layoutParams.height = initialFlexboxHeight
                }
            }

            titlos.text=question.question_text2
            Paragraph.text=question.question_text
            lateinit var paragraphTextView: TextView
            val placeholders = listOf("[____]", "[____]", "[____]", "[____]", "[____]", "[____]", "[____]", "[____]", "[____]", "[____]", "[____]", "[____]")
            var currentPlaceholderIndex = 0
            fun replaceNextPlaceholder(word: String) {
                val currentText = paragraphTextView.text.toString()
                val updatedText = currentText.replaceFirst(placeholders[currentPlaceholderIndex], word)
                paragraphTextView.text = updatedText
                currentPlaceholderIndex++ // Increment the index here
            }
            var characterCount = question.correctAnswers[0].toString().length
            Log.d("apapa","Number of characters: ${question.correctAnswers[0]} , $characterCount")

            if(characterCount>9){
                characterCount = (characterCount-9)/2 +9
            }

            data class ButtonClickState(val textBeforeClick: String, val clickedButtonId: Int)
            val buttonClickHistory = mutableListOf<ButtonClickState>()

            paragraphTextView =  itemView.findViewById(R.id.paragraphTextView)

            val buttons = listOf(
                itemView.findViewById<Button>(R.id.button1),
                itemView.findViewById<Button>(R.id.button2),
                itemView.findViewById<Button>(R.id.button3),
                itemView.findViewById<Button>(R.id.button4),
                itemView.findViewById<Button>(R.id.button5),
                itemView.findViewById<Button>(R.id.button6),
                itemView.findViewById<Button>(R.id.button7),
                itemView.findViewById<Button>(R.id.button8),
                itemView.findViewById<Button>(R.id.button9),
                itemView.findViewById<Button>(R.id.button10)
            )
            var index = 0
            buttons.forEach { button ->

                if(question.possibleAnswers.size>index){
                    button.visibility = View.VISIBLE
                button.text = question.possibleAnswers[index]

                val currentIndex = index // Capture the current index value

                button.setOnClickListener {
                    Log.d("apapa","${currentPlaceholderIndex} , ${placeholders.size}" )
                    if (currentPlaceholderIndex < characterCount) {
                        buttonClickHistory.add(
                            ButtonClickState(
                                paragraphTextView.text.toString(),
                                button.id
                            )
                        )
                        buttonClickOrder.add(currentIndex + 1) // Use captured index value here (+1 if you want to start from 1 instead of 0)
                        replaceNextPlaceholder(button.text.toString())
                        button.visibility = View.GONE

                        // Log the button order here, after each click
                        if (currentPlaceholderIndex >= placeholders.size) {
                            Log.d(
                                "MainActivity",
                                "Button order: ${buttonClickOrder.joinToString(", ")}"
                            )
                        }
                    }
                }
            }
                index++
            }


            val resetButton =  itemView.findViewById<Button>(R.id.resetButton)
            resetButton.setOnClickListener {
                paragraphTextView.text = question.question_text
                currentPlaceholderIndex = 0
                buttonClickOrder.clear()
                buttonClickHistory.clear()

                val size = question.possibleAnswers.size

                val buttonIds = listOf(
                    R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                    R.id.button5, R.id.button6, R.id.button7, R.id.button8,
                    R.id.button9, R.id.button10
                )

                buttonIds.forEachIndexed { index, buttonId ->
                    val visibility = if (index < size) View.VISIBLE else View.GONE
                    itemView.findViewById<Button>(buttonId).visibility = visibility
                }

            }

            val undoButton =  itemView.findViewById<Button>(R.id.undoButton)
            undoButton.setOnClickListener {
                if (buttonClickHistory.isNotEmpty()) {
                    val lastState = buttonClickHistory.removeAt(buttonClickHistory.size - 1)
                    paragraphTextView.text = lastState.textBeforeClick
                    itemView.findViewById<Button>(lastState.clickedButtonId).visibility = View.VISIBLE

                    if (currentPlaceholderIndex > 0) {
                        currentPlaceholderIndex--
                    }

                    if (buttonClickOrder.isNotEmpty()) {
                        buttonClickOrder.removeAt(buttonClickOrder.size - 1)
                    }
                }
            }

        }


    }

    // ViewHolder for type four
    // ViewHolder for type four
    inner class ViewHolderTypeFour(
        itemView: View,
        private val context: Context
    ) : RecyclerView.ViewHolder(itemView) {

        private val recyclerView: RecyclerView = itemView.findViewById(R.id.queueRecycler)
        private val queueModels: ArrayList<QueueItem> = ArrayList()
        private val titletext: TextView = itemView.findViewById(R.id.Queuetext)


        // Define ItemTouchHelper.Callback here
        private val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                // Swap the items in the list
                val movedItem = queueModels[fromPosition]
                queueModels.removeAt(fromPosition)
                queueModels.add(toPosition, movedItem)

                // Notify the adapter about the change
                recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Not used in this example
            }

            override fun getMoveThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return 0.1f // Adjust this value as needed
            }
        }

        init {
            // Attach ItemTouchHelper to enable drag-and-drop
            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }

        fun bind(question: Question) {
            // ... (existing code)
            var index = 1
            titletext.text=question.question_text2
            Log.d("QuizAdapter22", "Possible Answers Before Iteration: ${question.possibleAnswers.joinToString()}")

            Log.d("QuizAdapter22", "Possible Answers: ${question.possibleAnswers.joinToString()}")

            question.possibleAnswers.forEach { possibleAnswer ->
                // Do something with each possible question, for example:
                queueModels.add(QueueItem(possibleAnswer,index))
                Log.d("QuizAdapter22", "Item : $possibleAnswer")
                index++
            }

            val adapter = QueueAdapter(context, queueModels)

            // Set up RecyclerView with a LinearLayoutManager for the new type
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }

        fun getCurrentOrder(): List<Int> {
            return queueModels.map { it.Number }
        }


    }

    class ViewHolderTypeFive(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.mistakestext)
        private val textViewtitlos: TextView = itemView.findViewById(R.id.titlosmistakes)

        private lateinit var originalText: String
        private lateinit var spannableString: SpannableString
        private val coloredWords = mutableSetOf<IntRange>()



        init {



            textView.movementMethod = LinkMovementMethod.getInstance()
            textView.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    val layout = (v as TextView).layout
                    val x = event.x.toInt() - textView.totalPaddingLeft + textView.scrollX
                    val y = event.y.toInt() - textView.totalPaddingTop + textView.scrollY
                    val line = layout.getLineForVertical(y)

                    if (x <= layout.getLineWidth(line)) {
                        val offset = layout.getOffsetForHorizontal(line, x.toFloat())
                        if (offset < originalText.length) {
                            // Updated regex to treat digits, punctuation, and words separately
                            val segments = Regex("(\\d+|\\w+|\\S)").findAll(originalText).toList()
                                .find { matchResult -> offset in matchResult.range }

                            segments?.let {
                                val segmentStart = it.range.first
                                val segmentEnd = it.range.last + 1
                                val segmentStartX = layout.getPrimaryHorizontal(segmentStart)
                                val segmentEndX = layout.getPrimaryHorizontal(segmentEnd)

                                if (x >= segmentStartX && x <= segmentEndX) {
                                    val spannableString = SpannableString(textView.text)
                                    val spans = spannableString.getSpans(segmentStart, segmentEnd, ForegroundColorSpan::class.java)
                                    if (spans.isNotEmpty()) {
                                        spannableString.removeSpan(spans[0])
                                    } else {
                                        // Assign colors based on the type of segment
                                        val customColor = if (it.value.matches(Regex("\\d+"))) {
                                            Color.parseColor("#FFA07A") // Light salmon for numbers
                                        } else if (it.value.matches(Regex("\\w+"))) {
                                            Color.parseColor("#7D729F") // Purple for words
                                        } else {
                                            Color.parseColor("#FF6347") // Tomato for punctuation and other characters
                                        }
                                        spannableString.setSpan(ForegroundColorSpan(customColor), segmentStart, segmentEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                                    }
                                    textView.text = spannableString
                                }
                            }
                        }
                    }
                    true // Consume the touch event
                } else {
                    false // Return false for other actions (e.g., touch down)
                }
            }

        }

        fun bind(question: Question) {
            originalText = question.question_text
            spannableString = SpannableString(originalText)
            textView.text = spannableString
            textViewtitlos.text = question.question_text2
        }

        fun showColoredText(): List<String> {
            val coloredTextList = coloredWords.map { range ->
                originalText.substring(range.first, range.last)
            }

            val coloredTextString = coloredTextList.joinToString(", ")
            Toast.makeText(itemView.context, "Colored words: $coloredTextString", Toast.LENGTH_LONG).show()

            return coloredTextList
        }
    }






    fun interactWithCurrentItem(viewHolder: RecyclerView.ViewHolder, flagforH: Int): Pair<Boolean, String> {


        val position = viewHolder.adapterPosition // Get the position of the item in the adapter
        if (position != RecyclerView.NO_POSITION) {
            val currentQuestion =
                quizModels[position] // Access the current question using the position

            when (viewHolder) {
                is ViewHolderTypeOne -> {
                    val questionTextView1: CheckBox =
                        viewHolder.itemView.findViewById(R.id.text1multiplechoice)
                    val questionTextView2: CheckBox =
                        viewHolder.itemView.findViewById(R.id.text2multiplechoice)
                    val questionTextView3: CheckBox =
                        viewHolder.itemView.findViewById(R.id.text3multiplechoice)
                    val questionTextView4: CheckBox =
                        viewHolder.itemView.findViewById(R.id.text4multiplechoice)
                    val questionTextView5: CheckBox =
                        viewHolder.itemView.findViewById(R.id.text5multiplechoice)




                    val CheckBoxes = listOf<CheckBox>(
                        questionTextView1,
                        questionTextView2,
                        questionTextView3,
                        questionTextView4,
                        questionTextView5
                    )

                    var flag = true
                    var text = ""
                    var index = 0;
                    var aaa=currentQuestion.question_text
                    Log.d("QuizAdapter", "IsCheck : $aaa")

                    CheckBoxes.forEach { checkBox ->
                        // Perform your action with each checkBox here
                        var apanthsh = checkBox.isChecked
                        var kati=currentQuestion.correctAnswers.contains(index.toLong())
                        Log.d("QuizAdapter", "IsCheck : $apanthsh Index : $kati")
                        if ( !apanthsh == kati  ) {
                            Log.d("QuizAdapter", "LAAAATTTHHHHOOSSS")
                            text += checkBox.text
                            text += " \n"
                            flag = false
                        }
                        index++
                    }
                    if(flag){
                        updateQHistory(currentQuestion.question_id.toInt(), true,flagforH)

                    }else{
                        updateQHistory(currentQuestion.question_id.toInt(), false,flagforH)

                    }

                    Log.d("QuizAdapter", "Flag : $flag")


                    // Now you can use isChecked1, isChecked2, isChecked3, isChecked4, isChecked5
                    // For example, logging their values

                    return Pair(flag,text)
                }
                is ViewHolderTypeTwo -> {
                    var flag = true
                    val questionTextView5: TextView =
                        viewHolder.itemView.findViewById(R.id.souloutext)
                    val isTrueButtonClicked = viewHolder.getCurrentState()
                    Log.d("flagg", "I ${isTrueButtonClicked} , ${currentQuestion.correctAnswers[0].toInt()} , ${isTrueButtonClicked==(currentQuestion.correctAnswers[0].toInt() ==1)}")
                    if (isTrueButtonClicked==(currentQuestion.correctAnswers[0].toInt() ==1)) {
                        Log.d("QuizFragment2", "Is True Button Clicked: sosto")
                        Log.d("QuizFragment2", "${currentQuestion.question_id}")
                        updateQHistory(currentQuestion.question_id.toInt(), true,flagforH)
                        flag = true
                    }else{
                        Log.d("QuizFragment2", "Is True Button Clicked: lathos")
                        updateQHistory(currentQuestion.question_id.toInt(), false,flagforH)
                        flag = false
                    }
                    Log.d("flagg", "Id ${flag} ")
                    if(flag) {
                        return Pair(flag, "Apanthses Sosta")
                    }else{
                        if(!isTrueButtonClicked ){
                            return Pair(flag, "Apanthses Lathos all einai Sosto")
                        }else{
                            return Pair(flag, "Apanthses Sosto all einai Lathos")
                        }
                    }
                }

                is ViewHolderTypeThree -> {
                    var flag =true;
                    var text = ""
                    var minMistakes = Int.MAX_VALUE
                    var bestMatch = 0
                    var index2 = 0

                    currentQuestion.correctAnswers.forEach { correctAnswer ->
                        val correctAnswerString = correctAnswer.toString()
                        val buttonOrderString = buttonClickOrder.joinToString("")
                        var mistakes = 0

                        for (index in correctAnswerString.indices) {
                            if (index >= buttonOrderString.length || buttonOrderString[index] != correctAnswerString[index]) {
                                mistakes++
                            }
                        }

                        if (mistakes < minMistakes) {
                            minMistakes = mistakes
                            bestMatch = index2
                        }
                        index2++
                    }


                    val correctAnswerString = currentQuestion.correctAnswers[bestMatch].toString()
                    val buttonOrderString = buttonClickOrder.joinToString("")

                    for (index in currentQuestion.correctAnswers[bestMatch].toString().indices) {
                        if (index >= buttonOrderString.length || buttonOrderString[index] != correctAnswerString[index]) {
                            text +=  " expected " + currentQuestion.possibleAnswers[index] + " " + "found " + buttonOrderString.getOrNull(index)?.toString()
                        }
                    }

                    Log.d("Three3", "Result: ${minMistakes}")
                    if (minMistakes<1) {

                        Log.d("Three3", "Result: Correct")
                        updateQHistory(currentQuestion.question_id.toInt(), true,flagforH)
                        return Pair(flag,"Sosta")


                    } else {
                        Log.d("Three3", "Result: False")
                        updateQHistory(currentQuestion.question_id.toInt(), false,flagforH)
                        return Pair(false,text)

                    }

                }

                is ViewHolderTypeFour -> {
                    // Get the final order
                    val finalOrder = viewHolder.getCurrentOrder()
                    val correctAnswers = currentQuestion.correctAnswers
                    Log.d("QuizFragment2", "Final Order is correct: ${finalOrder}")


                    val combinedString = correctAnswers[0].toString() // Convert the single element to a string
                    Log.d("QuizFragment2", "combinedString : ${combinedString}")
                   // val correctedCorrectAnswers = combinedString.map { it.toString().toInt() }
                    var flag = true

                    var characterCount = combinedString.length
                    if(characterCount>9){
                        characterCount = (characterCount-9)/2 +9
                    }
                    val correctNumbers: MutableList<Int> = mutableListOf()

                    Log.d("QuizFragment2", "characterCount: ${characterCount}")
                    var i = 0
                    while (i < combinedString.length-1) {
                        Log.d("QuizFragment2", "auroisma: ${combinedString[i+1].digitToInt()+10} gia ${combinedString[i+1]}")
                        if (combinedString[i] == '1' && characterCount >= combinedString[i+1].digitToInt()+10) {
                            // If the current character is '1' and the next character is '2', combine them
                            Log.d("QuizFragment2", "mphka")
                            correctNumbers.add(combinedString[i+1].digitToInt()+10)
                            Log.d("QuizFragment2", "mphka  ${combinedString[i+1].digitToInt()+10}")
                            i++
                            i++
                        // Skip the next character as it has already been processed
                        } else {
                            // Otherwise, add the current character as it is
                         //   Log.d("QuizFragment2", "καθενα: ${combinedString[i]}")
                          //  Log.d("QuizFragment2", "else: ${combinedString[i].digitToInt()}")
                            correctNumbers.add(combinedString[i].digitToInt())
                            Log.d("QuizFragment2", "mphka + ${combinedString[i].digitToInt()}")
                            i++ // Move to the next character
                        }

                        if (i==combinedString.length-1){
                            correctNumbers.add(combinedString[i].digitToInt())
                        }
                    }

                    Log.d("finalOrder", "finalOrder" + finalOrder)
                    Log.d("finalOrder", "correct" + correctNumbers)
                    Log.d("QuizFragment2", "${finalOrder} + ${correctNumbers} + ${finalOrder == correctAnswers}")
                    // Initialize an empty string to store the mistake message
                    var mistakeMessage = ""

                    if (finalOrder == correctNumbers) {
                        // Lists are equal, show a Toast
                        val toastText = "Final Order is correct: $finalOrder"
                        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                        Log.d("QuizFragment2", "Is True Button Clicked: sosto")
                        Log.d("QuizFragment2", "${currentQuestion.question_id}")
                        updateQHistory(currentQuestion.question_id.toInt(), true,flagforH) // Pass 'true' if the answer is correct, 'false' otherwise
                        flag = true

                    } else {
                        // Lists are not equal, handle the case accordingly
                        val differences = finalOrder.zip(correctNumbers).mapNotNull {
                            if (it.first != it.second) it else null
                        }
                        Log.d("QuizFragment2", "${differences}")

                        // Construct a mistake message based on the differences
                        mistakeMessage = differences.joinToString(separator = ", ") { "(Expected: ${it.second}, Found: ${it.first})" }
                        val toastText = "Final Order is incorrect. Mistakes: $mistakeMessage"
                        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                        updateQHistory(currentQuestion.question_id.toInt(), false,flagforH)
                        flag = false
                    }

// Return the flag and the mistake message instead of "fasdf"
                    return Pair(flag, mistakeMessage)


                }

                is ViewHolderTypeFive -> {
                    var ListWords = viewHolder.showColoredText().map { it.replace("‘", "").replace("’", "").trim() }
                    ListWords.forEach { word ->
                        Log.d("Four", "Colored Word: $word")
                    }
                    var flag = true
                    var text = ""

                    // Normalize possibleAnswers and compare
                    val normalizedPossibleAnswers = currentQuestion.possibleAnswers.map { it.replace("‘", "").replace("’", "").trim() }

                    // Find missed answers
                    val missedAnswers = normalizedPossibleAnswers.filter { answer ->
                        !ListWords.contains(answer)
                    }

                    // Find unnecessary words
                    val unnecessaryWords = ListWords.filter { word ->
                        !normalizedPossibleAnswers.contains(word)
                    }

                    // Logging the results
                    if (missedAnswers.isNotEmpty()) {
                        Log.d("Four", "Missed Answers: ${missedAnswers.joinToString(", ")}")
                        text +=  missedAnswers.joinToString(", ")
                    } else {
                        Log.d("Four", "All answers found.")
                    }

                    if (unnecessaryWords.isNotEmpty()) {
                        Log.d("Four", "Unnecessary Words: ${unnecessaryWords.joinToString(", ")}")
                        text +="/"
                        text +=  unnecessaryWords.joinToString(", ")
                    }
                    if(missedAnswers.isEmpty() && unnecessaryWords.isEmpty()){
                        updateQHistory(currentQuestion.question_id.toInt(), true,flagforH)
                        flag = true

                    }else{
                        updateQHistory(currentQuestion.question_id.toInt(), false,flagforH)
                        flag = false

                    }
                    return Pair(flag,text)

                }





            }

        }

        return Pair(true,"LATHOS")
    }

    fun updateQHistory(questionId: Int, isCorrect: Boolean,flagforH: Int) {
        if (flagforH==1) {
            val segmentLength = 5
            val startIndex = (questionId - 1) * segmentLength
            val endIndex = startIndex + segmentLength

            // Ensure the indices are within the bounds of the string
            if (startIndex < myProfile.qHistory.length && endIndex <= myProfile.qHistory.length) {
                val newAnswer = if (isCorrect) 'T' else 'F'
                val currentSegment = myProfile.qHistory.substring(startIndex, endIndex)
                val updatedSegment = newAnswer + currentSegment.substring(0, segmentLength - 1)

                myProfile.qHistory = myProfile.qHistory.substring(0, startIndex) +
                        updatedSegment +
                        myProfile.qHistory.substring(endIndex)
            }
            Log.d("Three3", "${myProfile.qHistory}")
        }
    }


    fun extractNumbers(input: String): List<Int> {
        val numbers = mutableListOf<Int>()
        var i = 0
        var characterCount = input.length
        if(characterCount>9){
            characterCount = (characterCount-9)/2 +9
        }

        while (i < input.length) {
            var number = input[i].toString().toInt()

            // Check for two-digit number possibility
            if (i + 1 < input.length) {
                val nextDigit = input[i + 1].toString().toInt()
                val twoDigitNumber = number * 10 + nextDigit

                // Check if the two-digit number is logical in the current sequence
                if (numbers.isEmpty() || twoDigitNumber == numbers.last() + 1) {
                    number = twoDigitNumber
                    i++
                }
            }

            numbers.add(number)
            i++
        }

        return numbers
    }



}
