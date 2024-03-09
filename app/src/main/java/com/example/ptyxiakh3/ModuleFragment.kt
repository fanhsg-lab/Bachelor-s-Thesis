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
        val myList = listOf("if", "for", "Chapter1", "Chapter3", "Chapter2")
        moduleModels.clear() // Clear existing data to handle reset correctly
        // Initialize moduleModels with placeholders for each module in myList
        myList.forEach { moduleItem ->
            moduleModels.add(ModuleModel(moduleItem, 0, 0)) // Assuming 0, 0 can serve as a placeholder
        }

        myList.forEachIndexed { index, moduleItem ->
            questionsViewModel.getQuestionsByModule(moduleItem).observe(viewLifecycleOwner, Observer { questions ->
                val sortedQuestions = questions.sortedBy { it.question_id }

                var Nquestions = 0
                var NotNquestions = 0

                sortedQuestions.forEach { question ->
                    val startIndex = ((question.question_id - 1) * 5).toInt()
                    val historySegment = DbQuery.myProfile.qHistory.substring(startIndex, startIndex + 5)
                    if (historySegment.all { it == 'N' }) {
                        Nquestions++
                    } else {
                        NotNquestions++
                    }
                }

                val allQ = NotNquestions + Nquestions
                // Update the placeholder at the correct position with actual data
                moduleModels[index] = ModuleModel(moduleItem, allQ, NotNquestions)

                // Check if this is the last module to load, then setupRecyclerView
                if (moduleModels.none { it.tests == 0 && it.answered == 0 }) { // Assuming 0, 0 signifies unloaded data
                    setupRecyclerView()
                }
            })
        }
    }

    private fun setupRecyclerView() {
        view?.findViewById<RecyclerView>(R.id.module_recycler_view)?.let { recyclerView ->
            // Ensure the adapter is notified of data changes
            val adapter = (recyclerView.adapter as? AdapterModule) ?: AdapterModule(requireContext(), moduleModels, findNavController())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter.notifyDataSetChanged() // Notify the adapter that the data set has changed
        }
    }


    // Other methods like deleteAllUsers and insertDataToDatabase...
    // ...
}
