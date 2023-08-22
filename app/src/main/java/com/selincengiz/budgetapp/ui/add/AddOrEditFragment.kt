package com.selincengiz.budgetapp.ui.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.selincengiz.budgetapp.R
import com.selincengiz.budgetapp.common.FirebaseUtils
import com.selincengiz.budgetapp.data.model.Content
import com.selincengiz.budgetapp.data.model.IncomeExpense
import com.selincengiz.budgetapp.data.model.IncomeExpenseContent
import com.selincengiz.budgetapp.databinding.FragmentAddOrEditBinding
import kotlin.random.Random


class AddOrEditFragment : BottomSheetDialogFragment(), RadioGroup.OnCheckedChangeListener,
    AdapterView.OnItemClickListener {
    private lateinit var binding: FragmentAddOrEditBinding
    private val args by navArgs<AddOrEditFragmentArgs>()
    private lateinit var budget:IncomeExpense
    private lateinit var spinnerList: List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_or_edit, container, false)

        //Event on layout
        binding.addOrEditEvents = this

        binding.act.setOnItemClickListener(this@AddOrEditFragment)

        binding.radioGroup.setOnCheckedChangeListener(this@AddOrEditFragment)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
             budget = args.incomeExpense

            if (budget.title.isNullOrEmpty() && budget.docId.isNullOrEmpty()) {
                isEnableSpinner = false


            } else {
                isEnableSpinner = true
                titleTextEdit.setText(budget.title)
                priceTextEdit.setText(budget.price.toString())
                selectedSaveType = budget.incomeExpenseContent

                if (budget.incomeExpenseType == true) {
                    //income
                    radioGroup.check(incomeButton.id)

                } else {
                    //expense
                    radioGroup.check(expenseButton.id)
                }

                act.setText(selectedSaveType)
             /////SELECT EDEMİYORUM KAFAYI YİYECEM///////////////////////////////////////////////////////

            }


        }
    }


    fun setAdapterToSpinner(tag: Any) {
        spinnerList =
            IncomeExpenseContent.values().filter { it.category == tag }
                .map { it.text }

        val saveTypeAdapter = ArrayAdapter(
            requireContext(),
            androidx.transition.R.layout.support_simple_spinner_dropdown_item,
            spinnerList
        )
        binding.act.setAdapter(saveTypeAdapter)


    }

    fun onSave(isSelected: Boolean) {

        if (isSelected) {
            val title = binding.titleTextEdit.text.toString()
            val price = binding.priceTextEdit.text.toString().toDoubleOrNull()
            val incomeExpenseType = binding.selectedIncomeExpenseType
            val saveType = binding.selectedSaveType

            if (title.isNullOrEmpty()
                    .not() && price != null && incomeExpenseType != null && saveType.isNullOrEmpty()
                    .not()
            ) {

                if (budget.title.isNullOrEmpty() && budget.docId.isNullOrEmpty()) {
                    val incomeExpense = IncomeExpense( Random.nextInt(0, 999999999).toString(), title, price, incomeExpenseType, saveType)
                    firebaseSave(incomeExpense)
                }else{
                    val incomeExpense = IncomeExpense( budget.docId, title, price, incomeExpenseType, saveType)
                    firebaseSave(incomeExpense)
                }


            } else {
                Toast.makeText(requireContext(), "Fill in the blanks!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(requireContext(), "Fill in the blanks!", Toast.LENGTH_SHORT).show()
        }


    }


    fun firebaseSave(incomeExpense: IncomeExpense) {



        FirebaseUtils.db.collection("users").document(FirebaseUtils.auth.currentUser!!.email!!).collection("income_expense").document( incomeExpense.docId!!)
            .set(incomeExpense)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Succesfully saved.", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }


    }

    override fun onCheckedChanged(p0: RadioGroup?, i: Int) {

        binding.isEnableSpinner = true
        binding.act.setText("")
        val selectedRadioButton: RadioButton = binding.root.findViewById(i)

        when (selectedRadioButton.text.toString()) {
            "Expense" -> {
                binding.selectedIncomeExpenseType = false
                setAdapterToSpinner(Content.EXPENSE)
            }

            "Income" -> {
                binding.selectedIncomeExpenseType = true
                setAdapterToSpinner(Content.INCOME)
            }
        }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        with(binding) {

            when (spinnerList[position]) {
                IncomeExpenseContent.SALARY.text ->
                    selectedSaveType = IncomeExpenseContent.SALARY.text

                IncomeExpenseContent.RENT.text ->
                    selectedSaveType = IncomeExpenseContent.RENT.text

                IncomeExpenseContent.SOCIAL_TRANSFER.text ->
                    selectedSaveType = IncomeExpenseContent.SOCIAL_TRANSFER.text

                IncomeExpenseContent.SHOPPING.text ->
                    selectedSaveType = IncomeExpenseContent.SHOPPING.text

                IncomeExpenseContent.ACCOMODATION.text ->
                    selectedSaveType = IncomeExpenseContent.ACCOMODATION.text

                IncomeExpenseContent.BILLS.text ->
                    selectedSaveType = IncomeExpenseContent.BILLS.text


            }
        }
    }

}