package com.example.ptyxiakh3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var resendVerificationButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()  // Ensure FirebaseAuth instance is initialized
        resendVerificationButton = view.findViewById(R.id.resendVerificationButton)

        setUpLoginButton(view)
        setUpSignUpButton(view)
        setUpForgotPasswordButton(view)
        initializeGoogleLogin(view)  // Correct method to set up Google Sign-In

        setUpResendVerificationButton()

        val Popup = view.findViewById<LinearLayout>(R.id.HistorypopupLayout)
        val infoBtn = view.findViewById<ImageView>(R.id.info)
        val closeBtn = view.findViewById<ImageView>(R.id.closeBtn)

        closeBtn.setOnClickListener {
            Popup?.visibility = View.GONE
        }

        infoBtn.setOnClickListener {
            Popup?.visibility = View.VISIBLE
        }
        val email = view.findViewById<EditText>(R.id.email)
        val user = MainActivity.auth.currentUser
        if (user != null){
            email.setText(user.email)
        }

        return view
    }

    private fun setUpLoginButton(view: View) {
        val loginButton = view.findViewById<TextView>(R.id.loginB)
        loginButton.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.email).text.toString()
            val password = view.findViewById<EditText>(R.id.password).text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                performLogin(email, password)
            } else {
                Toast.makeText(requireContext(), "Email and password must not be empty.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                if (user != null && user.isEmailVerified) {
                    // Email is verified, allow login
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    activity?.finish()
                } else {
                    // Email is not verified, prompt the user to verify
                    Toast.makeText(requireContext(), "Please verify your email address.", Toast.LENGTH_LONG).show()
                    user?.sendEmailVerification()
                }
            } else {
                Toast.makeText(requireContext(), "Login failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun setUpSignUpButton(view: View) {
        val signupButton = view.findViewById<TextView>(R.id.signupB)
        signupButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setUpForgotPasswordButton(view: View) {
        val forgotPasswordButton = view.findViewById<TextView>(R.id.forgot_pass)
        forgotPasswordButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_forgotPassFragment3)
        }
    }

    private fun setUpResendVerificationButton() {
        resendVerificationButton.setOnClickListener {
            Log.e("EmailVerification", "mphka")
            val user = auth.currentUser
            user?.email
            Log.e("EmailVerification", "mphka $user")
            user?.sendEmailVerification()
                ?.addOnCompleteListener { verifyTask ->
                    Log.e("EmailVerification", "mphka2")
                    if (verifyTask.isSuccessful) {
                        Toast.makeText(requireContext(), "Verification email sent.", Toast.LENGTH_LONG).show()
                    } else {
                        verifyTask.exception?.let {
                            Log.e("EmailVerification", "Failed to send verification email: ${it.message}")
                            Toast.makeText(requireContext(), "F : ${it.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }


        }
    }

    private fun initializeGoogleLogin(view: View) {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        // Set up the Google Sign-In button
        val googleBtn = view.findViewById<TextView>(R.id.google)
        googleBtn.setOnClickListener {
            Log.d("GoogleLogin", "Preparing to launch Google SignIn intent")
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        Log.d("GoogleLogin", "Preparing to launch Google SignIn intent")
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 13) // Ensure this is being called
        Log.d("GoogleLogin", "SignIn intent launched with request code 13")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("GoogleLogin", "onActivityResult with requestCode: $requestCode, resultCode: $resultCode")

        if (requestCode == 13) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    val account = task.getResult(ApiException::class.java)
                    email = account.email ?: "No email found"
                    Log.d("GoogleLogin", "Google SignIn successful, proceeding with Firebase email $email")
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Log.e("GoogleLogin", "Google SignIn failed with error code: ${e.statusCode}", e)
                    handleSignInError(e)
                }
            } else {
                Log.d("GoogleLogin", "Google SignIn was cancelled or failed, resultCode: $resultCode")
                if (data != null) {
                    Log.d("GoogleLogin", "Intent data on failure: ${data.extras}")
                }
            }
        }
    }

    private fun handleSignInError(e: ApiException) {
        when (e.statusCode) {
            GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> Log.d("GoogleLogin", "User cancelled the sign-in")
            GoogleSignInStatusCodes.SIGN_IN_FAILED -> Log.d("GoogleLogin", "Sign-in attempt failed")
            GoogleSignInStatusCodes.NETWORK_ERROR -> Log.d("GoogleLogin", "Network error occurred")
            else -> Log.d("GoogleLogin", "Error signing in: ${GoogleSignInStatusCodes.getStatusCodeString(e.statusCode)}")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("GoogleLogin", "Firebase Google authentication successful")
                val username = email.substringBefore('@')
                DbQuery.createUserData(email, username, object : MyCompleteListener {
                    override fun onSuccess() {
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        activity?.finish()
                    }

                    override fun onFailure() {
                        // Handle failure
                    }
                })
            } else {
                Log.e("FirebaseAuth", "Firebase Google authentication failed: ${task.exception?.message}")
                Toast.makeText(requireContext(), "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
