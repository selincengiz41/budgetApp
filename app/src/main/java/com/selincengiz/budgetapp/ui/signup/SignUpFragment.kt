package com.selincengiz.budgetapp.ui.signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.selincengiz.budgetapp.R
import com.selincengiz.budgetapp.common.FirebaseUtils
import com.selincengiz.budgetapp.common.Extensions.getBitmapFromUri
import com.selincengiz.budgetapp.common.Extensions.loadUrl
import com.selincengiz.budgetapp.common.PermissionUtils
import com.selincengiz.budgetapp.common.PermissionUtils.checkMediaPermission
import com.selincengiz.budgetapp.common.PermissionUtils.shouldShowRationale
import com.selincengiz.budgetapp.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                openGalleryIntent()

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.signUpFunctions = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    fun signUpClicked(email: String, password: String, name: String, imageUri: Uri) {
        if (email.isNullOrEmpty().not() && password.isNullOrEmpty().not() && name.isNullOrEmpty()
                .not()
        ) {
            firebaseSignUp(email, password, name, imageUri)
        } else {
            Toast.makeText(requireContext(), "Please fill in the blanks.", Toast.LENGTH_SHORT)
                .show()
        }

    }

    fun firebaseSignUp(email: String, password: String, name: String, imageUri: Uri) {

        FirebaseUtils.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                task.addOnSuccessListener {
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                        photoUri = imageUri
                    }
                    FirebaseUtils.auth.currentUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { task ->

                            task.addOnSuccessListener {
                                Toast.makeText(
                                    requireContext(),
                                    "Successfully signed up!!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                findNavController().navigate(SignUpFragmentDirections.signUpToSummary())
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
                task.addOnFailureListener {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                        .show()
                }

            }


    }

    fun toSignInClicked() {
        findNavController().navigate(SignUpFragmentDirections.signUpToSignIn())
    }

    fun imageViewClicked() {
        requireContext().checkMediaPermission(
            onGranted = {
                openGalleryIntent()
            },
            onDenied = {
                requestPermissionLauncher.launch(PermissionUtils.galleryPermission)
            }
        )

        requireActivity().shouldShowRationale(
            onGranted = {
                Toast.makeText(
                    requireContext(),
                    R.string.permission_required,
                    Toast.LENGTH_SHORT
                ).show()

                requestPermissionLauncher.launch(
                    PermissionUtils.galleryPermission
                )
            },
        )


    }

    private val GALLERY_REQUEST_CODE = 100
    private fun openGalleryIntent() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            selectedImageUri?.let {
                binding.imageUri = it
                binding.imageView.loadUrl(it)

            }


        }
    }

}