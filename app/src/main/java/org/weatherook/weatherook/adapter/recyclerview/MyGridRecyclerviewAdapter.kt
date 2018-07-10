package org.weatherook.weatherook.adapter.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.weatherook.weatherook.R
import org.weatherook.weatherook.api.glide.GlideApp
import org.weatherook.weatherook.item.MyGridRecyclerviewdata
import org.weatherook.weatherook.viewholder.MyRecyclerviewViewHolder

/**
 * Created by HYEON on 2018-07-03.
 */

class MyGridRecyclerviewAdapter(private var myrecyclerviewItems: ArrayList<MyGridRecyclerviewdata>, val context: Context) : RecyclerView.Adapter<MyRecyclerviewViewHolder>() {

    lateinit var onItemClick: View.OnClickListener

    fun setOnItemClickListener(l : View.OnClickListener){
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerviewViewHolder {
        val mainView : View = LayoutInflater.from(parent.context).inflate(R.layout.my_item, parent, false)
        mainView.setOnClickListener(onItemClick)
        return MyRecyclerviewViewHolder(mainView)
    }

    override fun getItemCount(): Int = myrecyclerviewItems.size

    override fun onBindViewHolder(holder: MyRecyclerviewViewHolder, position: Int) {
       // holder!!.MyImage.setImageResource(myrecyclerviewItems[position].myimage)  Glide가 더 성능이 좋음
        GlideApp.with(context).load(myrecyclerviewItems[position].myimage).into(holder!!.MyImage)
    }
}