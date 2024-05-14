package com.example.ptyxiakh3

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class UserFragment : Fragment() {

    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var editButton: LinearLayout
    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var profileText: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var buttonLayout: LinearLayout
    private lateinit var progressDialog: Dialog
    private lateinit var dialogText: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView1)
        bottomNavigationView?.visibility = View.GONE

        val backImageView = view.findViewById<ImageView>(R.id.backImage)
        backImageView.setOnClickListener {
            // Using Navigation Component to navigate up

            findNavController().navigateUp()

        }

        name = view.findViewById(R.id.mp_name)
        email = view.findViewById(R.id.mp_email)
        phone = view.findViewById(R.id.mp_phone)
        editButton = view.findViewById(R.id.editB)
        cancelButton = view.findViewById(R.id.cancelB)
        saveButton = view.findViewById(R.id.saveB)
        buttonLayout = view.findViewById(R.id.button_layout)

        progressDialog = Dialog(requireContext()).apply {
            setContentView(R.layout.dialog_layout)
            window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialogText = findViewById(R.id.dialog_text)
            dialogText.text = "Updating Data..."
        }

        disableEditing()

        editButton.setOnClickListener { enableEditing() }
        cancelButton.setOnClickListener { disableEditing() }
        saveButton.setOnClickListener {
            if (validate()) {
                saveData()
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

        return view
    }

    private fun disableEditing() {
        name.isEnabled = false
        email.isEnabled = false
        phone.isEnabled = false
        buttonLayout.visibility = View.GONE
        // Assume DbQuery provides user profile data
        name.setText(DbQuery.myProfile.name)
        email.setText(DbQuery.myProfile.email)
        phone.setText(DbQuery.myProfile.phone)

        val profileName = DbQuery.myProfile.name
    }

    private fun enableEditing() {
        name.isEnabled = true
        phone.isEnabled = true
        buttonLayout.visibility = View.VISIBLE
    }

    private fun validate(): Boolean {
        val nameStr = name.text.toString()
        val phoneStr = phone.text.toString()

        if (nameStr.isEmpty()) {
            name.error = "Name cannot be empty!"
            return false
        }

        if (phoneStr.isNotEmpty() && (phoneStr.length != 10 || !TextUtils.isDigitsOnly(phoneStr))) {
            phone.error = "Enter a valid phone number"
            return false
        }

        return true
    }

    private fun saveData() {
        progressDialog.show()
        val phoneStr = if (phone.text.toString().isEmpty()) null else phone.text.toString()

        DbQuery.saveProfileData(name.text.toString(), phoneStr, object : MyCompleteListener {
            override fun onSuccess() {
                Toast.makeText(requireContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                disableEditing()
                progressDialog.dismiss()
            }

            override fun onFailure() {
                Toast.makeText(requireContext(), "Something went wrong! Please try again later.", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        })
    }
}
