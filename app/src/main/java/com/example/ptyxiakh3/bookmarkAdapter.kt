package com.example.ptyxiakh3

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ptyxiakh3.data.Question

class BookmarkAdapter(
    private val context: Context,
    private var questions: MutableList<Question>,  // Updated to 'questions' for clarity
    private val navController: NavController  // Assuming use in future
) :  RecyclerView.Adapter<BookmarkAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_bookmark, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val question = questions[position]

        // Log to check the binding process
        Log.d("BookmarkViewHolder", "Binding bookmark: ${question.question_id}")
        holder.quesNo.text ="Question's ID: "+ question.question_id.toString()

        // Binding the data to the views
        if(question.style=="SouLou") {

            holder.questionText.text = question.possibleAnswers[0]
            holder.text.text = question.question_text


            if(question.correctAnswers[0].toInt() ==1){
                holder.result.text = "True"
            }else{
                holder.result.text = "False"
            }
            holder.optionA.visibility = View.GONE
            holder.optionB.visibility = View.GONE
            holder.optionC.visibility = View.GONE
            holder.optionD.visibility = View.GONE
        }

        if (question.style == "Kena") {
            holder.optionA.visibility = View.GONE
            holder.optionB.visibility = View.GONE
            holder.optionC.visibility = View.GONE
            holder.optionD.visibility = View.GONE
            holder.result.visibility = View.GONE
            val sequence = question.correctAnswers.first().toString()
            var updatedQuestionText = question.question_text
            holder.questionText.text = question.question_text2
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
                val colorSpan = ForegroundColorSpan(Color.RED) // Change Color.RED to your desired color
                spannableString.setSpan(colorSpan, range.first, range.last, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            }

            // Set the spannable string to your TextView
            holder.text.text = spannableString
        }

        if (question.style == "Mistakes") {
            holder.optionA.visibility = View.GONE
            holder.optionB.visibility = View.GONE
            holder.optionC.visibility = View.GONE
            holder.optionD.visibility = View.GONE
            holder.result.visibility = View.GONE
            holder.questionText.text = question.question_text2
            // Create a SpannableString from the question text
            val spannableString = SpannableString(question.question_text)

            question.possibleAnswers.forEach { answer ->
                var startIndex = spannableString.indexOf(answer, ignoreCase = true)

                while (startIndex >= 0) {
                    // Calculate the end index of the answer
                    val endIndex = startIndex + answer.length

                    // Create a span to change the color of the answer
                    val colorSpan = ForegroundColorSpan(Color.RED) // Change Color.RED to your desired color

                    // Apply the color span on the spannable string
                    spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

                    // Find the next occurrence of the answer
                    startIndex = spannableString.indexOf(answer, startIndex + 1, ignoreCase = true)
                }
            }

            // Set the spannable string to your TextView
            holder.text.text = spannableString
        }

        if (question.style == "multiple choice") {
            holder.questionText.text = question.question_text
            holder.text.visibility = View.GONE
            holder.result.visibility = View.GONE

            // Iterate over all possible answers
            question.possibleAnswers.forEachIndexed { index, option ->
                // Determine the TextView for the current option (e.g., optionA, optionB, etc.)
                val optionTextView = when (index) {
                    0 -> holder.optionA
                    1 -> holder.optionB
                    2 -> holder.optionC
                    3 -> holder.optionD
                    else -> null // Add more cases if you have more options
                }

                // Set the text for the option
                optionTextView?.text = option

                // Check if the current option is a correct answer
                if (question.correctAnswers.contains(index.toLong())) { // Assuming correctAnswers are 1-indexed
                    // Set the text color to green for correct answers
                    optionTextView?.setTextColor(Color.GREEN)
                } else {
                    // Optionally, set the text color to default for incorrect answers
                    optionTextView?.setTextColor(Color.BLACK) // Use your default color
                }
            }
        }

        if(question.style=="Queue") {

            holder.questionText.text = question.question_text2
            holder.text.text = question.question_text

            holder.result.visibility = View.GONE

            holder.optionA.visibility = View.GONE
            holder.optionB.visibility = View.GONE
            holder.optionC.visibility = View.GONE
            holder.optionD.visibility = View.GONE
        }





    }

    override fun getItemCount(): Int {
        Log.d("BookmarkViewHolder", "Binding bookmark: ${questions.size}")
        return questions.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quesNo: TextView = itemView.findViewById(R.id.quesNo)
        val questionText: TextView = itemView.findViewById(R.id.question)
        val text: TextView = itemView.findViewById(R.id.text)
        val optionA: TextView = itemView.findViewById(R.id.optionA)
        val optionB: TextView = itemView.findViewById(R.id.optionB)
        val optionC: TextView = itemView.findViewById(R.id.optionC)
        val optionD: TextView = itemView.findViewById(R.id.optionD)
        val result: TextView = itemView.findViewById(R.id.result)
    }
}
