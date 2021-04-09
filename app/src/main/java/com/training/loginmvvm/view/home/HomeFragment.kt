package com.training.loginmvvm.view.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.indrayana.vp2advanced.adapter.OnItemClickListenerImpl
import com.training.loginmvvm.R
import com.training.loginmvvm.adapter.CardAdapter
import com.training.loginmvvm.databinding.FragmentHomeBinding
import com.training.loginmvvm.models.responses.ResponseStatus
import com.training.loginmvvm.datasources.remote.UserApi
import com.training.loginmvvm.models.BannerItem
import com.training.loginmvvm.repositories.UserRepository
import com.training.loginmvvm.models.User
import com.training.loginmvvm.utils.OverlapSliderTransformation
import com.training.loginmvvm.utils.cardItemDummyDataLarge
import com.training.loginmvvm.view.BaseFragment
import com.training.loginmvvm.viewmodel.HomeViewModel
import com.training.loginmvvm.utils.handleApiError
import com.training.loginmvvm.utils.visible
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {

    private lateinit var itemList: MutableList<BannerItem>

    private val sliderInterval: Long = 5000
    private val sliderHandler = Handler()
    private val sliderRunnable = Runnable {
        if (itemList.isNotEmpty()) {
            if (vp2_slider.currentItem + 1 == itemList.size) {
                vp2_slider.currentItem = 0
            } else {
                vp2_slider.currentItem = vp2_slider.currentItem + 1
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getUser()
        // updateUI(User(id = 1, name = "Agus", email = "agus@gmail.com", access_token = null, created_at = "12-04-2021", updated_at = "12-04-2021", email_verified_at = null, refresh_token = null))
        viewModel.user.observe(viewLifecycleOwner, Observer {
            viewBinding.pbLoading.visible(it is ResponseStatus.Loading)
            when(it) {
                is ResponseStatus.Success -> {
                     updateUI(it.value.user)
                }
                is ResponseStatus.Failure -> {
                    handleApiError(it, retry = { getUser() })
                }
            }
        })

        with(viewBinding) {
            btnLogout.setOnClickListener { logout() }
            layoutHeader.tvHeaderTitle.text = getString(R.string.text_home)
        }

    }

    private fun getUser() {
        viewModel.getUser()
    }

    private fun updateUI(user: User) {
        with(viewBinding) {
            tvID.text = user.id.toString()
            tvName.text = user.name
            tvEmail.text = user.email
        }
    }

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun getRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }

        return UserRepository(apiClient.buildApi(UserApi::class.java, token))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemList = cardItemDummyDataLarge(requireActivity(), 5)

        setupViewPager2()

        btn_view_all.setOnClickListener {
            Toast.makeText(context, "See all clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, sliderInterval)
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    private fun setupViewPager2() {
        val sliderAdapter = CardAdapter(itemList, 0).apply {
            onItemClickListener = object : OnItemClickListenerImpl() {
                override fun onItemClick(view: View?, position: Int) {
                    super.onItemClick(view, position)
                    showToast("Item clicked: ${itemList[position].title}")
                }

                override fun onItemLongClick(view: View?, position: Int) {
                    super.onItemLongClick(view, position)
                    showToast("Long click item: ${itemList[position].title}")
                }
            }
        }

        vp2_slider.apply {
            adapter = sliderAdapter
            offscreenPageLimit = 3
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

            val recyclerViewChild = children.firstOrNull() as RecyclerView
            recyclerViewChild.clipChildren = false
            recyclerViewChild.clipToPadding = false
            recyclerViewChild.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            recyclerViewChild.setPadding(50, 0, 50, 0)

            setPageTransformer(OverlapSliderTransformation(ViewPager2.ORIENTATION_HORIZONTAL))
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderHandler.removeCallbacks(sliderRunnable)
                    sliderHandler.postDelayed(sliderRunnable, sliderInterval)
                }
            })
        }

        dots_indicator.setViewPager2(vp2_slider)
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}