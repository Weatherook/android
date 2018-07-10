package org.weatherook.weatherook.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_recommend_today.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.RecommendAdapter
import org.weatherook.weatherook.item.RecommendItem
import java.util.ArrayList

class RecommendAFragment  : Fragment(), View.OnClickListener {
    var item1 = true
    override fun onClick(v: View) {

        when(v){
            recommend_refresh_btn -> {
                clear()

                if(!item1){
                    additem1()
                    item1 = true
                }

                else {
                    additem2()
                    item1= false
                }
                onResume()
            }
        }
    }

    lateinit var recommendItems: ArrayList<RecommendItem>
    lateinit var recommendAdapter: RecommendAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_recommend_today, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()

        recommend_refresh_btn.setOnClickListener(this)
        recommendItems = ArrayList()

        additem1()

        recommendAdapter = RecommendAdapter(recommendItems,context!!)
        //     recommendAdapter.setOnItemClickListener(this)
        home_recommend_recycler.layoutManager = GridLayoutManager(context,2)
        home_recommend_recycler.adapter = recommendAdapter

    }

    override fun onResume() {
        super.onResume()
        recommendAdapter.notifyDataSetChanged()
    }

    fun clear(){
        recommendItems.clear()
    }

    fun additem1(){
        recommendItems.add(RecommendItem(R.drawable.heartcolor))
        recommendItems.add(RecommendItem(R.drawable.heartcolor))
        recommendItems.add(RecommendItem(R.drawable.heartcolor))
        recommendItems.add(RecommendItem(R.drawable.heartcolor))

    }

    fun additem2(){
        recommendItems.add(RecommendItem(R.drawable.main_sun))
        recommendItems.add(RecommendItem(R.drawable.main_cloud_2))
        recommendItems.add(RecommendItem(R.drawable.main_rain_2))
        recommendItems.add(RecommendItem(R.drawable.main_sun))

    }


}