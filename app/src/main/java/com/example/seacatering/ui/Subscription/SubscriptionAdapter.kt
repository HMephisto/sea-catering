package com.example.seacatering.ui.Subscription

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seacatering.databinding.ItemSubscriptionBinding
import com.example.seacatering.domain.model.SubscriptionWithMenu
import com.example.seacatering.R
import java.text.NumberFormat
import java.util.Locale


class SubscriptionAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onToogleClick(item: SubscriptionWithMenu)
        fun onCancelClick(item: SubscriptionWithMenu)
    }

    inner class ViewHolder(private val binding: ItemSubscriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: SubscriptionWithMenu) {
            binding.apply {
                    tooglePause.setOnClickListener { listener.onToogleClick(item) }
                    cancelButton.setOnClickListener { listener.onCancelClick(item) }

                    if (item.subscription.status.uppercase() == ("ACTIVE")) {
                        tooglePause.text = "Pause"
                        tooglePause.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(tooglePause.context, R.color.grey))
                    } else {
                        tooglePause.text = "Start"
                        tooglePause.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(tooglePause.context, R.color.green))
                    }

                    menuName.text = item.menu.name
                    subStatus.text = item.subscription.status
                    mealPrice.text = "Total Price : " + formatToIDR(item.subscription.total_price)
                    mealType.text = "Meal Type : " + formatMeals(item.subscription.meal_type)
                    val valuesThumbnail = item.menu.image_url
                    val thumbnail = menuImage
                    Glide.with(thumbnail)
                        .load(valuesThumbnail)
                        .into(thumbnail)
                    deliveryDays.text = formatDays(item.subscription.delivery_days)

            }
        }
    }

    fun formatMeals(input: String): String {
        val meals = listOf("Breakfast", "Lunch", "Dinner")
        val foundMeals = meals.filter { input.contains(it) }
        return foundMeals.joinToString(", ")
    }

    fun formatToIDR(amount: Double): String {
        val localeID = Locale("in", "ID")
        val formatter = NumberFormat.getCurrencyInstance(localeID)
        return formatter.format(amount)
    }

    fun formatDays(input: String): String {
        val daysMap = mapOf(
            '1' to "Monday",
            '2' to "Tuesday",
            '3' to "Wednesday",
            '4' to "Thursday",
            '5' to "Friday",
            '6' to "Saturday",
            '7' to "Sunday"
        )

        val dayList = input.mapNotNull { daysMap[it] }
        return dayList.joinToString(", ")
    }

    private val differCallback = object : DiffUtil.ItemCallback<SubscriptionWithMenu>() {
        override fun areItemsTheSame(oldItem: SubscriptionWithMenu, newItem: SubscriptionWithMenu) =
            oldItem.subscription.id == newItem.subscription.id

        override fun areContentsTheSame(
            oldItem: SubscriptionWithMenu,
            newItem: SubscriptionWithMenu
        ) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var items: List<SubscriptionWithMenu>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        ItemSubscriptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.setData(items[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int = items.size
}