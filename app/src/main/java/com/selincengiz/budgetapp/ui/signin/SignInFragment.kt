package com.selincengiz.budgetapp.ui.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.selincengiz.budgetapp.R
import com.selincengiz.budgetapp.common.FirebaseUtils
import com.selincengiz.budgetapp.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.signInFunctions = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    fun signInClicked(email: String, password: String) {
        if (email.isNullOrEmpty().not() && password.isNullOrEmpty().not()) {
            firebaseSignIn(email, password)
        } else {
            Toast.makeText(requireContext(), "Please fill in the blanks.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun firebaseSignIn(email: String, password: String) {
        FirebaseUtils.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                task.addOnSuccessListener {
                    Toast.makeText(
                        requireContext(),
                        "Successfully signed in!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(SignInFragmentDirections.signInToSummary())
                }

                task.addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                         it.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            }
    }

    fun toSignUpClicked() {
        findNavController().navigate(SignInFragmentDirections.signInToSignUp())
    }

}