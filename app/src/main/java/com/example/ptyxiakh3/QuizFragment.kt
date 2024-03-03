package com.example.ptyxiakh3

import QuestionsViewModel
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

            val popupTextView = activity?.findViewById<TextView>(R.id.popuptextview)
            Log.d("popup","nextBtn1")

            val constraintLayout = activity?.findViewById<ConstraintLayout>(R.id.popup)
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
                                popupTextView?.background = drawable
                                popupTextView?.text = text
                            }else{
                                val drawable = context?.let { it1 -> ContextCompat.getDrawable(it1, R.drawable.falsetextview) }
                                popupTextView?.background = drawable
                                popupTextView?.text = text
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

