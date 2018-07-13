package org.weatherook.weatherook.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_following_b.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.CommentAdapter
import org.weatherook.weatherook.adapter.recyclerview.FollowingAdapter
import org.weatherook.weatherook.api.network.NetworkService
import org.weatherook.weatherook.item.CommentItem
import org.weatherook.weatherook.item.FollowingItem
import org.weatherook.weatherook.singleton.tokenDriver
import org.weatherook.weatherook.ui.activity.PopupActivity


class FollowingBFragment : Fragment(),View.OnClickListener {

    var token : String ?= null

    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null

    override fun onClick(v: View?) {

    }

    lateinit var commentItems1 : ArrayList<CommentItem>
    lateinit var commentItems2 : ArrayList<CommentItem>
    lateinit var commentAdapter : CommentAdapter
    lateinit var commentLinearLayoutManager: LinearLayoutManager
    lateinit var followingItems : ArrayList<FollowingItem>
    lateinit var followingAdapter : FollowingAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_following_b, container, false)

        tokenDriver.tokenDriver.subscribe{
            token = it
            Log.i("grid", token)
        }

        if(token!=null){
            val call = networkService.postFollowBoard(token!!)
            disposable = call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            { FollowBoardModel ->
                                Log.i("latestboard", FollowBoardModel.data.size.toString())
                                for (i in 0..FollowBoardModel.data.size-1){
                                    FollowBoardModel.data[i].let{
                                        followingItems.add(FollowingItem(it.boardIdx,it.userImg,it.userId,
                                                it.likeCnt, it.boardImg, it.boardDate, it.boardWeather, it.boardTempMin.toString()+"º/"+it.boardTempMax.toString()+"º",
                                                it.boardDesc, it.commentList))
                                        Log.i("latestboard", it.boardDate)
                                    }
                                    followingAdapter.notifyDataSetChanged()
                                }
                                //followingItems.add(FollowingItem(.data[]))
                            }, { fail -> Log.i("urls_failed", fail.message) })
        }
        
        return v
    }



    override fun onStart() {
        super.onStart()

        followingItems = ArrayList()
        commentItems1 = ArrayList()
        commentItems2 = ArrayList()

        commentItems1.add(CommentItem("kim","hihihi"))
        commentItems1.add(CommentItem("bin","i am bin"))
        commentItems1.add(CommentItem("young","hihihi"))

        commentItems2.add(CommentItem("young","hihihi"))
        commentItems2.add(CommentItem("kim","wow"))
        commentItems2.add(CommentItem("kim","zzzzz"))

        /*followingItems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart, "112", R.drawable.main_night_2, "7월 25일", "맑음", "25/31", "정빈이는 체고다 정비니 짱짱", commentItems1))
        followingItems.add(FollowingItem(R.drawable.brown, "프린스 빈", R.drawable.heart, "112", R.drawable.main_rain_2, "7월 26일", "흐림", "24/31", "정빈이는 체고다 정비니 짱짱", commentItems2))
        followingItems.add(FollowingItem(R.drawable.brown, "정시후", R.drawable.heart, "112", R.drawable.main_snow_2, "7월 27일", "맑음", "25/31", "정빈이는 체고다 정비니 짱짱", commentItems1))
        followingItems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart, "112", R.drawable.main_cloud_sun_2, "7월 2일", "맑음", "27/31", "정빈이는 체고다 정비니 짱짱", commentItems2))*/


        commentLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        followingAdapter = FollowingAdapter(followingItems, commentLinearLayoutManager, context!!)
        //      followingAdapter.setOnItemClickListener(this)
        home_following_recycler.layoutManager = LinearLayoutManager(context)
        home_following_recycler.adapter = followingAdapter



    }

}