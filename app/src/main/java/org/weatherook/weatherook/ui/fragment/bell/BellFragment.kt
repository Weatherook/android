package org.weatherook.weatherook.ui.fragment.bell

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.BellRecyclerviewAdapter
import org.weatherook.weatherook.item.BellRecyclerViewData

class BellFragment :  Fragment(), View.OnClickListener{
    override fun onClick(p0: View?) {

    }

    var likeitems : ArrayList<BellRecyclerViewData> = ArrayList()
    lateinit var bellRecyclerviewAdapter : BellRecyclerviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = View.inflate(activity, R.layout.fragment_bell, null)
        val like_recycle : RecyclerView = view!!.findViewById(R.id.like_recycle)

        likeitems.add(BellRecyclerViewData(R.drawable.main_night_2, "유클라", "님이 댓글을 남겼습니다.", "오늘 너무 더워요오","14", R.drawable.brown))
        likeitems.add(BellRecyclerViewData(R.drawable.main_snow_2, "프린스정", "님이 댓글을 남겼습니다.", "오늘 너무 더워요오","15", R.drawable.brown))
        likeitems.add(BellRecyclerViewData(R.drawable.main_cloud_sun_2, "유클라", "님이 댓글을 남겼습니다.", "오늘 너무 더워요오","220", R.drawable.brown))

        bellRecyclerviewAdapter = BellRecyclerviewAdapter(likeitems, context!!)
        bellRecyclerviewAdapter.setOnItemClickListener(this)
        like_recycle.adapter = bellRecyclerviewAdapter
        like_recycle.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onStart() {
        super.onStart()


    }
}