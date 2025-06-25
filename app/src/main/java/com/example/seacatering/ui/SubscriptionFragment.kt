package com.example.seacatering.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.seacatering.R
import com.example.seacatering.databinding.FragmentSubcscriptionBinding
import com.example.seacatering.domain.model.Menu
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Subscription
import com.example.seacatering.ui.condition.BottomSheetFragment
import com.google.android.material.checkbox.MaterialCheckBox
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SubscriptionFragment : Fragment(), SubscriptionSelectMealAdapter.OnItemClickListener {

    private var _binding: FragmentSubcscriptionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SubscriptionViewModel by viewModels()
    private val adapter: SubscriptionSelectMealAdapter = SubscriptionSelectMealAdapter(this)
    private val modalBottomSheet = BottomSheetFragment()

    private var selectedMenu: String = ""
    private var breakfastMealType: String = ""
    private var lunchMealType: String = ""
    private var dinnerMealType: String = ""

    private var mondayDeliveryDay: String = ""
    private var tuesdayDeliveryDay: String = ""
    private var wednesdayDeliveryDay: String = ""
    private var thursdayDeliveryDay: String = ""
    private var fridayDeliveryDay: String = ""
    private var saturdayDeliveryDay: String = ""
    private var sundayDeliveryDay: String = ""



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubcscriptionBinding.inflate(inflater, container, false)
        val view =  binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectedMealPlan.visibility = View.INVISIBLE

        textWatcherName()
        textWatcherPhone()
        checkingMealType()
        checkingDeliveryDays()
        setupForm()
        onSelectMealButton()
    }

    private fun onSelectMealButton(){
        binding.selectMealButton.setOnClickListener{
            setupChooseMeal()
            setupObserver()
        }
    }

    private fun setupForm(){
        binding.checkoutButton.setOnClickListener{
            if (!checkingValidate()) {
                validateForm()
            } else {
                setupCreateSubscriptionRequest()
            }
        }
    }

    private fun setupCreateSubscriptionRequest(){
        var mealType = breakfastMealType + lunchMealType + dinnerMealType
        var deliveryDays = mondayDeliveryDay + tuesdayDeliveryDay + wednesdayDeliveryDay + thursdayDeliveryDay + fridayDeliveryDay + saturdayDeliveryDay + sundayDeliveryDay
        lifecycleScope.launch {
            viewModel.userId.collect { id ->
                var subscription = Subscription(
                    id + selectedMenu,
                    id.toString(),
                    selectedMenu,
                    binding.subscriptionInputName.text.toString(),
                    binding.subscriptionInputNumber.text.toString(),
                    mealType,
                    deliveryDays,
                    binding.subscriptionInputAllergies.text.toString(),
                    "Active",
                )
                viewModel.createSubscription(subscription)
            }

        }
    }

    private fun setupObserver(){
        viewModel.createSubscriptionState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {}
                is Status.Success -> {
                    Toast.makeText(requireContext(), "Subscription Created", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun checkingValidate(): Boolean {
        return !(!validateName() || !validatePhone() || !validateSelectedMeal() ||
                !validateMealType() || !validateDeliveryDays())
    }

    private fun validateForm() {
        validateName()
        validatePhone()
        validateSelectedMeal()
        validateMealType()
        validateDeliveryDays()
    }

    private fun validateName(): Boolean{
        return if (binding.subscriptionInputName.length() == 0) {
            errorNullFullName()
            false
        } else {
            clearFullName()
            true
        }
    }

    private fun validatePhone(): Boolean{
        return if (binding.subscriptionInputNumber.length() == 0) {
            errorNullPhone()
            false
        } else {
            clearPhone()
            true
        }
    }

    private fun validateMealType(): Boolean{
        return if (breakfastMealType == "" && lunchMealType == "" && dinnerMealType == "") {
            errorMealType()
            false
        } else {
            true
        }
    }

    private fun validateDeliveryDays(): Boolean{
        return if (mondayDeliveryDay == "" && tuesdayDeliveryDay == "" && wednesdayDeliveryDay == ""
            && thursdayDeliveryDay == "" && fridayDeliveryDay == "" && saturdayDeliveryDay == ""
            && sundayDeliveryDay == "") {
            errorDeliveryDays()
            false
        } else {
            true
        }
    }

    private fun checkingMealType(){
        binding.mealTypeBreakfast.addOnCheckedStateChangedListener{ checkBox, state ->
            when (state) {
                MaterialCheckBox.STATE_CHECKED -> {
                    breakfastMealType = "Breakfast"
                }
                MaterialCheckBox.STATE_UNCHECKED -> {
                    breakfastMealType = ""
                }
            }
        }
        binding.mealTypeLunch.addOnCheckedStateChangedListener{ checkBox, state ->
            when (state) {
                MaterialCheckBox.STATE_CHECKED -> {
                    lunchMealType = "Lunch"
                }
                MaterialCheckBox.STATE_UNCHECKED -> {
                    lunchMealType = ""
                }
            }
        }
        binding.mealTypeDinner.addOnCheckedStateChangedListener{ checkBox, state ->
            when (state) {
                MaterialCheckBox.STATE_CHECKED -> {
                    dinnerMealType = "Dinner"
                }
                MaterialCheckBox.STATE_UNCHECKED -> {
                    dinnerMealType = ""
                }
            }
        }
    }

    private fun checkingDeliveryDays(){
        binding.deliveryMonday.addOnCheckedStateChangedListener{ checkBox, state ->
            when (state) {
                MaterialCheckBox.STATE_CHECKED -> {
                    mondayDeliveryDay = "1"
                }
                MaterialCheckBox.STATE_UNCHECKED -> {
                    mondayDeliveryDay = ""
                }
            }
        }
        binding.deliveryTuesday.addOnCheckedStateChangedListener{ checkBox, state ->
            when (state) {
                MaterialCheckBox.STATE_CHECKED -> {
                    tuesdayDeliveryDay = "2"
                }
                MaterialCheckBox.STATE_UNCHECKED -> {
                    tuesdayDeliveryDay = ""
                }
            }
        }
        binding.deliveryWednesday.addOnCheckedStateChangedListener{ checkBox, state ->
            when (state) {
                MaterialCheckBox.STATE_CHECKED -> {
                    wednesdayDeliveryDay = "3"
                }
                MaterialCheckBox.STATE_UNCHECKED -> {
                    wednesdayDeliveryDay = ""
                }
            }
        }

        binding.deliveryThursday.addOnCheckedStateChangedListener{ checkBox, state ->
            when (state) {
                MaterialCheckBox.STATE_CHECKED -> {
                    thursdayDeliveryDay = "4"
                }
                MaterialCheckBox.STATE_UNCHECKED -> {
                    thursdayDeliveryDay = ""
                }
            }
        }
        binding.deliveryFriday.addOnCheckedStateChangedListener{ checkBox, state ->
            when (state) {
                MaterialCheckBox.STATE_CHECKED -> {
                    fridayDeliveryDay = "5"
                }
                MaterialCheckBox.STATE_UNCHECKED -> {
                    fridayDeliveryDay = ""
                }
            }
        }
        binding.deliverySaturday.addOnCheckedStateChangedListener{ checkBox, state ->
            when (state) {
                MaterialCheckBox.STATE_CHECKED -> {
                    saturdayDeliveryDay = "6"
                }
                MaterialCheckBox.STATE_UNCHECKED -> {
                    saturdayDeliveryDay = ""
                }
            }
        }
        binding.deliverySunday.addOnCheckedStateChangedListener{ checkBox, state ->
            when (state) {
                MaterialCheckBox.STATE_CHECKED -> {
                    sundayDeliveryDay = "7"
                }
                MaterialCheckBox.STATE_UNCHECKED -> {
                    sundayDeliveryDay = ""
                }
            }
        }
    }

    private fun validateSelectedMeal(): Boolean{
        return if (selectedMenu == "") {
            errorSelectedMeal()
            false
        } else {
            true
        }
    }

    private fun textWatcherName() {
        binding.subscriptionInputName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                validateName()
            }

        })
    }

    private fun textWatcherPhone() {
        binding.subscriptionInputNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                validatePhone()
            }

        })
    }

    private fun errorNullFullName() {
        binding.subscriptionName.error = "* Nama harus diisi"
        errorBorderName()
    }

    private fun clearFullName() {
        binding.subscriptionName.isErrorEnabled = false
        defaultBorderName()
    }

    private fun errorNullPhone() {
        binding.subscriptionNumber.error = "* No Telp harus diisi"
        errorBorderPhone()
    }

    private fun clearPhone() {
        binding.subscriptionNumber.isErrorEnabled = false
        defaultBorderPhone()
    }

    private fun errorSelectedMeal() {
        Toast.makeText(requireContext(), "Please Select a meal!!", Toast.LENGTH_SHORT).show()
    }
    private fun errorMealType() {
        Toast.makeText(requireContext(), "Please Select a meal type!!", Toast.LENGTH_SHORT).show()
    }

    private fun errorDeliveryDays() {
        Toast.makeText(requireContext(), "Please Select a delivery days!!", Toast.LENGTH_SHORT).show()
    }

    private fun defaultBorderName() {
        binding.subscriptionInputName.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun errorBorderName() {
        binding.subscriptionInputName.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun defaultBorderPhone() {
        binding.subscriptionInputNumber.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun errorBorderPhone() {
        binding.subscriptionInputNumber.setBackgroundResource(R.drawable.bg_white_red_outline)
    }



    private fun setupChooseMeal() {

        modalBottomSheet.show(childFragmentManager, BottomSheetFragment.TAG)

        viewModel.getAllMenu()
        viewModel.getAllMenuState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.SuccessWithData<*> -> {
                    Log.e("done", "done")
                    val menus = result.data as List<Menu>
                    Log.e("done", menus.toString())
                    initListView()
                    adapter.items = menus
                }

                is Status.Failure -> {
                    Log.e("Failure", result.message)
                }

                is Status.Loading -> {
                    Log.e("LOADIN", "load")
                }

                else -> {}
            }
        }
    }

    private fun initListView() {
        modalBottomSheet.setAdapter(adapter)
    }

    override fun onItemClick(item: Menu) {
        binding.selectedMealPlan.visibility = View.VISIBLE
        binding.noMealText.visibility = View.GONE
        val valuesThumbnail = item.image_url
        val thumbnail = binding.mealImage
        Glide.with(thumbnail)
            .load(valuesThumbnail)
            .into(thumbnail)
        binding.planName.text = item.name
        binding.planPrice.text = "Rp. " + item.price.toString()

        selectedMenu = item.id

        modalBottomSheet.dismiss()
    }
}