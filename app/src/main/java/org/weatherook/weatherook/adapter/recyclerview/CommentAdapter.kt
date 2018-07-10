package org.weatherook.weatherook.adapter.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.weatherook.weatherook.R
import org.weatherook.weatherook.item.CommentItem
import org.weatherook.weatherook.viewholder.CommentViewHolder

class CommentAdapter(var commentItems : ArrayList<CommentItem>?) : RecyclerView.Adapter<CommentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val mainView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_comment,parent,false)

        return CommentViewHolder(mainView)
    }

    override fun getItemCount(): Int = commentItems!!.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder!!.commentId.text = commentItems!![position].id
        holder!!.commentComment.text = commentItems!![position].comment
    }
}