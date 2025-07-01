package com.example.seacatering.ui.menu

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.seacatering.MainActivity
import com.example.seacatering.R
import com.example.seacatering.domain.model.Menu
import com.example.seacatering.domain.model.Status
import com.example.seacatering.ui.condition.BottomSheetFragment
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class MenuDetailActivity : AppCompatActivity() {
    var menuId: String = ""
    lateinit var cover: ImageView
    lateinit var title: TextView
    lateinit var price: TextView
    lateinit var description: TextView
    lateinit var toolbar: MaterialToolbar

    private val viewModel: MenuDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_detail)

        menuId = intent.getStringExtra("id").toString()

        initContainer()
        setupRequest()
        setupObserver()
        onBackClick()



    }

    private fun initContainer() {
        cover = findViewById(R.id.itemCover)
        title = findViewById(R.id.itemTitle)
        price = findViewById(R.id.itemPrice)
        description = findViewById(R.id.itemDesc)
        toolbar = findViewById(R.id.topAppBar)
    }

    private fun setupRequest() {
        viewModel.getMenuDetail(menuId)
    }

    private fun setupObserver() {
        viewModel.getMenuDetailState.observe(this@MenuDetailActivity) { result ->
            when (result) {
                is Status.SuccessWithData<*> -> {
                    val menu = result.data as Menu
                    val valuesCover = menu.image_url

                    Glide.with(cover)
                        .load(valuesCover)
                        .into(cover)

                    title.text = menu.name
                    price.text = formatToIDR(menu.price)
                    description.text = menu.description
                }

                else -> {}
            }

        }
    }

    fun formatToIDR(amount: Double): String {
        val localeID = Locale("in", "ID")
        val formatter = NumberFormat.getCurrencyInstance(localeID)
        return formatter.format(amount)
    }

    private fun onBackClick() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }


}