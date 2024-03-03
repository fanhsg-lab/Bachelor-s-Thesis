package com.example.ptyxiakh3

import QuestionsViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

class  AllQuestionsFragment : Fragment() {
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
        val chapters = listOf("Chapter1", "Chapter2", "Chapter3")
        val data: LinkedHashMap<String, List<Triple<String, Int, Long>>> = linkedMapOf<String, List<Triple<String, Int, Long>>>().apply {
            chapters.forEach { chapter ->
                this[chapter] = listOf()
            }
        }



        val expandableListView: ExpandableListView = view.findViewById(R.id.expandableListView)


// Initialize the adapter here
        val adapter = MyCustomAdapter(requireContext(), data)
        expandableListView.setAdapter(adapter)

        chapters.forEach { chapter ->
            questionsViewModel.getQuestionsByModule(chapter).observe(viewLifecycleOwner, Observer { questions ->
                if (questions.isEmpty()) {
                    Log.d("QuestionsLog", "No questions found for $chapter module")
                } else {
                    Log.d("QuestionsLog", "Number of questions for $chapter module: ${questions.size}")
                    data[chapter] = questions.map { question ->
                        Triple(question.question_number.toString(), question.difficulty, question.question_id)
                    }



                    adapter.notifyDataSetChanged()
                }
            })

        }

        expandableListView.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            val groupTitle = adapter.getGroup(groupPosition) as String
            val childTitle = adapter.getChild(groupPosition, childPosition) as String
            Log.d("ExpandableListView", "Clicked: $groupTitle - $childTitle")
            true
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
