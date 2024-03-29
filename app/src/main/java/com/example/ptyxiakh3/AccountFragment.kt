package com.example.ptyxiakh3

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.ptyxiakh3.DbQuery.myProfile
import com.example.ptyxiakh3.MainActivity.Companion.auth
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView1)
        bottomNavigationView?.visibility = View.VISIBLE

        val view = inflater.inflate(R.layout.fragment_account, container, false)
        val logoutButton = view.findViewById<LinearLayout>(R.id.logoutB)


        val bookmarkButton = view.findViewById<LinearLayout>(R.id.bookmarkB)
        bookmarkButton.setOnClickListener {
            Log.d("LoginFragment", "Sign Up button clicked")
            view.findNavController().navigate(R.id.action_accountFragment_to_bookmarkFragment)
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



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val Textname = view.findViewById<TextView>(R.id.name)
        Textname.text = myProfile.name
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