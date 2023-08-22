package com.selincengiz.budgetapp.ui.income

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.selincengiz.budgetapp.NavGraphDirections
import com.selincengiz.budgetapp.R
import com.selincengiz.budgetapp.common.FirebaseUtils
import com.selincengiz.budgetapp.data.model.IncomeExpense
import com.selincengiz.budgetapp.databinding.FragmentIncomeBinding
import com.selincengiz.budgetapp.ui.adapter.BudgetAdapter
import com.selincengiz.budgetapp.ui.adapter.ItemListener
import com.selincengiz.budgetapp.ui.summary.SummaryFragmentDirections


class IncomeFragment : Fragment(), ItemListener {

    private lateinit var binding: FragmentIncomeBinding
    private val adapter by lazy { BudgetAdapter(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_income, container, false)
        binding.recycler.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fireBaseLiveIncomeRead()
    }

    fun fireBaseLiveIncomeRead() {

        FirebaseUtils.db.collection("users").document(FirebaseUtils.auth.currentUser!!.email!!)
            .collection("income_expense").whereEqualTo("incomeExpenseType", true)
            .addSnapshotListener { snapshot, error ->

                var total: Double = 0.0
                val tempList = arrayListOf<IncomeExpense>()

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