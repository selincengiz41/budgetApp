package com.selincengiz.budgetapp.data.model

enum class Content {
    INCOME,
    EXPENSE
}


enum class IncomeExpenseContent(val category: Content, val text: String) {
    SALARY(Content.INCOME, "Salary"),
    RENT(Content.INCOME, "Rent"),
    SOCIAL_TRANSFER(Content.INCOME, "Social Transfer"),
    SHOPPING(Content.EXPENSE, "Shopping"),
    BILLS(Content.EXPENSE, "Bills"),
    ACCOMODATION(Content.EXPENSE, "Accomodation")
}
