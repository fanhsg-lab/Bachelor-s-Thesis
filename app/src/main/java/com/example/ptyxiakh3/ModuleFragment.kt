package com.example.ptyxiakh3

import QuestionsViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ptyxiakh3.data.Question
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ModuleFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private val moduleModels: ArrayList<ModuleModel> = ArrayList()
    private val questionsViewModel: QuestionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_module, container, false)
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView1)
        bottomNavigationView?.visibility = View.VISIBLE
        Log.d("QuestionLog", "SIZE ${moduleModels.size}")
        moduleModels.clear()
        Log.d("QuestionLog", "SIZE clear ${moduleModels.size}")
        setupModuleModels()
        return view
    }

    private fun setupModuleModels() {
        val myList = listOf("if", "for")
        var modulesToLoad = myList.size

        myList.forEach { moduleItem ->
            questionsViewModel.getQuestionsByModule(moduleItem).observe(viewLifecycleOwner, Observer { questions ->
                val totalQuestions = questions.size
                var Nquestions = 0
                var NotNquestions = 0
                var processedQuestions = 0
                questions.forEach { question ->
                    val startIndex = ((question.question_id - 1) * 5).toInt()
                    processedQuestions++
                    Log.d("QuestionLog", "Question ID ${question.question_id}")
                    Log.d("QuestionLog", "Question ID ${DbQuery.myProfile.qHistory}")
                    val historySegment = DbQuery.myProfile.qHistory.substring(startIndex, startIndex + 5)
                    if (historySegment.all { it == 'N' }) {
                        Nquestions++
                    } else {
                        NotNquestions++
                    }

                    if (processedQuestions == totalQuestions) {
                        var allQ = NotNquestions + Nquestions
                        Log.d("QuestionLog", "Question ID ${allQ} + ${NotNquestions}")
                        moduleModels.add(ModuleModel(moduleItem, allQ, NotNquestions))
                        modulesToLoad--
                        if (modulesToLoad <= 0) {
                            setupRecyclerView()
                        }
                    }
                }
            })
        }

        if (myList.isEmpty()) {
            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() {
        view?.findViewById<RecyclerView>(R.id.module_recycler_view)?.let { recyclerView ->
            val adapter = AdapterModule(requireContext(), moduleModels, findNavController())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    // Other methods like deleteAllUsers and insertDataToDatabase...
    // ...
}
