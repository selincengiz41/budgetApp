package com.selincengiz.budgetapp.common

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirebaseUtils {
    var auth: FirebaseAuth = Firebase.auth
    val db = Firebase.firestore
}