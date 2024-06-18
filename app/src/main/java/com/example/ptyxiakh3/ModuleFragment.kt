package com.example.ptyxiakh3

import QuestionsViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ptyxiakh3.MainActivity.Companion.myList2
import com.example.ptyxiakh3.data.Question
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yourpackage.NetworkUtil

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

    private fun setupModuleModels() {
        val myList = myList2

        moduleModels.clear() // Clear existing data to handle reset correctly
        // Initialize moduleModels with placeholders for each module in myList
        myList.forEach { moduleItem ->
            moduleModels.add(ModuleModel(moduleItem, 0, 0)) // Assuming 0, 0 can serve as a placeholder
        }

        myList.forEachIndexed { index, moduleItem ->
            questionsViewModel.getQuestionsByModule(moduleItem).observe(viewLifecycleOwner, Observer { questions ->
                val sortedQuestions = questions.sortedBy { it.question_id }
                //Log.d("QuestionLog", "SIZadfafasdfr $sortedQuestions gie autooooooo $moduleItem")
                var Nquestions = 0
                var NotNquestions = 0

                sortedQuestions.forEach { question ->
                    val startIndex = ((question.question_id - 1) * 5).toInt()
                    Log.d("problhma","`1 " + DbQuery.myProfile.qHistory)
                    val historySegment = DbQuery.myProfile.qHistory.substring(startIndex, startIndex + 5)
                    Log.d("problhma","`2")
                    if (historySegment.all { it == 'N' }) {
                        Nquestions++
                    } else {
                        NotNquestions++
                    }
                }

                val allQ = NotNquestions + Nquestions

                Log.d("QuestionLog", "allQ allQ  $allQ   ")
                // Update the placeholder at the correct position with actual data
                moduleModels[index] = ModuleModel(moduleItem, allQ, NotNquestions)
                Log.d("QuestionLog", "Eimai edoppp  $moduleModels   ")
                // Check if this is the last module to load, then setupRecyclerView


                    Log.d("QuestionLog", "Eimai edo")
                    setupRecyclerView()

            })
        }
    }

    private fun setupRecyclerView() {
        view?.findViewById<RecyclerView>(R.id.module_recycler_view)?.let { recyclerView ->
            // Ensure the adapter is notified of data changes
            val adapter = (recyclerView.adapter as? AdapterModule) ?: AdapterModule(requireContext(), moduleModels, findNavController())
            recyclerView.adapter = adapter

            // Set up the RecyclerView with a GridLayoutManager to display 2 columns
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // Number of columns is set to 2

            adapter.notifyDataSetChanged() // Notify the adapter that the data set has changed
        }
    }



    // Other methods like deleteAllUsers and insertDataToDatabase...
    // ...
}
