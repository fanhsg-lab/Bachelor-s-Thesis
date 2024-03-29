package com.example.ptyxiakh3

import android.util.Log
import com.example.duolingo.Models.ProfileModel
import com.example.ptyxiakh3.data.Question
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

object DbQuery {
    var g_firestore: FirebaseFirestore? = null
    var myProfile: ProfileModel = ProfileModel("NA", "@",  0 ,"f")
    val g_bmIdList: MutableList<Long> = mutableListOf()
    val g_bookmarksList: MutableList<Question> = mutableListOf()




    fun createUserData(email: String, name: String, completeListener: MyCompleteListener) {
        val userData = hashMapOf(
            "EMAIL_ID" to email,
            "NAME" to name,
            "TOTAL_SCORE" to 0,
            "BOOKMARKS" to 0,
            "Q_HISTORY" to "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN"
        )

        val userDoc = g_firestore!!.collection("USERS")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)

        val batch = g_firestore!!.batch()

        batch.set(userDoc, userData)

        val countDoc = g_firestore!!.collection("USERS").document("TOTAL_USERS")

        // Increase COUNT by 1
        batch.update(countDoc, "COUNT", FieldValue.increment(1))

        // Commit the batch
        batch.commit()
            .addOnSuccessListener(OnSuccessListener {
                completeListener.onSuccess()
            })
            .addOnFailureListener(OnFailureListener { e ->
                completeListener.onFailure()
            })
    }

    fun updateQHistory(newQHistory: String, completeListener: MyCompleteListener) {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            completeListener.onFailure()
            return
        }

        g_firestore!!.collection("USERS").document(uid)
            .update("Q_HISTORY", newQHistory)
            .addOnSuccessListener {
                completeListener.onSuccess()
            }
            .addOnFailureListener {
                completeListener.onFailure()
            }


    }

    fun updateQScore(newScore: Int, completeListener: MyCompleteListener) {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            completeListener.onFailure()
            return
        }

        g_firestore!!.collection("USERS").document(uid)
            .update("TOTAL_SCORE", newScore)
            .addOnSuccessListener {
                completeListener.onSuccess()
            }
            .addOnFailureListener {
                completeListener.onFailure()
            }


    }

    fun saveBookmarks(completeListener: MyCompleteListener) {
        val batch = g_firestore!!.batch()
        val uid = FirebaseAuth.getInstance().uid

        if (uid != null) {
            // Preparing bookmark data
            val bmData = mutableMapOf<String, Any>()

            for (i in g_bmIdList.indices) {
                bmData["BM${i + 1}_ID"] = g_bmIdList[i]

            }

            bmData["BM${0 + 1}_ID"] =1
            // Reference to the bookmarks document
            val bmDoc = g_firestore!!.collection("USERS").document(uid)
                .collection("USER_DATA").document("BOOKMARKS")

            // Setting the bookmarks data in batch
            batch.set(bmDoc, bmData)

            // Preparing user data update
            val userData = mutableMapOf<String, Any>()
            userData["BOOKMARKS"] = g_bmIdList.size+1

            // Reference to the user document
            val userDoc = g_firestore!!.collection("USERS").document(uid)
            Log.d("book1", "mphka1")
            // Updating the user document in the batch
            batch.update(userDoc, userData)
        }

        // Committing the batch
        batch.commit()
            .addOnSuccessListener {
                Log.d("book1", "success")
                completeListener.onSuccess()
            }
            .addOnFailureListener {
                completeListener.onFailure()
            }
    }





    fun getUserData(completeListener: MyCompleteListener) {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            completeListener.onFailure()
            return
        }

        g_firestore!!.collection("USERS").document(uid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                with(DbQuery.myProfile) {
                    name = documentSnapshot.getString("NAME") ?: ""
                    email = documentSnapshot.getString("EMAIL_ID") ?: ""
                    bookmarksCount = documentSnapshot.getLong("BOOKMARKS")?.toInt() ?: 0
                    qHistory = documentSnapshot.getString("Q_HISTORY") ?: "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"
                }

                // After successfully fetching user data, load bookmarks IDs
                loadBmIds(object : MyCompleteListener {
                    override fun onSuccess() {
                        // Handle success for loading bookmarks IDs
                        completeListener.onSuccess()
                    }

                    override fun onFailure() {
                        // Handle failure for loading bookmarks IDs
                        completeListener.onFailure()
                    }
                })
            }
            .addOnFailureListener {
                completeListener.onFailure()
            }
    }


    fun loadBmIds(completeListener: MyCompleteListener) {
        g_bmIdList.clear()

        // Accessing the Firestore collection
        FirebaseAuth.getInstance().uid?.let {
            g_firestore!!.collection("USERS").document(it)
                .collection("USER_DATA").document("BOOKMARKS")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    Log.d("book1", "Entered the class")
                    val count = myProfile.bookmarksCount

                    for (i in 0 until count) {
                        Log.d("book1", "Inside add")
                        val bmID = documentSnapshot.getLong("BM${i + 1}_ID")?.toLong()

                        Log.d("book1", "loadBmIds $bmID")
                        // This is the ID of the question
                        if (bmID != null) {
                            Log.d("book13", bmID.toString())
                            g_bmIdList.add(bmID)
                        }
                    }

                    completeListener.onSuccess()
                }
                .addOnFailureListener {
                    completeListener.onFailure()
                }
        }
    }

/*
    fun loadBookmarks(completeListener: MyCompleteListener) {
        g_bookmarksList.clear()

        var tmp = 0

        if (g_bmIdList.isEmpty()) {
            completeListener.onSuccess()
        }

        for (docID in g_bmIdList) {
            g_firestore!!.collection("Questions").document(docID)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        g_bookmarksList.add(QuestionModel(
                            documentSnapshot.id,
                            documentSnapshot.getString("QUESTION"),
                            documentSnapshot.getString("A"),
                            documentSnapshot.getString("B"),
                            documentSnapshot.getString("C"),
                            documentSnapshot.getString("D"),
                            documentSnapshot.getLong("ANSWER")?.toInt() ?: 0,
                            0,
                            -1,
                            false
                        ))
                    }

                    tmp++
                    if (tmp == g_bmIdList.size) {
                        completeListener.onSuccess()
                    }
                }
                .addOnFailureListener {
                    completeListener.onFailure()
                }
        }
    }

*/


}

