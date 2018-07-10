package org.weatherook.weatherook.adapter.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.weatherook.weatherook.R
import org.weatherook.weatherook.api.glide.GlideApp
import org.weatherook.weatherook.item.FriendItem
import org.weatherook.weatherook.viewholder.FriendViewHolder

/**
 * Created by HYEON on 2018-07-08.
 */
class FriendAdapter (var friendItems : ArrayList<FriendItem>, val context : Context) : RecyclerView.Adapter<FriendViewHolder>() {

    private lateinit var onItemClick: View.OnClickListener

    fun setOnItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val mainView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_friend,parent,false)
//        mainView.setOnClickListener(onItemClick)
        return FriendViewHolder(mainView)
    }

    override fun getItemCount(): Int = friendItems.size

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        GlideApp.with(context).load(friendItems[position].profile).into(holder!!.friendProfile)
        holder!!.friendId.text = friendItems[position].id
    }
}