package org.weatherook.weatherook.ui.fragment.filter

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.filter_today.*
import kotlinx.android.synthetic.main.fragment_filter.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.item.FilterItem
import org.weatherook.weatherook.singleton.FilterDriver

class FilterFragment :  Fragment(), View.OnClickListener{

    var gender :String = "여"
    var tall : Int = 160
    var size : String = "1"
    var sizelist = arrayOf("마름","보통","통통", "뚱뚱")
    var stylelist : ArrayList<String> = ArrayList()
    var today_style: ArrayList<TextView> = ArrayList()


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
                fragmentTransaction.replace(R.id.filter_com_frame, FilterCompleteFragment(),"filtercomplete").commit()
                if(!today_women.isSelected == true) gender = "남"
                else gender = "여"
                tall = today_spinner_tall.selectedItem.toString().toInt()
                // tall = today_spinner_tall.selectedItemPosition.toString().toInt()
                size = sizelist[today_size_seekbar.progress]
                today_style.add(today_style_btn1)
                today_style.add(today_style_btn2)
                today_style.add(today_style_btn3)
                today_style.add(today_style_btn4)
                today_style.add(today_style_btn5)
                today_style.add(today_style_btn6)
                today_style.add(today_style_btn7)
                today_style.add(today_style_btn8)
                today_style.add(today_style_btn9)
                today_style.add(today_style_btn10)


//
                for (ts in today_style) {
                    if(ts.isSelected){
                        if(!stylelist.contains(ts.text.toString())){
                            stylelist.add(ts.text.toString())
                        }
                    }else{
                        if(stylelist.contains(ts.text.toString())){
                            stylelist.remove(ts.text.toString())
                        }
                    }
                }
                Log.d("tag", "============================ : " + gender + tall+ size + stylelist+ "=============================")
                FilterDriver.filterDriver.onNext(FilterItem(gender, tall, size, stylelist))

            }
        }

    }


    override fun onCreateView(inflater: LayoutInflater, conatiner: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = View.inflate(activity, R.layout.fragment_filter, null)

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

        //  SM!!.sendData(gender, tall, size, stylelist)
    }

    override fun onResume() {
        super.onResume()
        stylelist.clear()
        Log.i("filterFragment", stylelist.toString())
    }

    /*var SM: SendMessage? = null

    interface SendMessage {
        fun sendData(gender : String, tall : Int, size : String, stylelist : ArrayList<String>)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        try {
            SM = activity as SendMessage
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
*/


}