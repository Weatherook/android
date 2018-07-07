package org.weatherook.weatherook.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.FollowingAdapter
import org.weatherook.weatherook.adapter.HomePagerAdapter
import org.weatherook.weatherook.adapter.RecommendAdapter
import org.weatherook.weatherook.item.FollowingItem
import org.weatherook.weatherook.item.RecommendItem

class HomeFragment : Fragment(), View.OnClickListener {


    override fun onClick(v: View?) {
/*

  */
        }

    lateinit var followingItems : ArrayList<FollowingItem>
    lateinit var followingAdapter : FollowingAdapter


    lateinit var recommendItems : ArrayList<RecommendItem>
    lateinit var recommendAdapter : RecommendAdapter

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = View.inflate(activity, R.layout.fragment_home, null)
        return view
    }

    override fun onStart() {
        super.onStart()
        val following_recycle: RecyclerView = view!!.findViewById(R.id.home_following_recycler)

        recommendItems = ArrayList()

        recommendItems.add(RecommendItem(R.drawable.heartcolor))
        recommendItems.add(RecommendItem(R.drawable.heartcolor))
        recommendItems.add(RecommendItem(R.drawable.heartcolor))
        recommendItems.add(RecommendItem(R.drawable.heartcolor))

        recommendAdapter = RecommendAdapter(recommendItems,context!!)
   //     recommendAdapter.setOnItemClickListener(this)
        home_recommend_recycler.layoutManager = GridLayoutManager(context,2)
        home_recommend_recycler.adapter = recommendAdapter

        followingItems = ArrayList()

        followingItems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart,"112",R.drawable.main_night_2,"7월 25일","맑음","25/31","정빈이는 체고다 정비니 짱짱"))
        followingItems.add(FollowingItem(R.drawable.brown, "프린스 빈", R.drawable.heart,"112",R.drawable.main_rain_2,"7월 26일","흐림","24/31","정빈이는 체고다 정비니 짱짱"))
        followingItems.add(FollowingItem(R.drawable.brown, "정시후", R.drawable.heart,"112",R.drawable.main_snow_2,"7월 27일","맑음","25/31","정빈이는 체고다 정비니 짱짱"))
        followingItems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart,"112",R.drawable.main_cloud_sun_2,"7월 2일","맑음","27/31","정빈이는 체고다 정비니 짱짱"))


        followingAdapter = FollowingAdapter(followingItems,context!!)
        // followingAdapter.setOnItemClickListener(this)
        following_recycle.layoutManager = LinearLayoutManager(activity)
        following_recycle.adapter = followingAdapter



        val viewPager = view!!.findViewById<ViewPager>(R.id.weather_viewPager)
        val adapter = HomePagerAdapter(childFragmentManager)

        viewPager.adapter = adapter
        viewPager.currentItem = 1
    }
}
