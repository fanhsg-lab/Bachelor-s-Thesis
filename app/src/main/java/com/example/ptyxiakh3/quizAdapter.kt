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
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ptyxiakh3.DbQuery.myProfile
import com.example.ptyxiakh3.data.Question
import com.google.android.flexbox.FlexboxLayout
import java.util.Collections

class quizAdapter(
    private val context: Context,
    private val quizModels: ArrayList<Question>,
    private val navController: NavController,
    var layoutChangeListener: (() -> Unit)? = null,
    private var isTrueButtonClicked: Boolean = false,
    private var isFalseButtonClicked: Boolean = false
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val buttonClickOrder = mutableListOf<Int>()

    companion object {
        const val TYPE_ONE = 0
        const val TYPE_TWO = 1
        const val TYPE_THREE = 2
        const val TYPE_FOUR = 3
        const val TYPE_FIVE = 4
        const val TYPE_SIX = 5
    }

    override fun getItemViewType(position: Int): Int {
        // Example condition based on the 'style' attribute of the Question
        return when (quizModels[position].style) {
            "multiple choice" -> TYPE_ONE
            "SouLou" -> TYPE_TWO
            "Kena" -> TYPE_THREE
            "Queue" -> TYPE_FOUR
            "Mistakes" -> TYPE_FIVE
            "Fill" -> TYPE_SIX
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
            TYPE_ONE -> ViewHolderTypeOne(inflater.inflate(R.layout.quiz_item_layout_multiple, parent, false), context)
            TYPE_TWO -> ViewHolderTypeTwo(inflater.inflate(R.layout.quiz_item_layout_soulou, parent, false))
            TYPE_THREE -> ViewHolderTypeThree(inflater.inflate(R.layout.quiz_item_layout_kena, parent, false))
            TYPE_FOUR -> ViewHolderTypeFour(inflater.inflate(R.layout.quiz_item_layout_queue, parent, false), context)
            TYPE_FIVE -> ViewHolderTypeFive(inflater.inflate(R.layout.quiz_item_layout_mistakes, parent, false))
            TYPE_SIX -> ViewHolderTypeSix(inflater.inflate(R.layout.quiz_item_layout_fill, parent, false))
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
            is ViewHolderTypeSix -> holder.bind(question)
        }
        Log.d("quizAdapter2", "mphka")
    }

    override fun getItemCount(): Int = quizModels.size

    // ViewHolder for type one
    class ViewHolderTypeOne(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {
        private val questionTextView1: CheckBox = itemView.findViewById(R.id.text1multiplechoice)
        private val questionTextView2: CheckBox = itemView.findViewById(R.id.text2multiplechoice)
        private val questionTextView3: CheckBox = itemView.findViewById(R.id.text3multiplechoice)
        private val questionTextView4: CheckBox = itemView.findViewById(R.id.text4multiplechoice)
        private val questionTextView5: CheckBox = itemView.findViewById(R.id.text5multiplechoice)
        private var question_image: ImageView = itemView.findViewById(R.id.question_image)
        private val questionText: TextView = itemView.findViewById(R.id.quetextmultiplechoice)

        val questionTextViews = listOf<TextView>(questionTextView1, questionTextView2, questionTextView3, questionTextView4, questionTextView5)

        fun bind(question: Question) {
            Log.d("quizAdapter2", "mphka1")
            // Bind data for type one
            questionText.text = question.question_text
            if (question.image != 0) {
                question_image.setImageDrawable(ContextCompat.getDrawable(context, question.image))
                question_image.visibility=View.VISIBLE
            }
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
    inner class ViewHolderTypeTwo(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val questionTextView: TextView = itemView.findViewById(R.id.souloutext)
        private val Titlos: TextView = itemView.findViewById(R.id.titlossoulou)
        private val falsebut: Button = itemView.findViewById(R.id.falsebut_image)
        private val truebut: Button = itemView.findViewById(R.id.truebut_image)

        private val nextButtonDrawableId = R.drawable.next_button_drawable
        private val tfButtonsClickedDrawableId = R.drawable.tf_buttons_clicked
        private val startingBackground = R.drawable.next_button_drawable


        init {
            Log.d("flagError4", " 5 $isTrueButtonClicked")

            isTrueButtonClicked = false
            isFalseButtonClicked = false
            falsebut.setOnClickListener {
                // Check if the true button is already clicked

                    truebut.setTextColor(Color.parseColor("white"))
                    truebut.setBackgroundResource(nextButtonDrawableId)


                falsebut.setTextColor(Color.parseColor("#353a4f"))
                falsebut.setBackgroundResource(tfButtonsClickedDrawableId)

                // Update the state
                isTrueButtonClicked = false
                isFalseButtonClicked = true
            }

            truebut.setOnClickListener {
                // Check if the false button is already clicked

                    falsebut.setTextColor(Color.parseColor("white"))
                    falsebut.setBackgroundResource(nextButtonDrawableId)


                truebut.setTextColor(Color.parseColor("#353a4f"))
                truebut.setBackgroundResource(tfButtonsClickedDrawableId)

                // Update the state
                isTrueButtonClicked = true
                isFalseButtonClicked = false
            }
        }

        fun getCurrentState(): Pair<Boolean, Boolean> {
            return Pair(isTrueButtonClicked, isFalseButtonClicked)
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
                    Log.d("Kenatext3","${currentPlaceholderIndex} , ${placeholders.size}" )
                    if (currentPlaceholderIndex < characterCount) {
                        buttonClickHistory.add(
                            ButtonClickState(
                                paragraphTextView.text.toString(),
                                button.id
                            )
                        )
                        buttonClickOrder.add(currentIndex + 1) // Use captured index value here (+1 if you want to start from 1 instead of 0)
                        Log.d("Kenatext3","buttonClickOrder , ${currentIndex + 1}" )
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
        val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                // Swap the items in the list
                Collections.swap(queueModels, fromPosition, toPosition)
                recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)
                Log.d("ItemTouchHelper", "RecyclerView id: ${recyclerView.id}, Item moved from $fromPosition to $toPosition")
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Handle swipe actions if any (not used in this example)
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                Log.d("ItemTouchHelper", "Drag finished")
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)


        init {
            // Attach ItemTouchHelper to enable drag-and-drop
            Log.d("Fillerror","omos 11fd111")
            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }

        fun bind(question: Question) {
            // ... (existing code)
            var index = 1
            titletext.text=question.question_text2
            Log.d("QuizAdapter22", "Possible Answers Before Iteration: ${question.possibleAnswers.joinToString()}")
            Log.d("Fillerror","omos 2")
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
                                        coloredWords.remove(it.range)
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
                                        coloredWords.add(it.range)
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
                originalText.substring(range.first, range.last + 1) // Adjusted to include the end character
            }

            val coloredTextString = coloredTextList.joinToString(", ")
            //Toast.makeText(itemView.context, "Colored words: $coloredTextString", Toast.LENGTH_LONG).show()

            return coloredTextList
        }
    }

// Usage in ViewHolderTypeFive




    class ViewHolderTypeSix(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val questionTextView: TextView = itemView.findViewById(R.id.paragraphTextView)
        private val Titlos: TextView = itemView.findViewById(R.id.titlosfill)

        private val nextButtonDrawableId = R.drawable.next_button_drawable
        private val tfButtonsClickedDrawableId = R.drawable.tf_buttons_clicked
        private val startingBackground = R.drawable.next_button_drawable
        private val questionTextView1: TextView = itemView.findViewById(R.id.gramma1)
        private val questionTextView2: TextView = itemView.findViewById(R.id.gramma2)
        private val questionTextView3: TextView = itemView.findViewById(R.id.gramma3)
        private val questionTextView4: TextView = itemView.findViewById(R.id.gramma4)
        private val questionTextView5: TextView = itemView.findViewById(R.id.gramma5)

        private val lin5: LinearLayout = itemView.findViewById(R.id.lin5)
        private val lin4: LinearLayout = itemView.findViewById(R.id.lin4)
        private val lin3: LinearLayout = itemView.findViewById(R.id.lin3)
        private val lin2: LinearLayout = itemView.findViewById(R.id.lin2)
        private val lin1: LinearLayout = itemView.findViewById(R.id.lin1)

        val questionTextViews = listOf<TextView>(questionTextView1, questionTextView2, questionTextView3,questionTextView4,questionTextView5)
        val lins = listOf<LinearLayout>(lin1, lin2, lin3, lin4 ,lin5)

        fun bind(question: Question) {
            Log.d("quizAdapter2", "mphka2")
            // Bind data for type one
            questionTextView.text = question.question_text
            Titlos.text=question.question_text2

            lins.forEach { lin ->

                lin.visibility = View.GONE
            }

            question.possibleAnswers.forEachIndexed { index, answer ->
                if (index < questionTextViews.size) {
                    questionTextViews[index].text = answer + " ="
                    lins[index].visibility = View.VISIBLE
                }
            }

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
                        Log.d("QuizAdapter", "index : $index")
                        var kati=currentQuestion.correctAnswers.contains(index.toLong())
                        Log.d("QuizAdapter", "IsCheck : $apanthsh kati : $kati")
                        if ( !apanthsh == kati  ) {
                            Log.d("QuizAdapter", "LAAAATTTHHHHOOSSS")
                            text += " expected "
                            text += index

                            flag = false
                        }

                        index++
                    }
                    Log.d("MultipleTExt", "Text : $text")
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
                    var flag = false
                    val state = viewHolder.getCurrentState()
                    val questionTextView5: TextView =
                        viewHolder.itemView.findViewById(R.id.souloutext)
                    val isTrueButtonClicked = state.first
                    val isFalseButtonClicked = state.second
                    Log.d("flagError1", "$flag")
                    Log.d("flagError4", "$isTrueButtonClicked")
                    Log.d("Scoremessage", "Is True Button Clicked: sosto")
                    Log.d("flagg", "I ${isTrueButtonClicked} , ${currentQuestion.correctAnswers[0].toInt()} , ${isTrueButtonClicked==(currentQuestion.correctAnswers[0].toInt() ==1)}")
                    Log.d("j", "mphka2")
                  if(isTrueButtonClicked || isFalseButtonClicked) {
                      if (isTrueButtonClicked == (currentQuestion.correctAnswers[0].toInt() == 1)) {
                          Log.d("Three4", "Is True Button Clicked: sosto")
                          Log.d("QuizFragment2", "${currentQuestion.question_id}")
                          updateQHistory(currentQuestion.question_id.toInt(), true, flagforH)
                          flag = true
                          Log.d("DataQuiz", "mphka1")
                      } else {
                          Log.d("Three4", "Is True Button Clicked: lathos")
                          updateQHistory(currentQuestion.question_id.toInt(), false, flagforH)
                          flag = false
                          Log.d("flagError3", "$flag")
                          Log.d("DataQuiz", "mphka3")
                      }
                  }
                    Log.d("Three3","KAI OMOS2")
                    Log.d("flagg", "Id ${flag} ")
                    if(flag) {
                        return Pair(flag, "Σωστά")
                    }else{
                        if(!isTrueButtonClicked ){
                            return Pair(flag, "Λάθος")
                        }else{
                            return Pair(flag, "Λάθος")
                        }
                    }
                }

                is ViewHolderTypeThree -> {
                    var flag =true;

                    var finaltext =""
                    var minMistakes = Int.MAX_VALUE
                    var bestMatch = 0
                    var index2 = 0
                    var minText =""
                    Log.d("Fillerror","mphka 6")
                    currentQuestion.correctAnswers.forEach { correctAnswer ->
                        val correctAnswerString = correctAnswer.toString()
                        val buttonOrderString = buttonClickOrder.joinToString("")
                        Log.d("Three5","buttonOrderString ,$buttonOrderString" )
                        var mistakes = 0
                        var text = ""

                        for (index in correctAnswerString.indices) {
                            if (index >= buttonOrderString.length || buttonOrderString[index] != correctAnswerString[index]) {
                                mistakes++
                                text +=  " expected " +correctAnswerString[index].toString()

                            }
                        }

                        if (mistakes < minMistakes) {
                            minMistakes = mistakes
                            bestMatch = index2
                            minText = text
                            Log.d("Three5","minText ,$minText" )
                        }
                        index2++
                        Log.d("Three5","mphkaCurrent" )
                    }
                    finaltext = minText
                    Log.d("Kenatext5","finaltext $finaltext" )
                    val correctAnswerString = currentQuestion.correctAnswers[bestMatch].toString()
                    val buttonOrderString = buttonClickOrder.joinToString("")



                    Log.d("Three3","KAI OMOS")

                    Log.d("Three3", "Result: ${minMistakes}")
                    if (minMistakes<1) {
                        Log.d("Scoremessage","Flag true")
                        Log.d("Three4", "Result: Correct")
                        updateQHistory(currentQuestion.question_id.toInt(), true,flagforH)
                        return Pair(flag,"Sosta")


                    } else {
                        Log.d("Three4", "Result: False")
                        Log.d("Scoremessage","Flag false")
                        updateQHistory(currentQuestion.question_id.toInt(), false,flagforH)
                        return Pair(false,finaltext)

                    }

                }

                is ViewHolderTypeFour -> {
                    // Get the final order
                    Log.d("Fillerror","mphka 5")
                    val finalOrder = viewHolder.getCurrentOrder() // Υποθέτουμε ότι αυτή είναι μια λίστα αριθμών
                    val correctAnswers = currentQuestion.correctAnswers // Υποθέτουμε ότι αυτή είναι μια λίστα αριθμών

                    Log.d("wert", "finalOrder $finalOrder")
                    Log.d("wert", "correct $correctAnswers")

                    var flag = true
                    val mistakes = mutableListOf<String>()

                    // Συγκρίνουμε κάθε στοιχείο της λίστας
                    for (i in finalOrder.indices) {
                        if (i >= correctAnswers.size || finalOrder[i].toLong() != correctAnswers[i]) {
                            flag = false
                            mistakes.add("Position ${i + 1}: Expected ${correctAnswers.getOrNull(i)}, but got ${finalOrder.getOrNull(i)}")
                        }
                    }

                    if (flag) {
                        Log.d("Three6", "The final order is correct!")
                        updateQHistory(currentQuestion.question_id.toInt(), true,flagforH)
                    } else {
                        Log.d("Three6", "The final order is incorrect.")
                        mistakes.forEach { Log.d("OrderCheck", it) }
                        updateQHistory(currentQuestion.question_id.toInt(), false,flagforH)
                    }

                    Log.d("QuizFragment2", "Final Order is correct: $flag")

                    val mistakeMessage = if (mistakes.isNotEmpty()) mistakes.joinToString("\n") else "No mistakes"
                    Log.d("OrderCheck", "mistakeMessage $mistakeMessage")
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
                        text +=" expected "
                        text +=  missedAnswers.joinToString(" expected ")
                    } else {
                        Log.d("Four", "All answers found.")
                    }

                    if (unnecessaryWords.isNotEmpty()) {
                        text +=" expected "
                        text +=  unnecessaryWords.joinToString(" expected ")
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


                is ViewHolderTypeSix -> {
                    val questionTextView1: EditText =
                        viewHolder.itemView.findViewById(R.id.keno1)
                    questionTextView1.clearFocus()
                    val questionTextView2: EditText =
                        viewHolder.itemView.findViewById(R.id.keno2)
                    questionTextView2.clearFocus()
                    val questionTextView3: EditText =
                        viewHolder.itemView.findViewById(R.id.keno3)
                    questionTextView3.clearFocus()
                    val questionTextView4: EditText =
                        viewHolder.itemView.findViewById(R.id.keno4)
                    questionTextView4.clearFocus()
                    val questionTextView5: EditText =
                        viewHolder.itemView.findViewById(R.id.keno5)
                    questionTextView4.clearFocus()








                    val EditTexts = listOf<EditText>(
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

                    currentQuestion.correctAnswers.forEach { correctAnswer ->
                            // Perform your action with each checkBox here

                            val apanthsh = EditTexts[index].text.toString().toLongOrNull()
                            var kati = currentQuestion.correctAnswers[index]
                        Log.d("Fillerror2","correctAnswer $correctAnswer  kati  $kati")
                            Log.d("QuizAdapter", "IsCheck : $apanthsh Index : $kati")
                            if(apanthsh == kati){
                                Log.d("QuizAdapter", "Mphka")
                            }else{
                                text +=  " index " + index + " , "
                                flag =false
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



            }

        }

        return Pair(true,"LATHOS")
    }

    fun updateQHistory(questionId: Int, isCorrect: Boolean,flagforH: Int) {
        Log.d("Three4", "flagforH  ${flagforH} ")
        if (flagforH==1) {
            buttonClickOrder.clear()
            val segmentLength = 5
            val startIndex = (questionId - 1) * segmentLength
            val endIndex = startIndex + segmentLength
Log.d("DataQuiz","questionId $questionId ")
            // Ensure the indices are within the bounds of the string
            if (startIndex < myProfile.qHistory.length && endIndex <= myProfile.qHistory.length) {
                val newAnswer = if (isCorrect) 'T' else 'F'
                val currentSegment = myProfile.qHistory.substring(startIndex, endIndex)
                val updatedSegment = newAnswer + currentSegment.substring(0, segmentLength - 1)
                Log.d("Three6", "currentSegment  ${currentSegment}  updatedSegment  ${updatedSegment}")
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
