package com.example.seacatering.ui.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.seacatering.databinding.FragmentMenuBinding
import com.example.seacatering.domain.model.Menu
import com.example.seacatering.domain.model.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MenuFragment : Fragment(), MenuAdapter.OnItemClickListener {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by viewModels()
    private val adapter: MenuAdapter = MenuAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListView()
        setupRequest()
        setupObserver()
        swipeRefresh()
    }

    private fun initListView() {
        binding.menuRecycler.adapter = adapter
    }

    private fun setupRequest() {
        viewModel.getAllMenu()
    }

    private fun swipeRefresh() {
        binding.swiperRefreshMenu.setOnRefreshListener {
            setupRequest()
            binding.swiperRefreshMenu.isRefreshing = true
        }
    }

    private fun setupObserver() {
        viewModel.getAllMenuState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.SuccessWithData<*> -> {
                    Log.e("done", "done")
                    val menus = result.data as List<Menu>
                    Log.e("done", menus.toString())
                    adapter.items = menus
                    binding.swiperRefreshMenu.isRefreshing = false
                }

                is Status.Failure -> {
                    Log.e("Failure", result.message)
                }

                is Status.Success -> {
                    Log.e("success", "load")
                }

                is Status.Loading -> {
                    Log.e("LOADIN", "load")
                }

                else -> {}
            }
        }
    }

    override fun onItemClick(item: Menu) {
        val intent = Intent(this.context, MenuDetailActivity::class.java)
        intent.putExtra("id", item.id)
        startActivity(intent)    }
}