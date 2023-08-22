package com.selincengiz.budgetapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IncomeExpense(
    val docId: String?,
    val title: String?,
    val price: Double?,
    val incomeExpenseType: Boolean?,
    val incomeExpenseContent: String?
):Parcelable



