package com.example.seacatering.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.seacatering.databinding.ItemSubscriptionAdminBinding
import com.example.seacatering.domain.model.SubscriptionWithMenuAndUser
import java.text.NumberFormat
import java.util.Locale

class AdminSubscriptionAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<AdminSubscriptionAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(item: SubscriptionWithMenuAndUser)
    }

    inner class ViewHolder(private val binding: ItemSubscriptionAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: SubscriptionWithMenuAndUser) {
            binding.apply {
                itemFrame.setOnClickListener { listener.onItemClick(item) }

                userName.text = item.user.name
                mealName.text = item.menu.name
                totalPrice.text = formatToIDR(item.subscription.total_price)
            }
        }
    }

    fun formatToIDR(amount: Double): String {
        val localeID = Locale("in", "ID")
        val formatter = NumberFormat.getCurrencyInstance(localeID)
        return formatter.format(amount)
    }

    private val differCallback = object : DiffUtil.ItemCallback<SubscriptionWithMenuAndUser>() {
        override fun areItemsTheSame(oldItem: SubscriptionWithMenuAndUser, newItem: SubscriptionWithMenuAndUser) = oldItem.subscription.id == newItem.subscription.id
        override fun areContentsTheSame(oldItem: SubscriptionWithMenuAndUser, newItem: SubscriptionWithMenuAndUser) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var items: List<SubscriptionWithMenuAndUser>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(ItemSubscriptionAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.setData(items[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int = items.size

}