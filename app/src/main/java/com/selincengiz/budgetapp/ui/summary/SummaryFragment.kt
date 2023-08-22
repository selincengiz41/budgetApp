package com.selincengiz.budgetapp.ui.summary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.ktx.Firebase
import com.selincengiz.budgetapp.NavGraphDirections
import com.selincengiz.budgetapp.R
import com.selincengiz.budgetapp.common.FirebaseUtils
import com.selincengiz.budgetapp.data.model.Content
import com.selincengiz.budgetapp.data.model.IncomeExpense
import com.selincengiz.budgetapp.data.model.IncomeExpenseContent
import com.selincengiz.budgetapp.databinding.FragmentSummaryBinding
import com.selincengiz.budgetapp.ui.adapter.BudgetAdapter
import com.selincengiz.budgetapp.ui.adapter.ItemListener
import kotlin.math.log


class SummaryFragment : Fragment(), ItemListener {

    private lateinit var binding: FragmentSummaryBinding
    private val adapter by lazy { BudgetAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_summary, container, false)
        binding.recycler.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fireBaseLiveRead()
    }

    fun fireBaseLiveRead() {

        FirebaseUtils.db.collection("users").document(FirebaseUtils.auth.currentUser!!.email!!)
            .collection("income_expense").addSnapshotListener { snapshot, error ->

                val tempList = arrayListOf<IncomeExpense>()
                var total: Double = 0.0

                snapshot?.forEach { document ->
                    tempList.add(
                        IncomeExpense(
                            document.id,
                            document.get("title") as String,
                            document.get("price") as Double,
                            document.get("incomeExpenseType") as Boolean,
                            document.get("incomeExpenseContent") as String
                        )
                    )


                    if (document.get("incomeExpenseType") as Boolean) {
                        total += document.get("price") as Double

                    } else {
                        total -= document.get("price") as Double
                    }

                }
                binding.totalBudget = total

                adapter.submitList(tempList)
            }
    }

    override fun onClicked(budget: IncomeExpense) {
        findNavController().navigate(NavGraphDirections.actionGlobalAddOrEditFragment(budget))
    }


}