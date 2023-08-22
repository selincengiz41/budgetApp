package com.selincengiz.budgetapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.selincengiz.budgetapp.common.FirebaseUtils
import com.selincengiz.budgetapp.data.model.IncomeExpense
import com.selincengiz.budgetapp.databinding.ItemBudgetBinding

class BudgetAdapter(private val itemListener: ItemListener) :
    ListAdapter<IncomeExpense, BudgetAdapter.BudgetViewHolder>(BudgetDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder =
        BudgetViewHolder(
            ItemBudgetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), itemListener
        )

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) =
        holder.bind(getItem(position))

    class BudgetViewHolder(
        private val binding: ItemBudgetBinding,
        private val listener: ItemListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(budget: IncomeExpense) = with(binding) {

            var type = budget.incomeExpenseType
            priceColor = type
            tvContent.text = budget.incomeExpenseContent
            tvTitle.text = budget.title

            if (type == true) {
                tvPrice.text = "+" + budget.price.toString()
            } else {
                tvPrice.text = "-" + budget.price.toString()
            }

            delete.setOnClickListener {

                FirebaseUtils.db.collection("users")
                    .document(FirebaseUtils.auth.currentUser!!.email!!).collection("income_expense")
                    .document(budget.docId!!).delete().addOnSuccessListener {

                }.addOnFailureListener {

                        Toast.makeText(delete.context, it.message, Toast.LENGTH_SHORT).show()
                }

            }

            root.setOnClickListener {
                listener.onClicked(budget)
            }
        }

    }

    class BudgetDiffCallBack() : DiffUtil.ItemCallback<IncomeExpense>() {
        override fun areItemsTheSame(oldItem: IncomeExpense, newItem: IncomeExpense): Boolean {
            return oldItem.docId == newItem.docId
        }

        override fun areContentsTheSame(oldItem: IncomeExpense, newItem: IncomeExpense): Boolean {
            return oldItem == newItem
        }

    }


}

interface ItemListener {
    fun onClicked(budget: IncomeExpense)
}
