package com.example.ptyxiakh3

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duolingo.adapters.RankAdapter
import com.example.ptyxiakh3.DbQuery.myPerformance
import com.example.ptyxiakh3.DbQuery.myProfile
import com.example.ptyxiakh3.MainActivity.Companion.auth
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var usersView: RecyclerView

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        // Initialize the RecyclerView
        val usersView = view.findViewById<RecyclerView>(R.id.users_view)


        usersView.layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.VERTICAL
        }
        val limitedUsersList = DbQuery.g_usersList.take(3)
        // Initialize the adapter with the user list from DbQuery
        val adapter = RankAdapter(limitedUsersList)
        usersView.adapter = adapter


        // Inflate the layout for this fragment
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView1)
        bottomNavigationView?.visibility = View.VISIBLE

        val logoutButton = view.findViewById<LinearLayout>(R.id.logoutB)



        val bookmarkButton = view.findViewById<LinearLayout>(R.id.bookmarkB)
        bookmarkButton.setOnClickListener {
            Log.d("LoginFragment", "Sign Up button clicked")
            view.findNavController().navigate(R.id.action_accountFragment_to_bookmarkFragment)
        }

        val LeaderBoardButton = view.findViewById<LinearLayout>(R.id.leaderB)
        LeaderBoardButton.setOnClickListener {
            Log.d("LoginFragment", "Sign Up button clicked")
            view.findNavController().navigate(R.id.action_accountFragment_to_boardFragment)
        }

        val profileButton = view.findViewById<LinearLayout>(R.id.profileB)
        profileButton.setOnClickListener {
            Log.d("LoginFragment", "Sign Up button clicked")
            view.findNavController().navigate(R.id.action_accountFragment_to_userFragment)
        }



        logoutButton.setOnClickListener {
            // Handle the click event
            Log.d("YourFragmentOrActivity", "Logout clicked")
            // Perform your logout logic here
            val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView1)
            bottomNavigationView?.visibility = View.INVISIBLE
            auth.signOut()
            view.findNavController().navigate(R.id.action_accountFragment_to_loginFragment)

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



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val Textname = view.findViewById<TextView>(R.id.name)
        val scoreText = view.findViewById<TextView>(R.id.score)

        Textname.text = myProfile.name + "'s score:"
        scoreText.text = myPerformance.score.toString() + " XP"
        Log.e("UserData", myProfile.name)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}