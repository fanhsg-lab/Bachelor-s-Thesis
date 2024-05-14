package com.example.ptyxiakh3

import QuestionsViewModel
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

class  AllQuestionsFragment : Fragment(), ChildItemClickListener {
    private var param1: String? = null
    private var param2: String? = null
    private val questionsViewModel: QuestionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_all_questions, container, false)

        // Initialize an empty LinkedHashMap
        // Initialize an empty LinkedHashMap with chapters as keys
        val chapters = listOf("Syntax", "Python", "Conditional")
        val data: LinkedHashMap<String, List<Quartet<String, Int, Long, String>>> = linkedMapOf<String, List<Quartet<String, Int, Long, String>>>().apply {
            chapters.forEach { chapter ->
                this[chapter] = listOf()
            }
        }

        val adapter = MyCustomAdapter(requireContext(), data, this) // Updated to pass 'this'
        val expandableListView: ExpandableListView = view.findViewById(R.id.expandableListView)
        expandableListView.setAdapter(adapter)






        expandableListView.setAdapter(adapter)

        chapters.forEach { chapter ->
            questionsViewModel.getQuestionsByModule(chapter).observe(viewLifecycleOwner, Observer { questions ->
                if (questions.isEmpty()) {
                    Log.d("QuestionsLog", "No questions found for $chapter module")
                } else {
                    Log.d("QuestionsLog", "Number of questions for $chapter module: ${questions.size}")
                    data[chapter] = questions.map { question ->
                        // Log the question ID first
                        Log.d("QuestionsLog", "question ID: ${question.question_id}")
                        Log.d("allQ2","Id: ${question.question_id}  $chapter module:")
                        // Then return the Quartet as the last expression in the lambda
                        Quartet(question.question_number.toString(), question.difficulty, question.question_id, question.style)
                    }




                    adapter.notifyDataSetChanged()
                }
            })

        }


        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            Log.d("ExpandableListViewDebug", "Child Clicked: GroupPosition: $groupPosition, ChildPosition: $childPosition")
            val childItem = adapter.getChild(groupPosition, childPosition) as Quartet<String, Int, Long, String>
            Log.d("ExpandableListViewDebug", "Clicked Item: ${childItem.first}")
            Toast.makeText(context, "Clicked: ${childItem.first}", Toast.LENGTH_SHORT).show()
            true // Return true to indicate that the click was handled
        }


        val Popup = view.findViewById<LinearLayout>(R.id.ModulepopupLayout)
        val infoBtn = view.findViewById<ImageView>(R.id.info)
        val closeBtn = view.findViewById<ImageView>(R.id.closeBtn)

        closeBtn.setOnClickListener{
            Popup?.visibility=View.GONE
        }

        infoBtn.setOnClickListener{
            Popup?.visibility=View.VISIBLE
        }


        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val targetModule = "Chapter1"

        questionsViewModel.getQuestionsByModule(targetModule).observe(viewLifecycleOwner, Observer { questions ->

            if (questions.isEmpty()) {
                Log.d("QuestionsLog", "No questions found for $targetModule module")
            } else {
                Log.d("QuestionsLog", "Number of questions for $targetModule module: ${questions.size}")
                for (question in questions) {
                    Log.d("QuestionsLog", "Question ID: ${question.question_id}, Modules: ${question.modules}")
                }
            }
        })
    }

    private var lastClickedGroupPosition: Int? = null
    private var lastClickedChildPosition: Int? = null
    override fun onChildItemClick(groupPosition: Int, childPosition: Int, questionId: Long) {
        Log.d("ChildItemClick", "Clicked on child at groupPosition: $groupPosition, childPosition: $childPosition, childId: $questionId")

        // Find the ConstraintLayout
        val constraintLayout = view?.findViewById<LinearLayout>(R.id.popup)
        constraintLayout?.let {

            if (it.visibility == View.VISIBLE) {
                it.visibility = View.GONE
            }else {
                questionsViewModel.getQuestionById(questionId).observe(viewLifecycleOwner) { question ->

                    Log.d("apapapa","edo")
                    val quesNo = view?.findViewById<TextView>(R.id.quesNo)
                    val quesstyle = view?.findViewById<TextView>(R.id.quesstyle)
                    val questionText = view?.findViewById<TextView>(R.id.question)
                    val text = view?.findViewById<TextView>(R.id.text)
                    val optionA = view?.findViewById<TextView>(R.id.optionA)
                    val optionB = view?.findViewById<TextView>(R.id.optionB)
                    val optionC = view?.findViewById<TextView>(R.id.optionC)
                    val optionD = view?.findViewById<TextView>(R.id.optionD)
                    val result = view?.findViewById<TextView>(R.id.result)
                    val lasttext = view?.findViewById<TextView>(R.id.lasttext)


                    quesNo?.text ="Question's ID: "+ question.question_id.toString()
                    quesstyle?.text ="Style: "+ question.style.toString()

                    if(question.style=="SouLou") {
                        text?.visibility = View.VISIBLE
                        lasttext?.visibility=View.GONE
                        questionText?.text = question.possibleAnswers[0]
                        text?.text = question.question_text
                        result?.visibility = View.VISIBLE

                        if(question.correctAnswers[0].toInt() ==1){
                            result?.text = "True"
                        }else{
                            result?.text = "False"
                        }
                        optionA?.visibility = View.GONE
                        optionB?.visibility = View.GONE
                        optionC?.visibility = View.GONE
                        optionD?.visibility = View.GONE
                    }

                    if (question.style == "Kena") {
                        lasttext?.visibility=View.GONE
                        text?.visibility = View.VISIBLE
                        optionA?.visibility = View.GONE
                        optionB?.visibility = View.GONE
                        optionC?.visibility = View.GONE
                        optionD?.visibility = View.GONE
                        result?.visibility = View.GONE
                        val sequence = question.correctAnswers.first().toString()
                        var updatedQuestionText = question.question_text
                        questionText?.text = question.question_text2
                        // Placeholder to keep track of where we've inserted answers
                        val placeholders = mutableListOf<Pair<String, IntRange>>()

                        sequence.forEachIndexed { index, digit ->
                            val answerIndex = if (digit == '0') 9 else digit.toString().toInt() - 1
                            val answer = question.possibleAnswers[answerIndex]

                            // Find the start position of the first "[____]"
                            val blankStart = updatedQuestionText.indexOf("[____]")

                            if (blankStart != -1) {
                                // Calculate the end position for the answer
                                val answerEnd = blankStart + answer.length

                                // Replace the first "[____]" with the answer and update the text
                                updatedQuestionText = updatedQuestionText.replaceFirst("[____]", answer)

                                // Keep track of the answer and its position
                                placeholders.add(answer to IntRange(blankStart, answerEnd))
                            }
                        }

                        // Create a SpannableString from the updated text
                        val spannableString = SpannableString(updatedQuestionText)


                        // Apply color spans to each answer
                        placeholders.forEach { (answer, range) ->
                            // Assuming you have a Context available as 'context'
                            val textColor = ContextCompat.getColor(requireContext(), R.color.muted_orange)

                            val colorSpan = ForegroundColorSpan(textColor)

                            val sizeSpan = RelativeSizeSpan(1.3f) // Adjust the 1.5f to your desired size multiplier

                            spannableString.setSpan(colorSpan, range.first, range.last, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
                            spannableString.setSpan(sizeSpan, range.first, range.last, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

                        }


                        // Set the spannable string to your TextView
                        text?.text = spannableString
                    }

                    if (question.style == "Mistakes") {
                        Log.d("apapapa","edo2")
                        text?.visibility = View.VISIBLE
                        optionA?.visibility = View.GONE
                        optionB?.visibility = View.GONE
                        optionC?.visibility = View.GONE
                        optionD?.visibility = View.GONE
                        result?.visibility = View.GONE
                        lasttext?.visibility=View.VISIBLE
                        questionText?.text = question.question_text2
                        // Create a SpannableString from the question text
                        val spannableString = SpannableString(question.question_text)

                        question.possibleAnswers.forEach { answer ->
                            var startIndex = spannableString.indexOf(answer, ignoreCase = true)

                            while (startIndex >= 0) {
                                // Calculate the end index of the answer
                                val endIndex = startIndex + answer.length

                                // Create a span to change the color of the answer
                                val textColor = ContextCompat.getColor(requireContext(), R.color.muted_orange)
                                val colorSpan = ForegroundColorSpan(textColor)

                                // Apply the color span on the spannable string
                                spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

                                val sizeSpan = RelativeSizeSpan(1.3f) // Adjust the 1.5f to your desired size multiplier

                                spannableString.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE)


                                // Find the next occurrence of the answer
                                startIndex = spannableString.indexOf(answer, startIndex + 1, ignoreCase = true)
                            }
                        }

                        // Set the spannable string to your TextView
                        text?.text = spannableString

                        // Define the prefix text
                        val prefixText = "Τα σωστά είναι: "

// Initialize the SpannableStringBuilder with the prefix text
                        val spannableBuilder = SpannableStringBuilder(prefixText)

// Assuming question.question_module is a List<String>
                        val questionModule = question.question_module

// Append each word from the list and apply styles
                        questionModule.forEach { word ->
                            val start = spannableBuilder.length
                            spannableBuilder.append(word + ", ")
                            val end = spannableBuilder.length

                            // Apply color span
                            val textColor = ContextCompat.getColor(requireContext(), R.color.muted_orange)
                            val colorSpan = ForegroundColorSpan(textColor)
                            spannableBuilder.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

                            // Apply size span
                            val sizeSpan = RelativeSizeSpan(1.3f)  // Adjust this value as needed
                            spannableBuilder.setSpan(sizeSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                        }

// Set the SpannableStringBuilder to a TextView
                        lasttext?.text = spannableBuilder


                    }

                    if (question.style == "multiple choice") {
                        lasttext?.visibility=View.GONE
                        questionText?.text = question.question_text
                        text?.visibility = View.GONE
                        result?.visibility = View.GONE

                        // Iterate over all possible answers
                        question.possibleAnswers.forEachIndexed { index, option ->
                            // Determine the TextView for the current option (e.g., optionA, optionB, etc.)
                            val optionTextView = when (index) {
                                0 -> optionA
                                1 -> optionB
                                2 -> optionC
                                3 -> optionD
                                else -> null // Add more cases if you have more options
                            }

                            // Set the text for the option
                            optionTextView?.text = option
                            optionTextView?.visibility = View.VISIBLE
                            optionTextView?.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_color))
                            optionTextView?.textSize= 15f
                            // Check if the current option is a correct answer
                            if (question.correctAnswers.contains(index.toLong())) { // Assuming correctAnswers are 1-indexed
                                // Assuming 'this' or 'getActivity()' can be used to obtain a Context. Adjust accordingly.
                                optionTextView?.setTextColor(ContextCompat.getColor(requireContext(), R.color.muted_orange));
                                Log.d(
                                    "alll",
                                    "Updated content for question: $question, questionId: $questionId"
                                )
                                optionTextView?.textSize= 22f

                            }
                        }
                        Log.d("Mistakesall", "point2")

                    }

                    if(question.style=="Queue") {
                        lasttext?.visibility=View.GONE
                        text?.visibility = View.VISIBLE
                        questionText?.text = question.question_text2
                        text?.text = question.question_text

                        result?.visibility = View.GONE

                        optionA?.visibility = View.GONE
                        optionB?.visibility = View.GONE
                        optionC?.visibility = View.GONE
                        optionD?.visibility = View.GONE
                    }
                    // Ensure it's always visible (if you have previously set it to gone somewhere)
                    it.visibility = View.VISIBLE

                    // Update the content or appearance based on the clicked item
                    // For example, changing the text of a TextView within the ConstraintLayout


                    // Update any other views within the ConstraintLayout as needed
                    // This could include setting image resources, background colors, etc., based on the clicked item

                    Log.d(
                        "AdapterOnClick",
                        "Updated content for question: $question, questionId: $questionId"
                    )
                }
            }
        }

        // Optionally, store the last clicked positions if you need to track them for any reason
        lastClickedGroupPosition = groupPosition
        lastClickedChildPosition = childPosition
        // Perform any additional actions here, e.g., showing a dialog, navigating, etc.
    }






    // When setting up your adapter, pass 'this' as the listener




    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllQuestionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
data class Quartet<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

interface ChildItemClickListener {
    fun onChildItemClick(groupPosition: Int, childPosition: Int, childId: Long)
}
