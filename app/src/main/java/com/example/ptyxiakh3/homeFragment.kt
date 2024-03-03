package com.example.ptyxiakh3

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.ptyxiakh3.MainActivity.Companion.auth

/**
 * A simple [Fragment] subclass.
 * Use the [homeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class homeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?




    ): View? {

        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_home, container, false)







        Handler().postDelayed({
            if(auth.currentUser == null) {
                view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }else {
                view.findNavController().navigate(R.id.action_homeFragment_to_historyFragment)

            }



        },4000)


        return view
    }

}