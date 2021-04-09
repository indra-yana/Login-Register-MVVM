package com.indrayana.vp2advanced.adapter

import android.view.View

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On 08/04/2020 11.25
 ****************************************************/
interface OnItemClickListener {
    fun onItemClick(view: View?, position: Int)
    fun onItemLongClick(view: View?, position: Int)
    fun onButtonItemClick(view: View?, position: Int)
    fun onButtonShareItemClick(view: View?, position: Int)
    fun onButtonNotificationItemClick(view: View?, position: Int)
}