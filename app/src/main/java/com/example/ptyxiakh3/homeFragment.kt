package com.example.ptyxiakh3

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.ptyxiakh3.MainActivity.Companion.auth

class homeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        Handler().postDelayed({
            val user = auth.currentUser
            Log.e("EmailVerification", "mphka $user")
            if (user == null) {
                view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            } else {
                if (user.isEmailVerified) {
                    view.findNavController().navigate(R.id.action_homeFragment_to_historyFragment)
                } else {
                    // Email is not verified, prompt the user to verify
                    Toast.makeText(requireContext(), "Please verify your email address.", Toast.LENGTH_LONG).show()
                    view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                }
            }
        }, 4000)

        return view
    }
}
