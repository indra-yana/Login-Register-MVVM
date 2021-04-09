package com.training.loginmvvm.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.indrayana.vp2advanced.adapter.OnItemClickListenerImpl
import com.training.loginmvvm.models.BannerItem

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On 05/04/2020 16.30
 ****************************************************/
class CardAdapter(
    private var itemList: List<BannerItem>,
    private var viewType: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClickListener: OnItemClickListenerImpl? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BannerViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BannerViewHolder).apply {
            bindItem(itemList[position], onItemClickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}