package com.example.ptyxiakh3

import QuestionsViewModel
import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.ptyxiakh3.DbQuery.myProfile
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ptyxiakh3.data.Question
import com.example.ptyxiakh3.databinding.FragmentQuizBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class QuizFragment : Fragment()  {
    private var binding: FragmentQuizBinding? = null
    private var myRecyclerView: RecyclerView? = null

    // ViewModel initialization using the 'by viewModels()' Kotlin property delegate.
    private val questionsViewModel: QuestionsViewModel by viewModels()

    private lateinit var quizAdapter: quizAdapter

    val args: QuizFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Using View Binding to inflate the layout
        binding = FragmentQuizBinding.inflate(inflater, container, false)

        val myRecyclerView = activity?.findViewById<RecyclerView>(R.id.quizRecycler)

        // Assuming you have the ProgressBar with id R.id.progressBar4




        return binding?.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        myRecyclerView = binding?.quizRecycler


        val sourceFragment = args.fragment



        myRecyclerView?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar4)
        Log.d("edo1","${progressBar.progress}")
        progressBar.progress = 0
        progressBar.postInvalidate()



        //edo gia to horizontal
        val layoutManager = NonScrollableLinearLayoutManager(requireContext())
        myRecyclerView?.layoutManager = layoutManager



        // gia na einai 1-1 ta object sot recycler
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(myRecyclerView)

        var totalQuestions = 0
        var progressbarprogress = 0
        // Observe LiveData from ViewModel
        var questionsLiveData= MutableLiveData<List<Question>>()
        val myPosition2 = args.chapter
        val myQuizNumber2 = args.quiz
        val quiz2 = (myQuizNumber2+myPosition2*0.1).toString()


        if (sourceFragment == "History") {
            val myPosition = args.chapter
            val myQuizNumber = args.quiz
            val quiz = myQuizNumber+myPosition*0.1
            val questionsLiveDataFromViewModel = questionsViewModel.getQuestionsByQuizNumber(quiz)

            // Observe LiveData from ViewModel and post its value to your MutableLiveData
            questionsLiveDataFromViewModel.observe(viewLifecycleOwner, Observer { questions ->
                questionsLiveData.value = questions
            })


        } else if (sourceFragment == "Module") {
            val Module = args.chapter
            val Dif = args.quiz
            if(Dif==0){

                var questionsdata: LiveData<List<Question>>? = null



                if (Module == 1) {
                    questionsdata = questionsViewModel.getQuestionsByModule("if")
                } else if (Module == 2){
                    questionsdata = questionsViewModel.getQuestionsByModule("for")
                }
                val questionsWithOnlyN = mutableListOf<Question>()
                val questionsWithOtherCharacters = mutableListOf<Question>()

                var processedQuestions = 0

                questionsdata?.observe(viewLifecycleOwner, Observer { questions ->
                    // Your processing logic here
                })


                if (questionsdata != null) {
                    questionsdata.observe(viewLifecycleOwner, Observer { questions ->
                        val totalQuestions = questions.size
                        questions.forEach { question ->
                            // Calculate the start index for the current question ID
                            val startIndex = ((question.question_id - 1) * 5).toInt()
                            // Extract the corresponding 5-character string
                            Log.d("DataQuiz", "Question ID ${myProfile.qHistory} ")
                            val historySegment = myProfile.qHistory.substring(startIndex, startIndex + 5)
                            // Check if the extracted string contains only 'N's
                            processedQuestions++
                            if (historySegment.all { it == 'N' }) {
                                Log.d("DataQuiz", "Question ID ${question.question_id} has only NNNNN in history")
                                questionsWithOnlyN.add(question)
                            } else {
                                Log.d("DataQuiz", "Question ID ${question.question_id} has different characters in history")
                                questionsWithOtherCharacters.add(question)
                            }
                        }


                        // Check if all questions are processed
                        Log.d("DataQuiz", "Question ID: ${processedQuestions} + ${totalQuestions}")
                        if (processedQuestions == totalQuestions) {
                            Log.d("DataQuiz", "Question ID: mphka33")
                            // All questions processed, now check the size
                            val randomQuestions: List<Question> = if (questionsWithOnlyN.size >= 10) {
                                questionsWithOnlyN.shuffled().take(10)
                            } else {
                                questionsWithOnlyN.shuffled()
                            }

                            // Update LiveData
                            questionsLiveData.value = randomQuestions
                        }


                    })
                }


                Log.d("DataQuiz", "Question ID: ${myProfile.qHistory}")
            }else{
                var questionsdata: LiveData<List<Question>>? = null

                if (Module == 1) {
                    questionsdata = questionsViewModel.getQuestionsByModuleAndDifficulty("if",Dif)
                } else if (Module == 2){
                    questionsdata = questionsViewModel.getQuestionsByModuleAndDifficulty("for",Dif)
                }
                val questionsWithOnlyN = mutableListOf<Question>()
                val questionsWithOtherCharacters = mutableListOf<Question>()

                var processedQuestions = 0

                questionsdata?.observe(viewLifecycleOwner, Observer { questions ->
                    // Your processing logic here
                })


                if (questionsdata != null) {

                    questionsdata.observe(viewLifecycleOwner, Observer { questions ->
                        val totalQuestions = questions.size
                        val questionsWithOnlyN = mutableListOf<Question>()
                        val questionsWithOtherCharacters = mutableListOf<Question>()

                        questions.forEach { question ->
                            // Calculate the start index for the current question ID
                            val startIndex = ((question.question_id - 1) * 5).toInt()
                            // Extract the corresponding 5-character string
                            val historySegment = myProfile.qHistory.substring(startIndex, startIndex + 5)
                            // Check if the extracted string contains only 'N's
                            if (historySegment.all { it == 'N' }) {
                                Log.d("DataQuiz", "Question ID ${question.question_id} has only NNNNN in history")
                                questionsWithOnlyN.add(question)
                            } else {
                                Log.d("DataQuiz", "Question ID ${question.question_id} has different characters in history")
                                questionsWithOtherCharacters.add(question)
                            }
                        }

                        // Check if all questions are processed
                        if (questions.size == totalQuestions) {
                            Log.d("DataQuiz", "All questions processed")

                            // Shuffle both lists
                            questionsWithOnlyN.shuffle()
                            questionsWithOtherCharacters.shuffle()

                            // Create a combined list with a balanced number of questions from each category
                            val combinedList = mutableListOf<Question>()

// Add questions from both lists while handling potential empty lists
                            for (i in 0 until 2) {
                                if (i < questionsWithOnlyN.size) {
                                    combinedList.add(questionsWithOnlyN[i])
                                }
                                if (i < questionsWithOtherCharacters.size) {
                                    combinedList.add(questionsWithOtherCharacters[i])
                                }
                            }

// If there aren't enough questions in combinedList, fill up from either list without duplicating
                            val allQuestions = (questionsWithOnlyN + questionsWithOtherCharacters).distinct()
                            for (question in allQuestions) {
                                if (combinedList.size >= 4) break
                                if (!combinedList.contains(question)) {
                                    combinedList.add(question)
                                }
                            }


                            // Update LiveData
                            questionsLiveData.value = combinedList
                        }

                    })
                }



                Log.d("DataQuiz", "Question ID: ${myProfile.qHistory}")
            }


            //questionsLiveData = questionsViewModel.getQuestionsByQuizNumber(quiz) // Assuming quiz22 is a valid variable
        } else {

            throw IllegalStateException("Unknown source fragment")
        }





        questionsLiveData.observe(viewLifecycleOwner, Observer { questions ->

            // Here you can update your UI with the list of questions
            // For example, you could use a RecyclerView adapter to display the questions
            // If you don't have a RecyclerView yet, you will need to set it up along with an adapter
            // binding.recyclerView.adapter = YourAdapter().apply { submitList(questions) }
            Log.d("sxolia ", "1 "+totalQuestions.toString())
             totalQuestions = questions.size
            Log.d("sxolia ", "2 "+totalQuestions.toString())

             progressbarprogress = if (totalQuestions != 0) {
                100 / totalQuestions
            } else {
                0 // Handle the case where totalQuestions is zero
            }

            //edo ta stoixeia
            quizAdapter = quizAdapter(requireContext(), questions as ArrayList<Question>, findNavController()).apply {
                layoutChangeListener = {
                    changeFragmentLayout()
                }
            }
            myRecyclerView?.adapter = quizAdapter


            if (!questions.isNullOrEmpty()) {
               // Toast.makeText(context, "Number of questions for quiz $quiz: ${questions.size}", Toast.LENGTH_LONG).show()
                for (question in questions) {
                    Log.d("DataQuiz", "Question ID: ${question.question_id}")
                }
            } else {
                // If there are no questions, show a Toast message
              //  Toast.makeText(context, "No questions found for quiz $myQuizNumber", Toast.LENGTH_SHORT).show()
            }

            val bookmarkImageView = activity?.findViewById<ImageView>(R.id.qa_bookmarkB)
            val questionIdText = activity?.findViewById<TextView>(R.id.qa_catName)
            val layoutManager2 = myRecyclerView?.layoutManager as LinearLayoutManager
            val currentPos = layoutManager2.findFirstVisibleItemPosition()
            val question = quizAdapter.getQuestionAtPosition(currentPos+1)
            if (question != null) {
                Log.d("book13", "Question ID: ${question.question_id} pathsa")
                Log.d("book13", DbQuery.g_bmIdList.toString() +" edo")
                questionIdText?.text = question.style
                if (DbQuery.g_bmIdList.contains(question.question_id)) {
                    Log.d("book13", DbQuery.g_bmIdList.toString() +" edo")

                    if (bookmarkImageView != null) {
                        bookmarkImageView.setImageResource(R.drawable.ic_backround_selected)
                        bookmarkImageView.tag = "bookmarked"
                    }
                    Log.d("book13", "Question ID: ${question.question_id} gemato")
                }else{
                    if (bookmarkImageView != null) {
                        bookmarkImageView.setImageResource(R.drawable.ic_bookmark)
                        bookmarkImageView.tag = "not_bookmarked"
                    }
                    Log.d("book13", "Question ID: ${question.question_id} adeio")
                }
            }


        })


       // Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

        // Hide the bottom navigation view, if necessary
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView1)
        bottomNavigationView?.visibility = View.GONE

        Log.d("sxolia ", "3 "+totalQuestions.toString())



        val bookmarkImageView = activity?.findViewById<ImageView>(R.id.qa_bookmarkB)




        bookmarkImageView!!.setOnClickListener {
            val layoutManager = myRecyclerView?.layoutManager as LinearLayoutManager
            val currentPos = layoutManager.findFirstVisibleItemPosition()
            val question = quizAdapter.getQuestionAtPosition(currentPos)
            Log.d("book13", "Question ID: mphka")
            if (question != null) {
                Log.d("QuizFragment5", "Question ID: ${DbQuery.g_bmIdList} ")


            if (bookmarkImageView.tag == null || bookmarkImageView.tag == "not_bookmarked") {
                bookmarkImageView.setImageResource(R.drawable.ic_backround_selected)
                bookmarkImageView.tag = "bookmarked"
                Log.d("QuizFragment5", "bookmarked")
                 if (!DbQuery.g_bmIdList.contains(question.question_id)) {
                     DbQuery.g_bmIdList.add(question.question_id)
                     myProfile.bookmarksCount = DbQuery.g_bmIdList.size
                     Log.d("QuizFragment5", "Question ID: ${question.question_id} mphke")
                 }


            } else {
                bookmarkImageView.setImageResource(R.drawable.ic_bookmark)
                bookmarkImageView.tag = "not_bookmarked"
                Log.d("QuizFragment5", "not_bookmarked")

                if (DbQuery.g_bmIdList.contains(question.question_id)) {
                    DbQuery.g_bmIdList.remove(question.question_id)
                    myProfile.bookmarksCount = DbQuery.g_bmIdList.size
                    Log.d("QuizFragment5", "Question ID: ${question.question_id} bghke")

                }

            }
                Log.d("QuizFragment", "Question ID: ${question.question_id}")
                // Existing bookmark toggle logic...
            }
        }

           // if (!DbQuery.g_bmIdList.contains(question.qID)) {
           //     DbQuery.g_bmIdList.add(question.qID)
           //     DbQuery.myProfile.bookmarksCount = DbQuery.g_bmIdList.size
           // }




        binding?.btnNext?.setOnClickListener {

            //Log.d("popup","mphkapop")
            val currentPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
            val layoutManager = myRecyclerView?.layoutManager as LinearLayoutManager
            val currentPos = layoutManager.findFirstVisibleItemPosition()
            val question = quizAdapter.getQuestionAtPosition(currentPos)
            Log.d("1324", "Sou lou " + question?.question_id)

           // val popupTextView = activity?.findViewById<TextView>(R.id.popuptextview)


            val text = activity?.findViewById<TextView>(R.id.text)
            val optionA = activity?.findViewById<TextView>(R.id.optionA)
            val optionB = activity?.findViewById<TextView>(R.id.optionB)
            val optionC = activity?.findViewById<TextView>(R.id.optionC)
            val optionD = activity?.findViewById<TextView>(R.id.optionD)
            val result = activity?.findViewById<TextView>(R.id.result)
            val lasttext = activity?.findViewById<TextView>(R.id.lasttext)

            Log.d("popup","nextBtn1")
            val viewHolder = myRecyclerView?.findViewHolderForAdapterPosition(currentPosition)
            viewHolder?.let {
                var (flag, textFromAnswer) = quizAdapter.interactWithCurrentItem(it)




                if (question?.style == "SouLou") {
                    text?.visibility = View.GONE
                    lasttext?.visibility = View.GONE
                    text?.text = question.question_text
                    result?.visibility = View.VISIBLE

                    if (question.correctAnswers[0].toInt() == 1) {
                        result?.text = textFromAnswer
                    } else {
                        result?.text = textFromAnswer
                    }
                    optionA?.visibility = View.GONE
                    optionB?.visibility = View.GONE
                    optionC?.visibility = View.GONE
                    optionD?.visibility = View.GONE
                }

                if (question?.style == "Kena") {
                    lasttext?.visibility = View.GONE
                    text?.visibility = View.VISIBLE
                    optionA?.visibility = View.GONE
                    optionB?.visibility = View.GONE
                    optionC?.visibility = View.GONE
                    optionD?.visibility = View.GONE
                    result?.visibility = View.GONE
                    val sequence = question.correctAnswers.first().toString()
                    var updatedQuestionText = question.question_text
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
                        val textColor =
                            ContextCompat.getColor(requireContext(), R.color.muted_orange)

                        val colorSpan = ForegroundColorSpan(textColor)

                        val sizeSpan =
                            RelativeSizeSpan(1.3f) // Adjust the 1.5f to your desired size multiplier

                        spannableString.setSpan(
                            colorSpan,
                            range.first,
                            range.last,
                            Spanned.SPAN_INCLUSIVE_INCLUSIVE
                        )
                        spannableString.setSpan(
                            sizeSpan,
                            range.first,
                            range.last,
                            Spanned.SPAN_INCLUSIVE_INCLUSIVE
                        )

                    }


                    // Set the spannable string to your TextView
                    text?.text = spannableString
                }

                if (question?.style == "Mistakes") {
                    Log.d("apapapa", "edo2")
                    text?.visibility = View.VISIBLE
                    optionA?.visibility = View.GONE
                    optionB?.visibility = View.GONE
                    optionC?.visibility = View.GONE
                    optionD?.visibility = View.GONE
                    result?.visibility = View.GONE
                    lasttext?.visibility = View.VISIBLE
                    // Create a SpannableString from the question text
                    val spannableString = SpannableString(question.question_text)

                    question.possibleAnswers.forEach { answer ->
                        var startIndex = spannableString.indexOf(answer, ignoreCase = true)

                        while (startIndex >= 0) {
                            // Calculate the end index of the answer
                            val endIndex = startIndex + answer.length

                            // Create a span to change the color of the answer
                            val textColor =
                                ContextCompat.getColor(requireContext(), R.color.muted_orange)
                            val colorSpan = ForegroundColorSpan(textColor)

                            // Apply the color span on the spannable string
                            spannableString.setSpan(
                                colorSpan,
                                startIndex,
                                endIndex,
                                Spanned.SPAN_INCLUSIVE_INCLUSIVE
                            )

                            val sizeSpan =
                                RelativeSizeSpan(1.3f) // Adjust the 1.5f to your desired size multiplier

                            spannableString.setSpan(
                                sizeSpan,
                                startIndex,
                                endIndex,
                                Spanned.SPAN_INCLUSIVE_INCLUSIVE
                            )


                            // Find the next occurrence of the answer
                            startIndex =
                                spannableString.indexOf(answer, startIndex + 1, ignoreCase = true)
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
                        val textColor =
                            ContextCompat.getColor(requireContext(), R.color.muted_orange)
                        val colorSpan = ForegroundColorSpan(textColor)
                        spannableBuilder.setSpan(
                            colorSpan,
                            start,
                            end,
                            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                        )

                        // Apply size span
                        val sizeSpan = RelativeSizeSpan(1.3f)  // Adjust this value as needed
                        spannableBuilder.setSpan(
                            sizeSpan,
                            start,
                            end,
                            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                    }

// Set the SpannableStringBuilder to a TextView
                    lasttext?.text = spannableBuilder


                }

                if (question?.style == "multiple choice") {
                    lasttext?.visibility = View.GONE
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
                        optionTextView?.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.text_color
                            )
                        )
                        optionTextView?.textSize = 15f
                        // Check if the current option is a correct answer
                        if (question.correctAnswers.contains(index.toLong())) { // Assuming correctAnswers are 1-indexed
                            // Assuming 'this' or 'getActivity()' can be used to obtain a Context. Adjust accordingly.
                            optionTextView?.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.muted_orange
                                )
                            );

                            optionTextView?.textSize = 22f

                        }
                    }
                    Log.d("Mistakesall", "point2")

                }

                if (question?.style == "Queue") {
                    lasttext?.visibility = View.GONE
                    text?.visibility = View.VISIBLE
                    text?.text = question.question_text

                    result?.visibility = View.GONE

                    optionA?.visibility = View.GONE
                    optionB?.visibility = View.GONE
                    optionC?.visibility = View.GONE
                    optionD?.visibility = View.GONE
                }
            }
            // Ensure it's always visible (if you have previously set it to gone somewhere)
            it.visibility = View.VISIBLE

            // Update the content or appearance based on the clicked item
            // For example, changing the text of a TextView within the ConstraintLayout


            // Update any other views within the ConstraintLayout as needed
            // This could include setting image resources, background colors, etc., based on the clicked item

            Log.d(
                "AdapterOnClick",
                "Updated content for question: $question, questionId: $question.question_id"
            )







            val constraintLayout = activity?.findViewById<LinearLayout>(R.id.popup)
            constraintLayout?.let {
                if (it.visibility == View.VISIBLE) {
                    // Actions to perform when the ConstraintLayout is visible
                    it.visibility = View.GONE
                    // Add more actions here if needed, when making the layout GONE
                    val currentPos = layoutManager.findFirstVisibleItemPosition()
                    val question = quizAdapter.getQuestionAtPosition(currentPos+1)
                    val questionIdText = activity?.findViewById<TextView>(R.id.qa_catName)
                    if (question != null) {
                        questionIdText?.text = question.style
                        Log.d("book13", "Question ID: ${question.question_id} pathsa")
                        Log.d("book13", DbQuery.g_bmIdList.toString() +" edo")
                        if (DbQuery.g_bmIdList.contains(question.question_id)) {
                            Log.d("book13", DbQuery.g_bmIdList.toString() +" edo")
                            bookmarkImageView.setImageResource(R.drawable.ic_backround_selected)
                            bookmarkImageView.tag = "bookmarked"
                            Log.d("book13", "Question ID: ${question.question_id} gemato")
                        }else{
                            bookmarkImageView.setImageResource(R.drawable.ic_bookmark)
                            Log.d("book13", DbQuery.g_bmIdList.toString() +" edo")
                            bookmarkImageView.tag = "not_bookmarked"
                            Log.d("book13", "Question ID: ${question.question_id} adeio")
                        }
                    }



                    Log.d("popup","nextBtn")


                    if (currentPosition != RecyclerView.NO_POSITION) {
                        val nextPosition = currentPosition + 1
                        if (nextPosition < (myRecyclerView?.adapter?.itemCount ?: 0)) {
                            //EDO
                            myRecyclerView?.layoutManager?.scrollToPosition(nextPosition)
                            progressBar.progress += progressbarprogress
                            // Notify the adapter about the next position
                        } else {

                            progressBar.progress = 0
                            val result = myProfile.qHistory
                            Log.d("quizs","edo ,${myProfile.quizs}")
                            if (!myProfile.quizs.contains(quiz2)) {
                                myProfile.quizs.add(quiz2)
                            }
                            Log.d("quizs","edo2 ,${myProfile.quizs}")


                            DbQuery.updateQHistory(   result, object :
                                MyCompleteListener {
                                override fun onSuccess() {

                                }

                                override fun onFailure() {

                                }
                            })
                            DbQuery.updateQScore(   10, object :
                                MyCompleteListener {
                                override fun onSuccess() {

                                }

                                override fun onFailure() {

                                }
                            })
                            DbQuery.saveBookmarks(   object :
                                MyCompleteListener {
                                override fun onSuccess() {

                                }

                                override fun onFailure() {

                                }
                            })
                            DbQuery.updateQuizs(   myProfile.quizs, object :
                                MyCompleteListener {
                                override fun onSuccess() {

                                }

                                override fun onFailure() {

                                }
                            })

                            findNavController().navigateUp()

                        }
                    }

                } else {
                    Log.d("popup","nextBtn2")
                    // Actions to perform when the ConstraintLayout is not visible (i.e., GONE or INVISIBLE)
                    it.visibility = View.VISIBLE
                    if (::quizAdapter.isInitialized) {
                        val viewHolder = myRecyclerView?.findViewHolderForAdapterPosition(currentPosition)

                        viewHolder?.let {
                            var (flag, text) = quizAdapter.interactWithCurrentItem(it)
                            if (it is quizAdapter.ViewHolderTypeOne) {
                                // it is an instance of ViewHolderTypeOne
                                Log.d("flagg", "flag = ${flag} + text = ${text}, it ${it}")
                            } else if (it is quizAdapter.ViewHolderTypeTwo){
                                text=flag.toString()
                            } else if (it is quizAdapter.ViewHolderTypeThree){

                            }

                            Log.d("flagg", "flag = ${flag}")
                            if(flag){
                                val drawable = context?.let { it1 -> ContextCompat.getDrawable(it1, R.drawable.shadows2) }
                             //   popupTextView?.background = drawable
                             //   popupTextView?.text = text
                            }else{
                                val drawable = context?.let { it1 -> ContextCompat.getDrawable(it1, R.drawable.falsetextview) }
                             //   popupTextView?.background = drawable
                             //   popupTextView?.text = text
                            }

                        }


                    }


                    // Add more actions here if needed, when making the layout VISIBLE
                }
            }



            /*
            val currentPosition = layoutManager.findFirstCompletelyVisibleItemPosition()

            if (::quizAdapter.isInitialized) {
                val viewHolder = myRecyclerView?.findViewHolderForAdapterPosition(currentPosition)
                viewHolder?.let {
                    quizAdapter.interactWithCurrentItem(it)
                }
                Log.d("QuizFragment", "Adapter is initialized")
            } else {
                // Handle the case where quizAdapter is not yet initialized
                Log.e("QuizFragment", "Adapter is not initialized")
            }

            if (currentPosition != RecyclerView.NO_POSITION) {
                val nextPosition = currentPosition + 1
                if (nextPosition < (myRecyclerView?.adapter?.itemCount ?: 0)) {
                    //EDO
                    myRecyclerView?.layoutManager?.scrollToPosition(nextPosition)
                    progressBar.progress += progressbarprogress
                     // Notify the adapter about the next position
                } else {

                    progressBar.progress = 0
                    val result = myProfile.qHistory
                    DbQuery.updateQHistory(   result, object :
                        MyCompleteListener {
                        override fun onSuccess() {

                        }

                        override fun onFailure() {

                        }
                    })
                    findNavController().navigateUp()

                }
            }

             */
        }

        if (::quizAdapter.isInitialized) {
            // Safe to use quizAdapter here

             }



    }


    private fun changeFragmentLayout() {
        val bookmarkImageView = activity?.findViewById<ImageView>(R.id.qa_bookmarkB)
        val layoutManager = NonScrollableLinearLayoutManager(requireContext())
        val currentPos = layoutManager.findFirstVisibleItemPosition()
        val question = quizAdapter.getQuestionAtPosition(currentPos)
        if (question != null) {
            if (DbQuery.g_bmIdList.contains(question.question_id)) {
                bookmarkImageView!!.setImageResource(R.drawable.ic_backround_selected)
                bookmarkImageView.tag = "bookmarked"
            }else{
                bookmarkImageView!!.setImageResource(R.drawable.ic_bookmark)
                bookmarkImageView.tag = "not_bookmarked"
            }
        }
        Log.d("axxa","mphka")
    }

    fun handleTypeOneAction(position: Int) {
            // Implement action for Type One
        }

        fun handleTypeTwoAction(position: Int) {
            // Implement action for Type Two
        }

    override fun onDestroyView() {
        super.onDestroyView()
        // Avoid memory leaks by setting the binding to null
        binding = null
    }
    override fun onPause() {
        super.onPause()
        val progressBar = view?.findViewById<ProgressBar>(R.id.progressBar4)
        if (progressBar != null) {
            Log.d("edo1","${progressBar.progress}")
        }
        if (progressBar != null) {
            progressBar.progress = 0
        }
    }









    // Rest of your companion object and other methods, if necessary
}


class NonScrollableLinearLayoutManager(context: Context) : LinearLayoutManager(context, HORIZONTAL, false) {
    override fun canScrollHorizontally(): Boolean {
        return false // This will disable horizontal scrolling
    }
}

