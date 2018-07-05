package org.weatherook.weatherook.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.LikeRecyclerViewData
import org.weatherook.weatherook.adapter.LikeRecyclerviewAdapter

class LikedFragment :  Fragment(), View.OnClickListener{
    override fun onClick(p0: View?) {

    }

    var likeitems : ArrayList<LikeRecyclerViewData> = ArrayList()
    lateinit var likeRecyclerviewAdapter : LikeRecyclerviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = View.inflate(activity, R.layout.fragment_liked, null)
        val like_recycle : RecyclerView = view!!.findViewById(R.id.like_recycle)

        likeitems.add(LikeRecyclerViewData(R.drawable.main_night_2, "유클라", "님이 댓글을 남겼습니다.", R.drawable.brown))
        likeitems.add(LikeRecyclerViewData(R.drawable.main_cloud_sun_2, "원클라", "님이 댓글을 남겼습니다.", R.drawable.brown))
        likeitems.add(LikeRecyclerViewData(R.drawable.main_rain_2, "김클라", "님이 댓글을 남겼습니다.", R.drawable.brown))
        likeitems.add(LikeRecyclerViewData(R.drawable.main_snow_2, "김웹클라", "님이 댓글을 남겼습니다.", R.drawable.brown))



        likeRecyclerviewAdapter = LikeRecyclerviewAdapter(likeitems, context!!)
        likeRecyclerviewAdapter.setOnItemClickListener(this)
        like_recycle.adapter = likeRecyclerviewAdapter
        like_recycle.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onStart() {
        super.onStart()


    }
}