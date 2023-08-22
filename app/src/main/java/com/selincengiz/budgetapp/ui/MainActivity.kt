package com.selincengiz.budgetapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.selincengiz.budgetapp.NavGraphDirections
import com.selincengiz.budgetapp.R
import com.selincengiz.budgetapp.common.FirebaseUtils
import com.selincengiz.budgetapp.common.Extensions.loadUrl
import com.selincengiz.budgetapp.data.model.IncomeExpense

import com.selincengiz.budgetapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        with(binding) {

            //Fab click listener moveable olarak değiştirilidiğinden databinding ile fonksiyon tanımlaması yapamadım.
            binding.add.setOnClickListener {

                var budget = IncomeExpense(null, null, 0.0, null, null)
                binding.fragmentContainerView.findNavController()
                    .navigate(NavGraphDirections.actionGlobalAddOrEditFragment(budget))
            }

            //Custom Toolbar variable
            binding.toolbar.mainActivityFunctions = this@MainActivity


            //Bottom Navigation Menu
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

            NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
            navHostFragment.navController.addOnDestinationChangedListener { controller, destination, _ ->

                when (destination.id) {

                    R.id.summaryFragment -> {
                        binding.toolbar.profile.loadUrl(FirebaseUtils.auth.currentUser?.photoUrl)
                        binding.toolbar.name = FirebaseUtils.auth.currentUser?.displayName
                        binding.visibilityBottomNav = true
                        binding.visibilityToolbar = true
                        binding.visibilityFab = true

                    }

                    R.id.expenseFragment -> {
                        binding.toolbar.profile.loadUrl(FirebaseUtils.auth.currentUser?.photoUrl)
                        binding.toolbar.name = FirebaseUtils.auth.currentUser?.displayName
                        binding.visibilityBottomNav = true
                        binding.visibilityToolbar = true
                        binding.visibilityFab = true

                    }

                    R.id.incomeFragment -> {
                        binding.toolbar.profile.loadUrl(FirebaseUtils.auth.currentUser?.photoUrl)
                        binding.toolbar.name = FirebaseUtils.auth.currentUser?.displayName
                        binding.visibilityBottomNav = true
                        binding.visibilityToolbar = true
                        binding.visibilityFab = true


                    }

                    else -> {
                        binding.visibilityBottomNav = false
                        binding.visibilityToolbar = false
                        binding.visibilityFab = false

                    }

                }


            }

        }
    }

    fun logOut() {
        FirebaseUtils.auth.signOut()
        binding.fragmentContainerView.findNavController()
            .navigate(R.id.action_global_signInFragment)
    }


}