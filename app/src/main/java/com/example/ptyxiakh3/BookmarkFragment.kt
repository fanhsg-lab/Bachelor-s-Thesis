package com.example.ptyxiakh3

import QuestionsViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ptyxiakh3.data.Question
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarkFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var questionsViewModel: QuestionsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bookmark, container, false)
        questionsViewModel = ViewModelProvider(this).get(QuestionsViewModel::class.java)
        val questions = mutableListOf<Question>()
        var flagN = 0

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView1)
        bottomNavigationView?.visibility = View.GONE
        var index=0
        DbQuery.g_bmIdList.forEach { id ->
            questionsViewModel.getQuestionById(id).observe(viewLifecycleOwner, Observer { question ->
                question?.let {
                    index++
                    questions.add(it)
                    Log.d("bookmark", it.toString())
                    flagN++
                    val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.ba_recycler_view)

                    val adapter = BookmarkAdapter(requireContext(), questions, findNavController())

                    if(DbQuery.g_bmIdList.size==flagN) {
                        questions.sortBy { it.question_id }
                        Log.d("BookmarkViewHolder", "apappa")
                        Log.d("BookmarkViewHolder", questions.size.toString())
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    }else{
                        Log.d("bookmark", "popa")
                    }
                }
            })
        }

        val backImageView: ImageView = view.findViewById(R.id.ques_list_gridB)
        backImageView.setOnClickListener {
            // Using Navigation Component to navigate up

            findNavController().navigateUp()

        }

        val Popup = view.findViewById<LinearLayout>(R.id.HistorypopupLayout)
        val infoBtn = view.findViewById<ImageView>(R.id.info)
        val closeBtn = view.findViewById<ImageView>(R.id.closeBtn)


        closeBtn.setOnClickListener{
            Popup?.visibility=View.GONE
        }

        infoBtn.setOnClickListener{
            Popup?.visibility=View.VISIBLE
        }



        return view;
    }

    companion object {


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookmarkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookmarkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("BookmarkFragment", "Fragment view is destroyed")
        // Add your custom code here
        DbQuery.saveBookmarks(   object :
            MyCompleteListener {
            override fun onSuccess() {
                Log.d("BookmarkFragment", "Petyxe")

            }

            override fun onFailure() {

            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("BookmarkFragment", "Fragment is detached")
        // Add your custom code here
    }
}