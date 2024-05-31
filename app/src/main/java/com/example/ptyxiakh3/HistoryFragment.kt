package com.example.ptyxiakh3

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ptyxiakh3.DbQuery.myProfile
import com.example.navigationtry2.HistoryModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yourpackage.NetworkUtil

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
public var firstInvalidPosition: Int? = null
public var firstInvalidIndex: Int? = null

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    val historyModels: ArrayList<HistoryModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView1)
        bottomNavigationView?.visibility = View.VISIBLE

        Log.d("database2", "edo ${myProfile.qHistory}")

        if (!NetworkUtil.isInternetAvailable(requireContext())) {
            showNoInternetDialog()
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.history_recycler_view)
        setupHistoryModels()

        val adapter = AdapterHistory(requireContext(), historyModels, findNavController())

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val popup = view.findViewById<LinearLayout>(R.id.HistorypopupLayout)
        val infoBtn = view.findViewById<ImageView>(R.id.info)
        val closeBtn = view.findViewById<ImageView>(R.id.closeBtn)
        val leftImage = view.findViewById<ImageView>(R.id.ques_list_gridB)
        val instagramUsername = "fanis__gn"

        val buttonScrollToItem = view.findViewById<ImageView>(R.id.buttonScrollToItem)
        Log.d("moveto", "button exists: $buttonScrollToItem")
        buttonScrollToItem.setOnClickListener {
            Log.d("moveto", "button clicked ,firstInvalidPosition $firstInvalidPosition, firstInvalidIndex $firstInvalidIndex")
            firstInvalidPosition?.let { position ->
                recyclerView.smoothScrollToPosition(position)
                recyclerView.post {
                    val holder = recyclerView.findViewHolderForAdapterPosition(position)
                    firstInvalidIndex?.let { index ->
                        val buttonTag = "btn${index + 1}"
                        val button = holder?.itemView?.findViewWithTag<Button>(buttonTag)
                        button?.requestFocus() // Replace with actual button ID
                    }
                }
            }
        }

        leftImage.setOnClickListener {
            openInstagramProfile(instagramUsername)
        }

        closeBtn.setOnClickListener {

            popup?.visibility = View.GONE
        }

        infoBtn.setOnClickListener {
            popup?.visibility = View.VISIBLE
        }




        return view
    }

    private fun openInstagramProfile(username: String) {
        val uri = Uri.parse("http://instagram.com/_u/$username")
        val instagramIntent = Intent(Intent.ACTION_VIEW, uri)
        instagramIntent.setPackage("com.instagram.android")

        // Check if the Instagram app is installed
        if (isAppInstalled("com.instagram.android")) {
            startActivity(instagramIntent)
        } else {
            // If the Instagram app is not installed, open the profile in a web browser
            val webUri = Uri.parse("http://instagram.com/$username")
            val webIntent = Intent(Intent.ACTION_VIEW, webUri)
            startActivity(webIntent)
        }
    }

    private fun isAppInstalled(packageName: String): Boolean {
        return try {
            requireContext().packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    override fun onResume() {
        super.onResume()
        if (!NetworkUtil.isInternetAvailable(requireContext())) {
            showNoInternetDialog()
        }
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("No Internet Connection")
            .setMessage("This app requires an internet connection. Please check your network settings.")
            .setPositiveButton("OK") { _, _ -> requireActivity().finish() }
            .setCancelable(false)
            .show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setupHistoryModels() {
        historyModels.add(HistoryModel("Κεφάλαιο 1", "Εισαγωγή στην ψευδογλώσσα", "fasdfasdf", R.drawable.logo, 5))
        historyModels.add(HistoryModel("Κεφάλαιο 2", "Δομές Ακολουθίας", "fasdfasdf", R.drawable.logo, 6))
        historyModels.add(HistoryModel("Κεφάλαιο 3", "Δομές Επανάληψης", "fasdfasdf", R.drawable.logo, 7))
        historyModels.add(HistoryModel("Κεφάλαιο 4", "Λες απλές προτάσεις, μιλάς στον πληθυντικό sdfg sdsdfg ssdf", "fasdfasdf", R.drawable.logo, 8))
    }
}
