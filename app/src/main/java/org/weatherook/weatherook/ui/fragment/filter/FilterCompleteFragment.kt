package org.weatherook.weatherook.ui.fragment.filter


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_filter_complete.*
import kotlinx.android.synthetic.main.fragment_filter_complete.view.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.FollowingAdapter
import org.weatherook.weatherook.api.network.NetworkService
import org.weatherook.weatherook.item.CommentItem
import org.weatherook.weatherook.item.FollowingItem
import org.weatherook.weatherook.singleton.FilterDriver

/**
 * Created by HYEON on 2018-07-09.
 */
class FilterCompleteFragment : Fragment(), View.OnClickListener {

    override fun onClick(p0: View?) {

        when (p0) {
            refilter_txt -> {
                fragmentManager!!.beginTransaction().remove(this).commit()
                //fragmentManager!!.popBackStack()
            }
        }
    }

    lateinit var filtercomitems: ArrayList<FollowingItem>
    lateinit var filtercomadapter: FollowingAdapter
    lateinit var commentItems1: ArrayList<CommentItem>
    lateinit var commentItems2: ArrayList<CommentItem>
    lateinit var filterLinearLayoutManager: LinearLayoutManager

    var filter_gender = ""
    var filter_height = 0
    var filter_bodySize = ""
    var filter_stylelist = ArrayList<String>()
    var str = ""


    override fun onCreateView(inflater: LayoutInflater, conatiner: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = View.inflate(activity!!, R.layout.fragment_filter_complete, null)

        return view
    }

    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null

    override fun onPause() {
        super.onPause()
        val filter_com_recycle: RecyclerView = view!!.findViewById(R.id.filter_com_recycle)
        filter_com_recycle.adapter=null
    }

    override fun onResume() {
        super.onResume()
        Log.i("completeFragment", this.id.toString())

        filtercomitems = ArrayList()
        filterLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        filtercomadapter = FollowingAdapter(filtercomitems, filterLinearLayoutManager, context!!)
        val filter_com_recycle: RecyclerView = view!!.findViewById(R.id.filter_com_recycle)
        filter_com_recycle.layoutManager = LinearLayoutManager(context)
        filter_com_recycle.adapter = filtercomadapter

        FilterDriver.filterDriver.subscribe ({
            filter_com_gender.text = it.gender
            filter_com_tall.text = it.height.toString()
            filter_com_size.text = it.bodySize
            for (i in it.stylelist) {
                str + i
                Log.i("filter_log", str)
            }
            filter_gender = it.gender
            filter_height = it.height
            filter_bodySize = it.bodySize
            filter_stylelist = it.stylelist

            Log.i("filter_stylelist", filter_stylelist.toString())
            val call = networkService.postTodayFilter(it.gender, it.height, it.bodySize, it.stylelist)
            disposable = call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            { success ->
                                Log.i("filtercompletesucess", success.message + "//" + success.data.size)
                                for (i in 0..success.data.size - 1) {
                                    success.data.get(i).let {
                                        filtercomitems.add(FollowingItem(it.boardIdx, it.userImg.toString(), it.userId, it.likeCnt,
                                                it.boardImg, it.boardDate, it.boardWeather, it.boardTempMax.toString() + "/" + it.boardTempMin.toString(),
                                                it.boardDesc.toString(), it.commentList))
                                        filtercomadapter.notifyDataSetChanged()
                                    }
                                }


                            }, { fail -> Log.i("filtercomplete", fail.message) })
        },{fail -> })
        filter_com_style1.text = str

        /*val call = networkService.postTodayFilter(filter_gender,filter_height,filter_bodySize,filter_stylelist)
        disposable = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        { success->
                            Log.i("filtercompletesucess", success.message+"//"+success.data.size)
                            for(i in 0..success.data.size-1){
                                success.data.get(i).let {
                                    filtercomitems.add(FollowingItem(it.boardIdx,it.userImg.toString(),it.userId,it.likeCnt,
                                            it.boardImg,it.boardDate,it.boardWeather,it.boardTempMax.toString()+"/"+it.boardTempMin.toString(),
                                            it.boardDesc.toString(),it.commentList))
                                    filtercomadapter.notifyDataSetChanged()
                                }
                            }


                            },{fail-> Log.i("filtercomplete", fail.message)})*/

        /*filtercomitems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart, "112", R.drawable.main_night_2, "7월 25일", "맑음", "25/31", "정빈이는 체고다 정비니 짱짱", commentItems1))
        filtercomitems.add(FollowingItem(R.drawable.brown, "프린스 빈", R.drawable.heart, "112", R.drawable.main_rain_2, "7월 26일", "흐림", "24/31", "정빈이는 체고다 정비니 짱짱", commentItems2))
        filtercomitems.add(FollowingItem(R.drawable.brown, "정시후", R.drawable.heart, "112", R.drawable.main_snow_2, "7월 27일", "맑음", "25/31", "정빈이는 체고다 정비니 짱짱", commentItems1))
        filtercomitems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart, "112", R.drawable.main_cloud_sun_2, "7월 2일", "맑음", "27/31", "정빈이는 체고다 정비니 짱짱", commentItems2))*/

        refilter_txt.setOnClickListener(this)


    }
}