package com.example.seacatering.ui.admin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.seacatering.R
import com.example.seacatering.domain.model.Status
import com.example.seacatering.ui.ProfileViewmodel
import com.example.seacatering.ui.auth.AuthActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var toolbar: MaterialToolbar

    private val viewModel: AdminViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)


        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        toolbar = findViewById(R.id.topAppBar)

        val fragments = listOf(AdminStatisticsFragment(), AdminSubscriptionFragment())
        val titles = listOf("Statistic", "Subscriptions")

        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = fragments.size
            override fun createFragment(position: Int) = fragments[position]
        }

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()

        onLogoutButton()
        setupObserver()
    }

    private fun setupObserver(){
        viewModel.logoutState.observe(this@AdminActivity) { result ->
            when (result) {
                is Status.Loading -> {}

                Status.Success -> {
                    viewModel.saveUserId("")
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }

                else -> {}
            }
        }
    }
    private fun onLogoutButton() {

        toolbar.setNavigationOnClickListener {
            viewModel.logout()

        }
    }
}