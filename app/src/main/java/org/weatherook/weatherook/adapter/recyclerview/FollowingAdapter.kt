package org.weatherook.weatherook.adapter.recyclerview

import android.content.Context
import android.content.Intent
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_following.view.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.api.glide.GlideApp
import org.weatherook.weatherook.item.CommentItem
import org.weatherook.weatherook.item.FollowingItem
import org.weatherook.weatherook.viewholder.FollowingViewHolder
import org.weatherook.weatherook.ui.activity.CommentActivity
import org.weatherook.weatherook.utils.KeyboardVisibility


class FollowingAdapter(var followingItems : ArrayList<FollowingItem>, var commentLayoutManager: LinearLayoutManager, val context : Context) : RecyclerView.Adapter<FollowingViewHolder>() {

    private lateinit var onItemClick: View.OnClickListener

    lateinit var commentItems: ArrayList<CommentItem>
    lateinit var commentAdapter: CommentAdapter
    fun setOnItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val mainView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_following,parent,false)
//        mainView.setOnClickListener(onItemClick)

        mainView.following_popup.setOnClickListener {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet, null)
            val dialog = BottomSheetDialog(context!!)
            dialog.setContentView(view)
            dialog.show()
        }
        mainView.following_commentwrite_btn.setOnClickListener{
            mainView.following_comment_visible.visibility = View.VISIBLE
            mainView.following_comment_write.requestFocus()
            KeyboardVisibility.showKeyboard(context)

        }

        mainView.following_commentshow_btn.setOnClickListener {
            val intent1 = Intent(context, CommentActivity::class.java)
            mainView.context.startActivity(intent1)
        }
        return FollowingViewHolder(mainView)
    }

    override fun getItemCount(): Int = followingItems.size

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        GlideApp.with(context).load(followingItems[position].profile).into(holder!!.followingProfile)
        holder!!.followingId.text = followingItems[position].id
        GlideApp.with(context).load(followingItems[position].heart).into(holder!!.followingHeart)
        holder!!.followingCount.text = followingItems[position].count
        GlideApp.with(context).load(followingItems[position].photo).into(holder!!.followingPhoto)
        holder!!.followingDate.text = followingItems[position].date
        holder!!.followingWeather.text = followingItems[position].weather
        holder!!.followingTemp.text = followingItems[position].temperature
        holder!!.followingContent.text = followingItems[position].content
        holder!!.followingComment.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder!!.followingComment.adapter = CommentAdapter(followingItems[position].comment)


    }
}