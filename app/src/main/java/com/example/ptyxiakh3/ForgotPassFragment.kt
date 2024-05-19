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
import com.example.ptyxiakh3.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPassFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forgot_pass, container, false)

        auth = FirebaseAuth.getInstance()

        val btnResetPassword: Button = view.findViewById(R.id.btnResetPassword)
        val etEmail: EditText = view.findViewById(R.id.etEmail)

        btnResetPassword.setOnClickListener {
            val email = etEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Reset link sent to your email. Please check your inbox.", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Failed to send reset email: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(context, "Please enter your email address.", Toast.LENGTH_LONG).show()
            }
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

        val backButton = view.findViewById<ImageView>(R.id.backB) // Find the ImageView by its ID

        backButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

        }

        return view
    }

}
