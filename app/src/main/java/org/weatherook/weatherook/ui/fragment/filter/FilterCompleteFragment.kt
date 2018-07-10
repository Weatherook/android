package org.weatherook.weatherook.ui.fragment.filter


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_filter_complete.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.FollowingAdapter
import org.weatherook.weatherook.item.CommentItem
import org.weatherook.weatherook.item.FollowingItem

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


    override fun onCreateView(inflater: LayoutInflater, conatiner: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = View.inflate(activity, R.layout.fragment_filter_complete, null)

        return view
    }

    override fun onStart() {
        super.onStart()

        commentItems1 = ArrayList()
        commentItems2 = ArrayList()
        filtercomitems = ArrayList()

        commentItems1.add(CommentItem("kim","hihihi"))
        commentItems1.add(CommentItem("bin","i am bin"))
        commentItems1.add(CommentItem("young","hihihi"))

        commentItems2.add(CommentItem("young","hihihi"))
        commentItems2.add(CommentItem("kim","wow"))
        commentItems2.add(CommentItem("kim","zzzzz"))

        filtercomitems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart, "112", R.drawable.main_night_2, "7월 25일", "맑음", "25/31", "정빈이는 체고다 정비니 짱짱", commentItems1))
        filtercomitems.add(FollowingItem(R.drawable.brown, "프린스 빈", R.drawable.heart, "112", R.drawable.main_rain_2, "7월 26일", "흐림", "24/31", "정빈이는 체고다 정비니 짱짱", commentItems2))
        filtercomitems.add(FollowingItem(R.drawable.brown, "정시후", R.drawable.heart, "112", R.drawable.main_snow_2, "7월 27일", "맑음", "25/31", "정빈이는 체고다 정비니 짱짱", commentItems1))
        filtercomitems.add(FollowingItem(R.drawable.brown, "hiriyo", R.drawable.heart, "112", R.drawable.main_cloud_sun_2, "7월 2일", "맑음", "27/31", "정빈이는 체고다 정비니 짱짱", commentItems2))

        filterLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        filtercomadapter = FollowingAdapter(filtercomitems, filterLinearLayoutManager, context!!)
        filter_com_recycle.layoutManager = LinearLayoutManager(context)
        filter_com_recycle.adapter = filtercomadapter

        refilter_txt.setOnClickListener(this)


    }


}