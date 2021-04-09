package com.training.loginmvvm.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.indrayana.vp2advanced.adapter.OnItemClickListenerImpl

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On 13/04/2020 09.46
 ****************************************************/
abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindItem(item: Any, onItemClickListener: OnItemClickListenerImpl?)

}