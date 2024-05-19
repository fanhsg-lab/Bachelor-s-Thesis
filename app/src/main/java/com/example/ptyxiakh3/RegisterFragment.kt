package com.example.ptyxiakh3

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.ptyxiakh3.MainActivity.Companion.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {
    private lateinit var resendVerificationButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val button = view.findViewById<Button>(R.id.signupB)
        val backButton = view.findViewById<ImageView>(R.id.backB)

        backButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        button.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.emailID).text.toString()
            val password = view.findViewById<EditText>(R.id.password).text.toString()
            val username = view.findViewById<EditText>(R.id.username).text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                Firebase.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = Firebase.auth.currentUser
                            user?.sendEmailVerification()
                                ?.addOnCompleteListener { verifyTask ->
                                    if (verifyTask.isSuccessful) {
                                        DbQuery.createUserData(email, username, object : MyCompleteListener {
                                            override fun onSuccess() {
                                                Toast.makeText(requireContext(), "Verification email sent. Please check your email.", Toast.LENGTH_LONG).show()
                                                view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                                            }

                                            override fun onFailure() {
                                                Toast.makeText(requireContext(), "User creation failed", Toast.LENGTH_LONG).show()
                                            }
                                        })
                                    } else {
                                        verifyTask.exception?.let {
                                            Log.e("EmailVerification", "Failed to send verification email: ${it.message}")
                                            Toast.makeText(requireContext(), "F : ${it.message}", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                        } else {
                            task.exception?.let {
                                Log.e("UserCreation", "Registration failed: ${it.message}")
                                Toast.makeText(requireContext(), "Registration failed: ${it.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "Email and Password must not be empty.", Toast.LENGTH_LONG).show()
            }
        }

        val popup = view.findViewById<LinearLayout>(R.id.HistorypopupLayout)
        val infoBtn = view.findViewById<ImageView>(R.id.info)
        val closeBtn = view.findViewById<ImageView>(R.id.closeBtn)

        closeBtn.setOnClickListener {
            popup?.visibility = View.GONE
        }

        infoBtn.setOnClickListener {
            popup?.visibility = View.VISIBLE
        }




        return view
    }
}
