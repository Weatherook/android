package org.weatherook.weatherook.ui.fragment.filter


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
class FilterCompleteFragment : Fragment(),View.OnClickListener {

    override fun onClick(p0: View?) {

        when(p0){
            refilter_txt-> {
                fragmentManager!!.beginTransaction().remove(this).commit()
                fragmentManager!!.popBackStack()
            }
        }
    }
    lateinit var filtercomitems : ArrayList<FollowingItem>
    lateinit var filtercomadapter : FollowingAdapter
    lateinit var commentItems1 : ArrayList<CommentItem>
    lateinit var commentItems2 : ArrayList<CommentItem>
    lateinit var filterLinearLayoutManager: LinearLayoutManager

    var filter_gender =""
    var filter_height =0
    var filter_bodySize =""
    var filter_stylelist = ArrayList<String>()


    override fun onCreateView(inflater: LayoutInflater, conatiner: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = View.inflate(activity!!, R.layout.fragment_filter_complete, null)
        FilterDriver.filterDriver.subscribe {
            view.filter_com_gender.text=it.gender
            view.filter_com_tall.text=it.height.toString()
            view.filter_com_size.text=it.bodySize
            var str =""
            for(i in it.stylelist){
                str+i
            }
            filter_gender = it.gender
            filter_height = it.height
            filter_bodySize = it.bodySize
            filter_stylelist = it.stylelist
            view.filter_com_style.text = str
        }

        return view
    }

    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null

    override fun onStart() {
        super.onStart()
        Log.i("completeFragment",this.id.toString())

        commentItems1 = ArrayList()
        commentItems2 = ArrayList()
        filtercomitems = ArrayList()
        val call = networkService.postTodayFilter(filter_gender,filter_height,filter_bodySize,filter_stylelist)
        disposable = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        { success->
                            for(i in 0..success.data.size-1){
                                success.data.get(i).let {
                                    filtercomitems.add(FollowingItem(it.boardIdx,it.userImg.toString(),it.userId,it.likeCnt,
                                            it.boardImg,it.boardDate,it.boardWeather,it.boardTempMax.toString()+"/"+it.boardTempMin.toString(),
                                            it.boardDesc.toString(),it.commentList))
                                }
                            }


                            },{fail-> Log.i("urls_failed", fail.message)})

        /*filtercomitems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart, "112", R.drawable.main_night_2, "7월 25일", "맑음", "25/31", "정빈이는 체고다 정비니 짱짱", commentItems1))
        filtercomitems.add(FollowingItem(R.drawable.brown, "프린스 빈", R.drawable.heart, "112", R.drawable.main_rain_2, "7월 26일", "흐림", "24/31", "정빈이는 체고다 정비니 짱짱", commentItems2))
        filtercomitems.add(FollowingItem(R.drawable.brown, "정시후", R.drawable.heart, "112", R.drawable.main_snow_2, "7월 27일", "맑음", "25/31", "정빈이는 체고다 정비니 짱짱", commentItems1))
        filtercomitems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart, "112", R.drawable.main_cloud_sun_2, "7월 2일", "맑음", "27/31", "정빈이는 체고다 정비니 짱짱", commentItems2))*/

        filterLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        filtercomadapter = FollowingAdapter(filtercomitems, filterLinearLayoutManager, context!!)
        filter_com_recycle.layoutManager = LinearLayoutManager(context)
        filter_com_recycle.adapter = filtercomadapter

        refilter_txt.setOnClickListener(this)


    }

    var c_gender : String="여"
    var c_tall : Int?=null
    var c_size : String?=null
    var c_style : ArrayList<String>?=null


    fun displayReceivedData(gender : String, tall:Int, size :String, style : ArrayList<String>){
        c_gender = gender
        c_tall = tall
        c_size = size
        c_style = style
        Log.i("c_gender",c_gender)
        Log.i("c_tall",c_tall.toString())

    }



}