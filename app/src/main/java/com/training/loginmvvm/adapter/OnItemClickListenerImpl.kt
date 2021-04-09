package com.indrayana.vp2advanced.adapter

import android.view.View

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On 08/04/2020 12.39
 ****************************************************/
abstract class OnItemClickListenerImpl : OnItemClickListener {
    override fun onItemLongClick(view: View?, position: Int) { }
    override fun onItemClick(view: View?, position: Int) { }
    override fun onButtonItemClick(view: View?, position: Int) { }
    override fun onButtonShareItemClick(view: View?, position: Int) {}
    override fun onButtonNotificationItemClick(view: View?, position: Int) {}
}