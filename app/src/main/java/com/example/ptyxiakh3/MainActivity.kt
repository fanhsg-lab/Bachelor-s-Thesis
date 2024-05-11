package com.example.ptyxiakh3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.ptyxiakh3.DbQuery.g_usersCount
import com.example.ptyxiakh3.DbQuery.g_usersList
import com.example.ptyxiakh3.DbQuery.myPerformance
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {


    companion object {
        lateinit var auth: FirebaseAuth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth =FirebaseAuth.getInstance()
        setTheme(R.style.Theme_App_Light)  // Make sure to define this style in your styles.xml


        DbQuery.g_firestore = FirebaseFirestore.getInstance()


        setContentView(R.layout.activity_main)


        DbQuery.getUserData(object : MyCompleteListener {
            override fun onSuccess() {
                // Handle success, if needed
            }

            override fun onFailure() {
                // Log the error message when onFailure is called
                Log.e("UserData", "Failed to retrieve user data")
            }
        })

        DbQuery.getUsersCount(object : MyCompleteListener {
            override fun onSuccess() {
                Log.d("kainourgio", "correct users count")
            }

            override fun onFailure() {
                // Log the error message when onFailure is called
                Log.e("UserData", "Failed to retrieve user data")
            }
        })


        DbQuery.getTopUsers(object : MyCompleteListener {
            override fun onSuccess() {
                Log.d("kainourgio","mphla  ${myPerformance.score}")


                if (myPerformance.score != 0) {
                    if (!DbQuery.isMeOnTopList) {
                        calculateRank()
                    }


                    Log.d("kainourgio","score ${myPerformance.score} , rank ${myPerformance.rank}")
                }


            }

            override fun onFailure() {

            }
        })

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentmain1) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView1)
        bottomNavigationView.setupWithNavController(navController)




    }

    private fun calculateRank() {
        val lowTopScore = g_usersList.last().score
        val remainingSlots = g_usersCount - 20
        val mySlot = (myPerformance.score * remainingSlots) / lowTopScore
        val rank = if (lowTopScore != myPerformance.score) {
            g_usersCount - mySlot
        } else {
            21
        }

        myPerformance.rank = rank
    }

}