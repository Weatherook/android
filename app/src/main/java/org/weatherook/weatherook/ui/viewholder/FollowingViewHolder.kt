package org.weatherook.weatherook.ui.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.weatherook.weatherook.R

class FollowingViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
    var followingProfile : ImageView = itemView!!.findViewById(R.id.following_profile) as ImageView
    var followingId : TextView = itemView!!.findViewById(R.id.following_id) as TextView
    var followingPhoto : ImageView = itemView!!.findViewById(R.id.following_photo) as ImageView
}
