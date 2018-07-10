package org.weatherook.weatherook.ui.fragment.my

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.MyListRecyclerviewAdapter
import org.weatherook.weatherook.item.MyListRecyclerviewData

/**
 * Created by HYEON on 2018-07-04.
 */

class MyListFragment : Fragment(), View.OnClickListener {
    override fun onClick(p0: View?) {

    }

    var mylistitems: ArrayList<MyListRecyclerviewData> = ArrayList()

    lateinit var myListRecyclerviewAdapter: MyListRecyclerviewAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = View.inflate(activity, R.layout.fragment_my_list, null)

        return view
    }

    override fun onStart() {
        super.onStart()
        val mypage_recycle: RecyclerView = view!!.findViewById(R.id.setting_list_recycle)


        mylistitems.add(MyListRecyclerviewData(R.drawable.brown, "mystoryname", R.drawable.heart, "7월25일", "맑음", "31/25", "오늘 날씨는 최고", "#정빈이최고"))
        mylistitems.add(MyListRecyclerviewData(R.drawable.brown, "mystoryname", R.drawable.heart, "7월25일", "맑음", "31/25", "오늘 날씨는 최고", "#정빈이최고"))
        mylistitems.add(MyListRecyclerviewData(R.drawable.brown, "mystoryname", R.drawable.heart, "7월25일", "맑음", "31/25", "오늘 날씨는 최고", "#정빈이최고"))


        myListRecyclerviewAdapter = MyListRecyclerviewAdapter(mylistitems, context!!)
        myListRecyclerviewAdapter.setOnItemClickListener(this)
        mypage_recycle.layoutManager = LinearLayoutManager(activity)
        //myGridRecyclerviewAdapter = MyGridRecyclerviewAdapter(myitems, context!!)
        mypage_recycle.adapter = myListRecyclerviewAdapter
        /*myListRecyclerviewAdapter.apply {
            mypage_recycle.adapter = this
        }*/
    }
}