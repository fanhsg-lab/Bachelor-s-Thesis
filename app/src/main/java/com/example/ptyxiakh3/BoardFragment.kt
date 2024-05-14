package com.example.ptyxiakh3

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duolingo.adapters.RankAdapter
import com.example.ptyxiakh3.DbQuery.g_usersList
import com.example.ptyxiakh3.DbQuery.myPerformance
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BoardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_board, container, false)

        val backImageView = view.findViewById<ImageView>(R.id.backImage)
        backImageView.setOnClickListener {
            // Using Navigation Component to navigate up

            findNavController().navigateUp()

        }

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView1)
        bottomNavigationView?.visibility = View.GONE


        val usersView = view.findViewById<RecyclerView>(R.id.users_view)
        usersView.layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.VERTICAL
        }

        // Debugging to check if the user list is not empty
        Log.d("LeaderBoard", "User List: $g_usersList")
        val adapter = RankAdapter(g_usersList)
        usersView.adapter = adapter

        val total_score = view.findViewById<TextView>(R.id.total_score)
        total_score.text = "Score: " + myPerformance.score.toString()

        val rank = view.findViewById<TextView>(R.id.rank)
        rank.text = "Rank - " + myPerformance.rank.toString()


        val Popup = view.findViewById<LinearLayout>(R.id.HistorypopupLayout)
        val infoBtn = view.findViewById<ImageView>(R.id.info)
        val closeBtn = view.findViewById<ImageView>(R.id.closeBtn)


        closeBtn.setOnClickListener{
            Popup?.visibility=View.GONE
        }

        infoBtn.setOnClickListener{
            Popup?.visibility=View.VISIBLE
        }

        // Return the correctly setup view
        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BoardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BoardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}