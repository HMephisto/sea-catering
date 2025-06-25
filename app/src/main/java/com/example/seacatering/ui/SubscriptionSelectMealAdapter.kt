package com.example.seacatering.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seacatering.databinding.ItemMenuSimpleBinding
import com.example.seacatering.domain.model.Menu

class SubscriptionSelectMealAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<SubscriptionSelectMealAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(item: Menu)
    }

    inner class ViewHolder(private val binding: ItemMenuSimpleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: Menu) {
            binding.apply {
                itemCard.setOnClickListener { listener.onItemClick(item) }

                planName.text = item.name
                planPrice.text = "Rp. " + item.price.toString()
                val valuesThumbnail = item.image_url
                val thumbnail = menuImage
                Glide.with(thumbnail)
                    .load(valuesThumbnail)
                    .into(thumbnail)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Menu>() {
        override fun areItemsTheSame(oldItem: Menu, newItem: Menu) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Menu, newItem: Menu) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var items: List<Menu>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(ItemMenuSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.setData(items[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int = items.size
}