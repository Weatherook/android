package org.weatherook.weatherook.adapter.recyclerview

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView

import org.weatherook.weatherook.R
import org.weatherook.weatherook.item.RecommendItem
import org.weatherook.weatherook.ui.activity.SigninActivity
import org.weatherook.weatherook.viewholder.RecommendViewHolder
import android.view.*


class RecommendAdapter(var recommendItems : ArrayList<RecommendItem>, val context: Context) : RecyclerView.Adapter<RecommendViewHolder>() {

    private lateinit var onItemClick: View.OnClickListener

    fun setOnItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val mainView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_recommend,parent,false)
//        mainView.setOnClickListener(onItemClick)


        return RecommendViewHolder(mainView)
    }

    override fun getItemCount(): Int = recommendItems.size

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        holder.recommendCody.setImageResource(recommendItems[position].cody)
        val intent :Intent = Intent(context, SigninActivity::class.java)
        //intent.putExtra("url", recommendItems[position].cody)
    //   holder.recommendCody.setOnClickListener {
         //   context.startActivity(intent)
       // }
    }
}