package com.example.ptyxiakh3

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ptyxiakh3.DbQuery.myProfile
import com.example.navigationtry2.HistoryModel
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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



        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.history_recycler_view)
        setupHistoryModels()

        val adapter = AdapterHistory(requireContext(), historyModels, findNavController())

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



        return view;
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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun setupHistoryModels(){

        historyModels.add( HistoryModel("Chapter 1", "Λες απλές προτάσεις, μιλάς στον πληθυντικό sdfg sdsdfg ssdf ", R.drawable.logo,5))
        historyModels.add( HistoryModel("Chapter 2", "Λες δύσκολες λέξεςι και δύσκολες προτάσεις προσπάθησε πολύ σκληρά", R.drawable.logo,6))
        historyModels.add( HistoryModel("Chapter 3", "Lorem ipsum", R.drawable.logo,7))
        historyModels.add( HistoryModel("Chapter 1", "Λες απλές προτάσεις, μιλάς στον πληθυντικό sdfg sdsdfg ssdf ", R.drawable.logo,8))
        historyModels.add( HistoryModel("Chapter 2", "Λες δύσκολες λέξεςι και δύσκολες προτάσεις προσπάθησε πολύ σκληρά", R.drawable.logo,9))
        historyModels.add( HistoryModel("Chapter 3", "Lorem ipsum", R.drawable.logo,10))


    }
}