package org.weatherook.weatherook.adapter.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.weatherook.weatherook.R
import org.weatherook.weatherook.api.glide.GlideApp
import org.weatherook.weatherook.item.BellRecyclerViewData
import org.weatherook.weatherook.viewholder.BellRecyclerViewHolder

/**
 * Created by HYEON on 2018-07-05.
 */
class BellRecyclerviewAdapter(private var bellrecyclerviewItems : ArrayList<BellRecyclerViewData>, val context : Context) : RecyclerView.Adapter<BellRecyclerViewHolder>() {
    lateinit var onItemClick : View.OnClickListener

    fun setOnItemClickListener(I:View.OnClickListener) {
        onItemClick = I
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BellRecyclerViewHolder {
        val mainView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_bell, parent, false)
        mainView.setOnClickListener(onItemClick)
        return BellRecyclerViewHolder(mainView)
    }

    override fun getItemCount(): Int = bellrecyclerviewItems.size

    override fun onBindViewHolder(holder: BellRecyclerViewHolder, position: Int) {
        GlideApp.with(context).load(bellrecyclerviewItems[position].bellprofile).into(holder!!.bellProfile)
        holder!!.bellName.text = bellrecyclerviewItems[position].bellname
        holder!!.bellRingTxt.text = bellrecyclerviewItems[position].bellringtxt
        holder!!.bellReply.text = bellrecyclerviewItems[position].bellreply
        holder!!.bellTime.text = bellrecyclerviewItems[position].belltime
        GlideApp.with(context).load(bellrecyclerviewItems[position].bellboardimg).into(holder!!.bellBoardImg)
    }
}