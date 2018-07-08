package org.weatherook.weatherook.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_following_a.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.FollowingAdapter
import org.weatherook.weatherook.item.FollowingItem

class FollowingBFragment : Fragment() {


    lateinit var followingItems : ArrayList<FollowingItem>
    lateinit var followingAdapter : FollowingAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_following_b, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()

        followingItems = ArrayList()

        followingItems.add(FollowingItem(R.drawable.brown, "히리요", R.drawable.heart,"112",R.drawable.main_night_2,"7월 25일","맑음","25/31","정빈이는 체고다 정비니 짱짱"))
        followingItems.add(FollowingItem(R.drawable.brown, "프린스 빈", R.drawable.heart,"112",R.drawable.main_rain_2,"7월 26일","흐림","24/31","정빈이는 체고다 정비니 짱짱"))
        followingItems.add(FollowingItem(R.drawable.brown, "정시후", R.drawable.heart,"112",R.drawable.main_snow_2,"7월 27일","맑음","25/31","정빈이는 체고다 정비니 짱짱"))
        followingItems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart,"112",R.drawable.main_cloud_sun_2,"7월 2일","맑음","27/31","정빈이는 체고다 정비니 짱짱"))


        followingAdapter = FollowingAdapter(followingItems,context!!)
        //      followingAdapter.setOnItemClickListener(this)
        home_following_recycler.layoutManager = LinearLayoutManager(context)
        home_following_recycler.adapter = followingAdapter



    }

}