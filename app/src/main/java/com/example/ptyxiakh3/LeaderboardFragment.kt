package com.example.ptyxiakh3

import QuestionsViewModel
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.room.InvalidationTracker
import com.example.ptyxiakh3.data.Question
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.yourpackage.NetworkUtil
import java.util.Arrays

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
οιπόν έχουμε 3 ποσοστά στην αρχή συνολικό ποσοστό ΟΛΑ για τουλάχιστον μία σωστή απάντηση
 απο κάτω το αριθμό πλήθους που έχει απαντήσει

 χειρότερη κατηγορία
 ποσοστό όλων των απαντήσεων

αντι για κουμπια να έχω διακόπτες

 μπαρα 3-4 χρωμάτων με το ποσοστό δυσκολίας των ερωτήσεων
 */

class LeaderboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val questionsViewModel: QuestionsViewModel by viewModels()

    val selectedModules = mutableListOf(
        "Πανελλήνιες", "Κεφάλαιο1","Θεωρία"
    )

    val selectedStyles = mutableListOf("SouLou", "Kena", "Mistakes", "multiple choice", "Queue", "Fill")
    private var currentButtonState = ButtonState.CIRCLE
    private var currentButtonState2 = ButtonState.CIRCLE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        if (!NetworkUtil.isInternetAvailable(requireContext())) {
            showNoInternetDialog()
        }


    }

    override fun onResume() {
        super.onResume()
        if (!NetworkUtil.isInternetAvailable(requireContext())) {
            showNoInternetDialog()
        }
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("No Internet Connection")
            .setMessage("This app requires an internet connection. Please check your network settings.")
            .setPositiveButton("OK") { _, _ -> requireActivity().finish() }
            .setCancelable(false)
            .show()
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        // Only inflate the layout once
        val view = inflater.inflate(R.layout.fragment_leaderboard, container, false)

        var mAdView : AdView?= view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()

        // Start loading the ad in the background.
        mAdView?.loadAd(adRequest)

        // Initialize progress bars
        val bar01 : ProgressBar? = view.findViewById(R.id.vertical_progressbar01)
        val bar02 : ProgressBar? = view.findViewById(R.id.vertical_progressbar02)
        val bar03 : ProgressBar? = view.findViewById(R.id.vertical_progressbar03)
        val bar04 : ProgressBar? = view.findViewById(R.id.vertical_progressbar04)
        val bar05 : ProgressBar? = view.findViewById(R.id.vertical_progressbar05)
        val bar06 : ProgressBar? = view.findViewById(R.id.vertical_progressbar06)
        val bar07 : ProgressBar? = view.findViewById(R.id.vertical_progressbar07)

        val bar7 : ProgressBar? = view.findViewById(R.id.vertical_progressbar6)
        val bar8 : ProgressBar? = view.findViewById(R.id.vertical_progressbar7)
        val bar9 : ProgressBar? = view.findViewById(R.id.vertical_progressbar8)
        val bar10 : ProgressBar? = view.findViewById(R.id.vertical_progressbar9)
        val bar11 : ProgressBar? = view.findViewById(R.id.vertical_progressbar10)
        val bar12 : ProgressBar? = view.findViewById(R.id.vertical_progressbar11)
        val bar13 : ProgressBar? = view.findViewById(R.id.vertical_progressbar12)
        val bar14 : ProgressBar? = view.findViewById(R.id.vertical_progressbar13)
        val bar15 : ProgressBar? = view.findViewById(R.id.vertical_progressbar14)

        val barLeft : ProgressBar? = view?.findViewById(R.id.progressBarLeft)
        val barRight : ProgressBar? = view?.findViewById(R.id.progressBarRight)

        val textLeft : TextView? = view?.findViewById(R.id.textLeft)
        val textRight : TextView? = view?.findViewById(R.id.textRight)



        questionsViewModel.readAllData.observe(viewLifecycleOwner, Observer { questions ->


            var rightProgress = handleQuestions(
                questions = questions,
                userAnswers = DbQuery.myProfile.qHistory,
                bars =  listOf(bar01, bar02, bar03, bar04, bar05 ,bar06 ,bar07),
                countingMethod = CountingMethod.USED_COUNTS,
                selectedCategories = selectedModules, // These are modules
                groupBy = "style", // Group results by style
                filterBy = "module",
                difficultyFilter = listOf(1,2,3)  // Filter questions by module
            )

            barRight?.progress = rightProgress
            textRight?.text = rightProgress.toString() + "%"

            var leftProgress = handleQuestions(
                questions = questions,
                userAnswers = DbQuery.myProfile.qHistory,
                bars =  listOf(bar01, bar02, bar03, bar04, bar05 ,bar06 ,bar07),
                countingMethod = CountingMethod.TOTAL_COUNTS,
                selectedCategories = selectedModules, // These are modules
                groupBy = "style", // Group results by style
                filterBy = "module",
                difficultyFilter = listOf(1,2,3)  // Filter questions by module
            )

            barLeft?.progress = leftProgress
            textLeft?.text = leftProgress.toString() + "%"


            handleQuestions(
                questions = questions,
                userAnswers = DbQuery.myProfile.qHistory,
                bars =  listOf(bar7, bar8, bar9, bar10, bar11 ,bar12,bar13 ,bar14 ,bar15),
                countingMethod = CountingMethod.TOTAL_COUNTS,
                selectedCategories = listOf("SouLou", "Kena", "Mistakes", "multiple choice", "Queue", "Fill"), // These are modules
                groupBy = "module", // Group results by style
                filterBy = "style" // Filter questions by module
            )



        })


        val Popup = view.findViewById<LinearLayout>(R.id.ModulepopupLayout)
        val infoBtn = view.findViewById<ImageView>(R.id.info)
        val closeBtn = view.findViewById<ImageView>(R.id.closeBtnn)

        closeBtn.setOnClickListener{
            Popup?.visibility=View.GONE
        }

        infoBtn.setOnClickListener{
            Popup?.visibility=View.VISIBLE
        }



            return view
    }


    private fun handleQuestions(
        questions: List<Question>,
        userAnswers: String,
        bars: List<ProgressBar?>,
        countingMethod: CountingMethod,
        selectedCategories: List<String>,
        groupBy: String,
        filterBy: String,
        difficultyFilter: List<Int>? = null  // Now accepts a list of difficulties for filtering
    ): Int {
        val difficultyCounts = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
        val categoryGroups = when (groupBy) {
            "module" -> questions.flatMap { it.modules }.toSet()
            "style" -> questions.map { it.style }.toSet()
            "difficulty" -> questions.map { it.difficulty.toString() }.toSet()
            else -> throw IllegalArgumentException("Group by must be 'module', 'style', or 'difficulty'")
        }

        val filterGroups = when (filterBy) {
            "module" -> questions.flatMap { it.modules }.toSet()
            "style" -> questions.map { it.style }.toSet()
            "difficulty" -> questions.map { it.difficulty.toString() }.toSet()
            else -> throw IllegalArgumentException("Filter by must be 'module', 'style', or 'difficulty'")
        }

        val categoryCorrectCounts = mutableMapOf<String, Int>().withDefault { 0 }
        val categoryCounts = mutableMapOf<String, Int>().withDefault { 0 }
        var totalAnsweredQuestions =0
        var overallCorrect = 0
        var overallCount = 0
        Log.d("LeaderBoard", "groupBy: $groupBy, filterBy: $filterBy")
        questions.forEach { question ->
            // Apply difficulty filter if provided
            if (difficultyFilter == null || difficultyFilter.contains(question.difficulty)) {
                Log.d("LeaderBoard", " Question, ${question.question_id}")

                val categories = when (groupBy) {
                    "module" -> question.modules
                    "style" -> listOf(question.style)
                    "difficulty" -> listOf(question.difficulty.toString())
                    else -> listOf()
                }
                val filters = when (filterBy) {
                    "module" -> question.modules
                    "style" -> listOf(question.style)
                    "difficulty" -> listOf(question.difficulty.toString())
                    else -> listOf()
                }
                Log.d("LeaderBoard", " Mphka1")
                val startIndex = questions.indexOf(question) * 5
                val answers = if (startIndex + 5 <= userAnswers.length) {
                    userAnswers.substring(startIndex, startIndex + 5)
                } else {
                    userAnswers.substring(startIndex) + "N".repeat(5 - (userAnswers.length - startIndex))
                }
                Log.d("LeaderBoard", " userAnswers $answers")
                val hasAnswers = answers.any { it == 'T' || it == 'F' }



                if (filters.any { it in selectedCategories }) {
                    Log.d("LeaderBoard", " Mphka2")
                    if (hasAnswers) {

                        totalAnsweredQuestions++
                        difficultyCounts[question.difficulty] = difficultyCounts.getOrDefault(question.difficulty, 0) + 1
                    }

                    categories.forEach { category ->
                        Log.d("LeaderBoard", " Mphka3")


                        if (hasAnswers) {
                            Log.d("LeaderBoard", " Mphka4")
                            when (countingMethod) {
                                CountingMethod.TOTAL_COUNTS -> {
                                    val countT = answers.count { it == 'T' }
                                    val countTF = answers.count { it == 'T' || it == 'F' }
                                    categoryCounts[category] = categoryCounts.getValue(category) + countTF
                                    categoryCorrectCounts[category] = categoryCorrectCounts.getValue(category) + countT
                                    Log.d("LeaderBoard", " Question, ${question.question_id} category: $category, categoryCounts[category]: ${categoryCounts[category]}")
                                    overallCount += countTF
                                    overallCorrect += countT
                                }
                                CountingMethod.USED_COUNTS -> {
                                    categoryCounts[category] = categoryCounts.getValue(category) + 1
                                    overallCount += 1
                                    if (answers.any { it == 'T' }) {
                                        categoryCorrectCounts[category] = categoryCorrectCounts.getValue(category) + 1
                                        Log.d("LeaderBoard", " Question, ${question.question_id} category: $category, categoryCounts[category]: ${categoryCounts[category]}")
                                        overallCorrect += 1
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        val overallCorrectness = if (overallCount > 0) 100 * overallCorrect / overallCount else 0
        var minCorrectness = Int.MAX_VALUE // Initialize with the maximum possible integer value

        activity?.runOnUiThread {
            var minCorrectness = Int.MAX_VALUE // Make sure it's initialized to a very high value initially.
            var minCorrectnessCategory = "" // This will hold the category name with the lowest correctness.

            categoryGroups.forEachIndexed { index, category ->
                val correct = categoryCorrectCounts.getValue(category)
                val count = categoryCounts.getValue(category)
                val categoryCorrectness = if (count > 0) 100 * correct / count else -1

                // Update the minimum correctness and the corresponding category name
                if (categoryCorrectness < minCorrectness && categoryCorrectness != -1) {
                    minCorrectness = categoryCorrectness
                    minCorrectnessCategory = category // Update the category name here
                }

                if (index < bars.size) {
                    bars[index]?.progress = categoryCorrectness
                    Log.d("LeaderBoard", "index: $index, categoryCorrectness: $categoryCorrectness, category: $category")
                }

                Log.d("LeaderBoard", "Category: $category, Correct: $correct, Count: $count")
                Log.d("LeaderBoard", "Category: $category, Correctness: $categoryCorrectness%")
            }
            // Ensure there is a last bar to update with overall correctness
            bars.lastOrNull()?.progress = overallCorrectness


            // Log or use the minimum correctness
            Log.d("LeaderBoard", "Minimum Correctness: $minCorrectness% of $minCorrectnessCategory")
            // Update a UI component with the minimum correctness if needed
            // For example, setting a progress bar to show the minimum correctness
            val minCorrectnessBar : ProgressBar? = view?.findViewById(R.id.progressBarMiddle)
            val minText : TextView? = view?.findViewById(R.id.textcenter)
            val minTextBar : TextView? = view?.findViewById(R.id.centerBarText)
            if(minCorrectness < 1000) {
                minTextBar?.text = minCorrectness.toString() + "%"
                minCorrectnessBar?.progress = minCorrectness
            }

            minText?.text = minCorrectnessCategory
        }

        val difficultyPercentages = difficultyCounts.mapValues { (difficulty, count) ->
            if (totalAnsweredQuestions > 0) {
                (count.toDouble() / totalAnsweredQuestions) * 100
            } else {
                0.0
            }
        }
        Log.d("diffd","Difficulty 1 count: ${difficultyCounts[1]}, percentage: ${"%.2f".format(difficultyPercentages[1])}%")
        Log.d("diffd","Difficulty 2 count: ${difficultyCounts[2]}, percentage: ${"%.2f".format(difficultyPercentages[2])}%")
        Log.d("diffd","Difficulty 3 count: ${difficultyCounts[3]}, percentage: ${"%.2f".format(difficultyPercentages[3])}%")

        val progressBar123 : ProgressBar? = view?.findViewById(R.id.progressBar123)
        progressBar123?.progress = difficultyPercentages[1]?.toInt()!!
        progressBar123?.secondaryProgress = difficultyPercentages[1]?.toInt()!! + difficultyPercentages[2]?.toInt()!!
        return overallCorrectness;
    }




    enum class CountingMethod {
        TOTAL_COUNTS, // Use total counts of answers (either T or F)
        USED_COUNTS // Use counts of questions where an answer was provided (used at least once)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val myButton : ImageView = view.findViewById(R.id.chscharacter)
        val myButton22 : ImageView = view.findViewById(R.id.chscharacter2)
        val myButton2 : Button = view.findViewById(R.id.myButton2)
        val myButton23 : Button = view.findViewById(R.id.myButton23)
        val closeBtn : Button = view.findViewById(R.id.closeBtn)
        val closeBtn2 : Button = view.findViewById(R.id.closeBtn2)
        val popupLayout  : LinearLayout = view.findViewById(R.id.popupLayout )
        val popupLayout2  : LinearLayout = view.findViewById(R.id.popupLayout2 )
        val anaAskhkhLayout : LinearLayout = view.findViewById(R.id.firstStat )
        val anaChapterLayout : LinearLayout = view.findViewById(R.id.secondStat )

        val Textallagh : TextView = view.findViewById(R.id.anaTiText)
        val Textallagh2 : TextView = view.findViewById(R.id.anaTiText2)

        val bar01 : ProgressBar? = view.findViewById(R.id.vertical_progressbar01)
        val bar02 : ProgressBar? = view.findViewById(R.id.vertical_progressbar02)
        val bar03 : ProgressBar? = view.findViewById(R.id.vertical_progressbar03)
        val bar04 : ProgressBar? = view.findViewById(R.id.vertical_progressbar04)
        val bar05 : ProgressBar? = view.findViewById(R.id.vertical_progressbar05)
        val bar06 : ProgressBar? = view.findViewById(R.id.vertical_progressbar06)
        val bar07 : ProgressBar? = view.findViewById(R.id.vertical_progressbar07)


        val bar7 : ProgressBar? = view.findViewById(R.id.vertical_progressbar6)
        val bar8 : ProgressBar? = view.findViewById(R.id.vertical_progressbar7)
        val bar9 : ProgressBar? = view.findViewById(R.id.vertical_progressbar8)
        val bar10 : ProgressBar? = view.findViewById(R.id.vertical_progressbar9)
        val bar11 : ProgressBar? = view.findViewById(R.id.vertical_progressbar10)
        val bar12 : ProgressBar? = view.findViewById(R.id.vertical_progressbar11)
        val bar13 : ProgressBar? = view.findViewById(R.id.vertical_progressbar12)
        val bar14 : ProgressBar? = view.findViewById(R.id.vertical_progressbar13)
        val bar15 : ProgressBar? = view.findViewById(R.id.vertical_progressbar14)

        val barLeft : ProgressBar? = view.findViewById(R.id.progressBarLeft)

        Log.d("LeaderboardFragment", "onViewCreated called")

        val checkBox1 : CheckBox? = view.findViewById(R.id.checkBox1)
        val checkBox2 : CheckBox? = view.findViewById(R.id.checkBox2)
        val checkBox3 : CheckBox? = view.findViewById(R.id.checkBox3)
        val checkBox4 : CheckBox? = view.findViewById(R.id.checkBox4)
        val checkBox5 : CheckBox? = view.findViewById(R.id.checkBox5)

        val checkBox21 : CheckBox? = view.findViewById(R.id.checkBox21)
        val checkBox22 : CheckBox? = view.findViewById(R.id.checkBox22)
        val checkBox23 : CheckBox? = view.findViewById(R.id.checkBox23)
        val checkBox24 : CheckBox? = view.findViewById(R.id.checkBox24)
        val checkBox25 : CheckBox? = view.findViewById(R.id.checkBox25)


        view.postDelayed({
            listOf(checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox21, checkBox22, checkBox23, checkBox24, checkBox25).forEach {
                it?.isChecked = true
            }
        }, 100)  // Delay by 100 milliseconds




        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        progressBar?.max = 100
        progressBar?.secondaryProgress = 50
        progressBar?.progress = 25





        myButton.setOnClickListener {
            // Toggle visibility
            popupLayout.visibility = if (popupLayout.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        myButton22.setOnClickListener {
            // Toggle visibility
            popupLayout2.visibility = if (popupLayout2.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        Textallagh.setOnClickListener{
            anaAskhkhLayout.visibility = if (anaAskhkhLayout.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            anaChapterLayout.visibility = if (anaChapterLayout.visibility == View.GONE) View.VISIBLE else View.GONE
            questionsViewModel.readAllData.observe(viewLifecycleOwner, Observer { questions ->
                handleQuestions(
                    questions = questions,
                    userAnswers = DbQuery.myProfile.qHistory,
                    listOf(),
                    countingMethod = CountingMethod.TOTAL_COUNTS,
                    selectedCategories = selectedStyles, // These are modules
                    groupBy = "module", // Group results by style
                    filterBy = "style" // Filter questions by module
                )
            })
        }

        Textallagh2.setOnClickListener{
            anaChapterLayout.visibility = if (anaChapterLayout.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            anaAskhkhLayout.visibility = if (anaAskhkhLayout.visibility == View.GONE) View.VISIBLE else View.GONE
            questionsViewModel.readAllData.observe(viewLifecycleOwner, Observer { questions ->
                handleQuestions(
                    questions = questions,
                    userAnswers = DbQuery.myProfile.qHistory,
                    bars = listOf(),
                    countingMethod = CountingMethod.TOTAL_COUNTS,
                    selectedCategories = selectedModules, // These are modules
                    groupBy = "style", // Group results by style
                    filterBy = "module" // Filter questions by module
                )
            })
        }

        closeBtn.setOnClickListener {
            // Toggle visibility
            selectedModules.clear()

            if (checkBox1?.isChecked == true) selectedModules.add("Chapter1")
            if (checkBox2?.isChecked == true) selectedModules.add("Chapter2")
            if (checkBox3?.isChecked == true) selectedModules.add("Chapter3")
            if (checkBox4?.isChecked == true) selectedModules.add("For")
            if (checkBox5?.isChecked == true) selectedModules.add("If")


            Log.d("statisticcheck", "exoume $selectedModules ")
            popupLayout.visibility = if (popupLayout.visibility == View.VISIBLE) View.GONE else View.VISIBLE

            questionsViewModel.readAllData.observe(viewLifecycleOwner, Observer { questions ->



                when (currentButtonState) {
                    ButtonState.CIRCLE -> {
                        Log.d("statisticcheck", "exoeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeume Circle ")


                        handleQuestions(
                            questions = questions,
                            userAnswers = DbQuery.myProfile.qHistory,
                            bars =  listOf(bar01, bar02, bar03, bar04, bar05 ,bar06, bar07),
                            countingMethod = CountingMethod.TOTAL_COUNTS,
                            selectedCategories = selectedModules, // These are modules
                            groupBy = "style", // Group results by style
                            filterBy = "module" // Filter questions by module
                        )
                    }

                    ButtonState.HISTORY -> {
                        Log.d("statisticcheck", "exoeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeume History ")


                        handleQuestions(
                            questions = questions,
                            userAnswers = DbQuery.myProfile.qHistory,
                            bars =  listOf(bar01, bar02, bar03, bar04, bar05 ,bar06, bar07),
                            countingMethod = CountingMethod.USED_COUNTS,
                            selectedCategories = selectedModules, // These are modules
                            groupBy = "style", // Group results by style
                            filterBy = "module" // Filter questions by module
                        )


                    }

                    ButtonState.SHADOW -> {

                    }

                }

            })
        }

        closeBtn2.setOnClickListener {
            // Toggle visibility
            selectedStyles.clear()

            if (checkBox21?.isChecked == true) selectedStyles.add("SouLou")
            if (checkBox22?.isChecked == true) selectedStyles.add("Kena")
            if (checkBox23?.isChecked == true) selectedStyles.add("multiple choice")
            if (checkBox24?.isChecked == true) selectedStyles.add("Queue")
            if (checkBox25?.isChecked == true) selectedStyles.add("Mistakes")

            Log.d("statisticcheck", "exoume $selectedStyles ")
            popupLayout2.visibility = if (popupLayout2.visibility == View.VISIBLE) View.GONE else View.VISIBLE

            questionsViewModel.readAllData.observe(viewLifecycleOwner, Observer { questions ->

                when (currentButtonState2) {
                    ButtonState.CIRCLE -> {
                        Log.d("statisticcheck", "exoeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeume Circle ")

                        handleQuestions(
                            questions = questions,
                            userAnswers = DbQuery.myProfile.qHistory,
                            listOf(bar7, bar8, bar9, bar10, bar11 ,bar12,bar13 ,bar14 ,bar15),
                            countingMethod = CountingMethod.TOTAL_COUNTS,
                            selectedCategories = selectedStyles, // These are modules
                            groupBy = "module", // Group results by style
                            filterBy = "style" // Filter questions by module
                        )
                    }

                    ButtonState.HISTORY -> {
                        Log.d("statisticcheck", "exoeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeume History ")


                        handleQuestions(
                            questions = questions,
                            userAnswers = DbQuery.myProfile.qHistory,
                            bars =  listOf(bar7, bar8, bar9, bar10, bar11 ,bar12,bar13 ,bar14 ,bar15),
                            countingMethod = CountingMethod.USED_COUNTS,
                            selectedCategories = selectedStyles, // These are modules
                            groupBy = "module", // Group results by style
                            filterBy = "style" // Filter questions by module
                        )


                    }

                    ButtonState.SHADOW -> {

                    }

                }

            })
        }


        myButton23.setOnClickListener {
            questionsViewModel.readAllData.observe(viewLifecycleOwner, Observer { questions ->
                when (currentButtonState2) {
                    ButtonState.CIRCLE -> {
                        myButton23.setBackgroundResource(R.drawable.modulebutton4)
                        currentButtonState2 = ButtonState.HISTORY

                        handleQuestions(
                            questions = questions,
                            userAnswers = DbQuery.myProfile.qHistory,
                            bars =  listOf(bar7, bar8, bar9, bar10, bar11 ,bar12,bar13 ,bar14 ,bar15),
                            countingMethod = CountingMethod.USED_COUNTS,
                            selectedCategories = selectedStyles, // These are modules
                            groupBy = "module", // Group results by style
                            filterBy = "style" // Filter questions by module
                        )
                        Log.d("statisticcheck", "gia na doume $currentButtonState ")
                    }

                    ButtonState.HISTORY -> {
                        myButton23.setBackgroundResource(R.drawable.modulebutton2)
                        currentButtonState2 = ButtonState.CIRCLE

                        handleQuestions(
                            questions = questions,
                            userAnswers = DbQuery.myProfile.qHistory,
                            bars =  listOf(bar7, bar8, bar9, bar10, bar11 ,bar12,bar13 ,bar14 ,bar15),
                            countingMethod = CountingMethod.TOTAL_COUNTS,
                            selectedCategories = selectedStyles, // These are modules
                            groupBy = "module", // Group results by style
                            filterBy = "style" // Filter questions by module
                        )
                        Log.d("statisticcheck", "gia na doume $currentButtonState ")
                    }

                    ButtonState.SHADOW -> {

                    }

                }
            })
        }



        myButton2.setOnClickListener {
            questionsViewModel.readAllData.observe(viewLifecycleOwner, Observer { questions ->
                when (currentButtonState) {
                    ButtonState.CIRCLE -> {
                        myButton2.setBackgroundResource(R.drawable.modulebutton4)
                        currentButtonState = ButtonState.HISTORY

                        handleQuestions(
                            questions = questions,
                            userAnswers = DbQuery.myProfile.qHistory,
                            bars =  listOf(bar01, bar02, bar03, bar04, bar05 ,bar06, bar07),
                            countingMethod = CountingMethod.USED_COUNTS,
                            selectedCategories = selectedModules, // These are modules
                            groupBy = "style", // Group results by style
                            filterBy = "module" // Filter questions by module
                        )
                        Log.d("statisticcheck", "gia na doume $currentButtonState ")
                    }

                    ButtonState.HISTORY -> {
                        myButton2.setBackgroundResource(R.drawable.modulebutton2)
                        currentButtonState = ButtonState.CIRCLE

                        handleQuestions(
                            questions = questions,
                            userAnswers = DbQuery.myProfile.qHistory,
                            bars =  listOf(bar01, bar02, bar03, bar04, bar05 ,bar06, bar07),
                            countingMethod = CountingMethod.TOTAL_COUNTS,
                            selectedCategories = selectedModules, // These are modules
                            groupBy = "style", // Group results by style
                            filterBy = "module" // Filter questions by module
                        )
                        Log.d("statisticcheck", "gia na doume $currentButtonState ")
                    }

                    ButtonState.SHADOW -> {

                    }

                }
            })
        }








    }



}