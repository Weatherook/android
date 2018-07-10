package org.weatherook.weatherook.ui.fragment.filter

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_filter.*
import org.weatherook.weatherook.R

class FilterFragment :  Fragment(), View.OnClickListener{
    override fun onClick(p0: View?) {

        when(p0) {
            filter_today -> {
                filter_today.setTextColor(resources.getColor(R.color.weatherookTheme))
                filter_total.setTextColor(Color.GRAY)
                filter_today.setText(Html.fromHtml("<u>"+ "오늘" + "<u>"))
                filter_total.setText(Html.fromHtml("전체"))

                val fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.filter_frame, FilterTodayFragment()).commit()
            }
            filter_total -> {
                filter_total.setTextColor(resources.getColor(R.color.weatherookTheme))
                filter_today.setText(Html.fromHtml("오늘"))
                filter_today.setTextColor(Color.GRAY)
                filter_total.setText(Html.fromHtml("<u>"+ "전체" + "<u>"))
                val fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.filter_frame, FilterTotalFragment()).commit()
            }
            filter_ok -> {
                val fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.filter_com_frame, FilterCompleteFragment()).commit()
            }
        }

    }


    override fun onCreateView(inflater: LayoutInflater, conatiner: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = View.inflate(activity, R.layout.fragment_filter, null)

        //val mygrid_recycle : RecyclerView = view.findViewById(R.id.setting_grid_recycle)
        //val mylist_recycle : RecyclerView = view.findViewById(R.id.setting_list_recycle)

        //val fragmentManager : FragmentManager = activity!!.supportFragmentManager

        return view
}


    override fun onStart() {
        super.onStart()
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.filter_frame, FilterTodayFragment()).commit()
        filter_today.setText(Html.fromHtml("<u>"+ "오늘" + "<u>"))
        //my_grid_img.isSelected = true
//        filter_today.isSelected = false
//        filter_total.isSelected = false
        filter_today.setOnClickListener(this)
        filter_total.setOnClickListener(this)
        filter_ok.setOnClickListener(this)

    }



}