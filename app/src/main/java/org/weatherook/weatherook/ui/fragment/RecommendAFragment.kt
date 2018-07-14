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
import kotlinx.android.synthetic.main.fragment_recommend_today.*
import kotlinx.android.synthetic.main.fragment_recommend_today.view.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.RecommendAdapter
import org.weatherook.weatherook.api.network.NetworkService
import org.weatherook.weatherook.item.RecommendItem
import org.weatherook.weatherook.singleton.tokenDriver
import org.weatherook.weatherook.singleton.WeatherDriver
import java.util.*

class RecommendAFragment : Fragment(), View.OnClickListener {
    //오늘의 추천코디
    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null

    var token: String? = null

    var item1 = true
    override fun onClick(v: View) {

        when (v) {
            recommend_refresh_btn -> {
                clear()
                val call = networkService.postRecommend(token, x.toFloat(), y.toFloat(), 2)
                disposable = call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                                { success ->

                                    Log.i("urls_success1", success.data.size.toString())
                                    var num: Int = 0
                                    if (success.data.size > 4) {
                                        num = 4
                                    } else {
                                        num = success.data.size
                                    }

                                    for (i in 0..num - 1) {
                                        recommendItems.add(RecommendItem(success.data[i].commendImg, success.data[i].commendRef.toString()))
                                        recommendAdapter.notifyDataSetChanged()
                                    }

                                }, { fail -> Log.i("urls_failed", fail.message) })
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

        /*when(v){
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
        }*/
    }

    var x = 37.toDouble()
    var y = 126.toDouble()

    lateinit var recommendItems: ArrayList<RecommendItem>
    lateinit var recommendAdapter: RecommendAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_recommend_today, container, false)
        tokenDriver.tokenDriver.subscribe {
            token = it
            Log.i("recommend", token)
        }

        recommendItems = ArrayList()

        recommendAdapter = RecommendAdapter(recommendItems, context!!)
        //     recommendAdapter.setOnItemClickListener(this)
        v.home_recommend_recycler.layoutManager = GridLayoutManager(context, 2)
        v.home_recommend_recycler.adapter = recommendAdapter

        WeatherDriver.weatherDriver.subscribe { it ->
            try {
                x = it.x
                y = it.y
                val call = networkService.postRecommend(token, it.x.toFloat(), it.y.toFloat(), 2)
                disposable = call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                                { success ->
                                        for (i in 0..4-recommendItems.size-1) {
                                            recommendItems.add(RecommendItem(success.data[i].commendImg, success.data[i].commendRef.toString()))
                                            recommendAdapter.notifyDataSetChanged()
                                        }

                                }, { fail -> Log.i("urls_failed", fail.message) })

            } catch (e: Exception) {
            }
        }
        return v
    }

    override fun onStart() {
        super.onStart()

        recommend_refresh_btn.setOnClickListener(this)


        /*if(token!=null){
            val call = networkService.postRecommend(token!!)
            disposable = call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            { success->
                                Log.i("urls_success1", success.data.size.toString())
                                for(i in 0..success.data.size-1){

                                }
                            },{fail-> Log.i("urls_failed", fail.message)})
        }*/

        //additem1()

    }

    fun clear() {
        recommendItems.clear()
        val call = networkService.postRecommend(token, x.toFloat(), y.toFloat(), 2)
        disposable = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        { success ->
                            for (i in 0..4-recommendItems.size-1) {
                                recommendItems.add(RecommendItem(success.data[i].commendImg, success.data[i].commendRef.toString()))
                                recommendAdapter.notifyDataSetChanged()
                            }

                        }, { fail -> Log.i("urls_failed", fail.message) })
    }

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

                                for (i in 0..num - 1) {
                                    recommendItems.add(RecommendItem(success.data[i].commendImg, success.data[i].commendRef.toString()))
                                    recommendAdapter.notifyDataSetChanged()
                                }
                            }
                        }, { fail -> Log.i("urls_failed", fail.message) })
    }

    /*fun additem1(){
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
    }*/


}