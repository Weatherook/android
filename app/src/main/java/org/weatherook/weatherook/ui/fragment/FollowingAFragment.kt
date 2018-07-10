package org.weatherook.weatherook.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_following_a.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.CommentAdapter
import org.weatherook.weatherook.adapter.recyclerview.FollowingAdapter
import org.weatherook.weatherook.item.CommentItem
import org.weatherook.weatherook.item.FollowingItem

class FollowingAFragment : Fragment(), View.OnClickListener{
    override fun onClick(v: View?) {

        when(v) {

            following_recent_btn -> {

                following_recent_btn.setTextColor(resources.getColor(R.color.colorAccent))
                following_popularity_btn.setTextColor(Color.parseColor("#aaaaaa"))
                followingItems.clear()

                followingItems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart, "112", R.drawable.main_night_2, "7월 25일", "맑음", "25/31", "정빈이는 체고다 정비니 짱짱", commentItems1))
                followingItems.add(FollowingItem(R.drawable.brown, "프린스 빈", R.drawable.heart, "112", R.drawable.main_rain_2, "7월 26일", "흐림", "24/31", "정빈이는 체고다 정비니 짱짱", commentItems2))
                followingItems.add(FollowingItem(R.drawable.brown, "정시후", R.drawable.heart, "112", R.drawable.main_snow_2, "7월 27일", "맑음", "25/31", "정빈이는 체고다 정비니 짱짱", commentItems1))
                followingItems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart, "112", R.drawable.main_cloud_sun_2, "7월 2일", "맑음", "27/31", "정빈이는 체고다 정비니 짱짱", commentItems2))
                onResume()

            }

            following_popularity_btn -> {
                following_recent_btn.setTextColor(Color.parseColor("#aaaaaa"))
                following_popularity_btn.setTextColor(resources.getColor(R.color.colorAccent))
                followingItems.clear()
                followingItems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart, "112", R.drawable.main_sun, "7월 25일", "맑음", "25/31", "정빈이는 체고다 정비니 짱짱", commentItems1))
                followingItems.add(FollowingItem(R.drawable.brown, "프린스 빈", R.drawable.heart, "112", R.drawable.main_rain_2, "7월 26일", "흐림", "24/31", "정빈이는 체고다 정비니 짱짱", commentItems2))
                followingItems.add(FollowingItem(R.drawable.brown, "정시후", R.drawable.heart, "112", R.drawable.main_sun, "7월 27일", "맑음", "25/31", "정빈이는 체고다 정비니 짱짱", commentItems1))
                followingItems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart, "112", R.drawable.main_cloud_sun_2, "7월 2일", "맑음", "27/31", "정빈이는 체고다 정비니 짱짱", commentItems2))
                onResume()

            }
        }

    }

    lateinit var commentItems1 : ArrayList<CommentItem>
    lateinit var commentItems2 : ArrayList<CommentItem>
    lateinit var commentAdapter : CommentAdapter
    lateinit var commentLinearLayoutManager: LinearLayoutManager
    lateinit var followingItems : ArrayList<FollowingItem>
    lateinit var followingAdapter : FollowingAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_following_a, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()

        following_recent_btn.setOnClickListener(this)
        following_popularity_btn.setOnClickListener(this)

        commentItems1 = ArrayList()
        commentItems2 = ArrayList()
        followingItems = ArrayList()

        commentItems1.add(CommentItem("kim","hihihi"))
        commentItems1.add(CommentItem("bin","i am bin"))
        commentItems1.add(CommentItem("young","hihihi"))

        commentItems2.add(CommentItem("young","hihihi"))
        commentItems2.add(CommentItem("kim","wow"))
        commentItems2.add(CommentItem("kim","zzzzz"))

        followingItems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart, "112", R.drawable.main_night_2, "7월 25일", "맑음", "25/31", "정빈이는 체고다 정비니 짱짱", commentItems1))
        followingItems.add(FollowingItem(R.drawable.brown, "프린스 빈", R.drawable.heart, "112", R.drawable.main_rain_2, "7월 26일", "흐림", "24/31", "정빈이는 체고다 정비니 짱짱", commentItems2))
        followingItems.add(FollowingItem(R.drawable.brown, "정시후", R.drawable.heart, "112", R.drawable.main_snow_2, "7월 27일", "맑음", "25/31", "정빈이는 체고다 정비니 짱짱", commentItems1))
        followingItems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart, "112", R.drawable.main_cloud_sun_2, "7월 2일", "맑음", "27/31", "정빈이는 체고다 정비니 짱짱", commentItems2))

        commentLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        followingAdapter = FollowingAdapter(followingItems, commentLinearLayoutManager, context!!)
        //      followingAdapter.setOnItemClickListener(this)
        home_following_recycler.layoutManager = LinearLayoutManager(context)
        home_following_recycler.adapter = followingAdapter



    }

    override fun onResume() {
        super.onResume()
        followingAdapter.notifyDataSetChanged()
    }
}