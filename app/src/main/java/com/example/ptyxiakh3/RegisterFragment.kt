package com.example.ptyxiakh3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val button = view.findViewById<Button>(R.id.signupB)

        val backButton = view.findViewById<ImageView>(R.id.backB) // Find the ImageView by its ID

        backButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

        }


        button.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.emailID).text.toString()
            val password = view.findViewById<EditText>(R.id.password).text.toString()
            val username = view.findViewById<EditText>(R.id.username).text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()) {
                MainActivity.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful) {


                            //Create User με το DbQuery
                            DbQuery.createUserData(    email, username, object :
                                MyCompleteListener {
                                    override fun onSuccess() {
                                        Toast.makeText(requireContext(), "yes", Toast.LENGTH_LONG).show()
                                        view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                                    }

                                    override fun onFailure() {
                                        Toast.makeText(requireContext(), "no", Toast.LENGTH_LONG).show()
                                    }
                                })





                        }
                    }
                    .addOnFailureListener { exception ->
                        activity?.let {
                            Toast.makeText(it, exception.localizedMessage, Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}