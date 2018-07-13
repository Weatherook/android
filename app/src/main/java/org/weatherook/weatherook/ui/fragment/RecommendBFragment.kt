package org.weatherook.weatherook.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recommend_today.view.*
import kotlinx.android.synthetic.main.fragment_recommend_tomorrow.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.RecommendAdapter
import org.weatherook.weatherook.api.network.NetworkService
import org.weatherook.weatherook.item.RecommendItem
import org.weatherook.weatherook.singleton.WeatherDriver
import org.weatherook.weatherook.singleton.tokenDriver
import java.util.ArrayList

class RecommendBFragment : Fragment(), View.OnClickListener {
    //내일의 추천코디
    var item1 = true

    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null

    var token: String? = null

    override fun onClick(v: View) {

        when (v) {
            home_refresh_btn -> {
                clear()
/*
                if(!item1){
                    additem1()
                    item1 = true
                }

                else {
                    additem2()
                    item1= false
                }
                onResume()*/
            }
        }
    }

    lateinit var recommendItems: ArrayList<RecommendItem>
    lateinit var recommendAdapter: RecommendAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_recommend_tomorrow, container, false)
        tokenDriver.tokenDriver.subscribe {
            token = it
            Log.i("recommend", token)
        }
        recommendItems = ArrayList()

        //additem1()

        recommendAdapter = RecommendAdapter(recommendItems, context!!)
        //     recommendAdapter.setOnItemClickListener(this)
        v.home_recommend_recycler.layoutManager = GridLayoutManager(context, 2)
        v.home_recommend_recycler.adapter = recommendAdapter

        WeatherDriver.weatherDriver.subscribe { it ->
            try {
                x=it.x
                y=it.y

                val call = networkService.postRecommend(token, it.x.toFloat(), it.y.toFloat(), 3)
                disposable = call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                                { success ->
                                    if (recommendItems.size == 0) {
                                        Log.i("urls_success1", success.data.size.toString())
                                        var num: Int = 0
                                        if (success.data.size > 4) {
                                            num = 4
                                        } else {
                                            num = success.data.size
                                        }

                                        for (i in 0..num - 1) {
                                            recommendItems.add(RecommendItem(success.data[i].commendImg,success.data[i].commendRef.toString()))
                                            recommendAdapter.notifyDataSetChanged()
                                        }
                                    }
                                }, { fail -> Log.i("urls_failed", fail.message) })

            } catch (e: Exception) {
            }
        }
        return v
    }

    override fun onStart() {
        super.onStart()

        home_refresh_btn.setOnClickListener(this)
    }
    var x=0.toDouble()
    var y=0.toDouble()

    override fun onResume() {
        super.onResume()
        //recommendAdapter.notifyDataSetChanged()
        clear()
        val call = networkService.postRecommend(token, x.toFloat(), y.toFloat(), 2)
        disposable = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        { success ->
                            if (recommendItems.size == 0) {
                                Log.i("urls_success1", success.data.size.toString())
                                var num: Int = 0
                                if (success.data.size > 4) {
                                    num = 4
                                } else {
                                    num = success.data.size
                                }

                                if(recommendItems.size<4) {
                                    for(i in 0..num-1){
                                        recommendItems.add(RecommendItem(success.data[i].commendImg,success.data[i].commendRef.toString()))
                                        recommendAdapter.notifyDataSetChanged()
                                    }
                                }
                            }
                        }, { fail -> Log.i("urls_failed", fail.message) })
    }

    fun clear() {
        recommendItems.clear()
    }

    /*fun additem2(){
        recommendItems.add(RecommendItem(R.drawable.heartcolor))
        recommendItems.add(RecommendItem(R.drawable.heartcolor))
        recommendItems.add(RecommendItem(R.drawable.heartcolor))
        recommendItems.add(RecommendItem(R.drawable.heartcolor))

    }

    fun additem1(){
        recommendItems.add(RecommendItem(R.drawable.main_sun))
        recommendItems.add(RecommendItem(R.drawable.main_cloud_2))
        recommendItems.add(RecommendItem(R.drawable.main_rain_2))
        recommendItems.add(RecommendItem(R.drawable.main_sun))

    }*/


}