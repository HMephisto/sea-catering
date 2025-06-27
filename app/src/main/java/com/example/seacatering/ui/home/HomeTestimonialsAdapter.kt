package com.example.seacatering.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seacatering.databinding.ItemTestimonyBinding
import com.example.seacatering.domain.model.Testimony
import com.example.seacatering.R
class HomeTestimonialsAdapter  :
    RecyclerView.Adapter<HomeTestimonialsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemTestimonyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: Testimony) {
            binding.apply {

                userName.text = item.user_name
                review.text = item.testimony_message

                if (item.rating >= 1)
                    star1.setImageResource(R.drawable.ic_star_filled)
                if (item.rating >= 2)
                    star2.setImageResource(R.drawable.ic_star_filled)
                if (item.rating >= 3)
                    star3.setImageResource(R.drawable.ic_star_filled)
                if (item.rating >= 4)
                    star4.setImageResource(R.drawable.ic_star_filled)
                if (item.rating >= 5)
                    star5.setImageResource(R.drawable.ic_star_filled)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Testimony>() {
        override fun areItemsTheSame(oldItem: Testimony, newItem: Testimony) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Testimony, newItem: Testimony) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var items: List<Testimony>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(ItemTestimonyBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.setData(items[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int = items.size

}