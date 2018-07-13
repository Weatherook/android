package org.weatherook.weatherook.ui.fragment.home

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_weather_today.*
import kotlinx.android.synthetic.main.fragment_weather_today.view.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.WeatherAdapter
import org.weatherook.weatherook.api.network.NetworkService
import org.weatherook.weatherook.item.WeatherDriverItem
import org.weatherook.weatherook.item.WeatherItem
import org.weatherook.weatherook.singleton.WeatherDriver
import java.util.*
import org.weatherook.weatherook.singleton.LatLongDriver
import java.text.SimpleDateFormat

class TodayFragment : Fragment(), View.OnClickListener {

    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null


    var weatherStr = arrayOf("맑음", "맑음", "구름", "흐림", "비", "비", "눈")
    var weatherIcon = arrayOf(R.drawable.main_sun, R.drawable.main_sun, R.drawable.main_cloud_sun_2, R.drawable.main_cloud_2, R.drawable.main_cloud_2, R.drawable.main_rainy_big, R.drawable.main_snow_2)
    var weatherimg = arrayOf(R.drawable.main_sun2, R.drawable.main_sun2, R.drawable.main_cloud_sun, R.drawable.main_cloud, R.drawable.main_rainy, R.drawable.main_rainy, R.drawable.main_snow)
    private var isWeatherButtons: Boolean = false
    override fun onClick(v: View) {
        if (!isWeatherButtons) {
            isWeatherButtons = true
            home_weather_alarm.visibility = View.INVISIBLE
           // home_weather_recycler.visibility = View.VISIBLE
            home_weather_grid.visibility = View.VISIBLE
        } else {
            isWeatherButtons = false
            home_weather_alarm.visibility = View.VISIBLE
           // home_weather_recycler.visibility = View.INVISIBLE
            home_weather_grid.visibility = View.INVISIBLE
        }

    }

    lateinit var weatherItems: ArrayList<WeatherItem>
    lateinit var weatherAdapter: WeatherAdapter
    private var x: Double = 37.0
    private var y: Double = 126.0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = View.inflate(activity!!, R.layout.fragment_weather_today, null)
        view.home_weather_location
        WeatherDriver.weatherDriver.subscribe {
            try {
                if(it!=null){
                    view.home_weather_location.text = getAddress(context!!,it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val image : ImageView = view.findViewById(R.id.frag_today_weatherimg)
        var anim = TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, -0.05f, TranslateAnimation.RELATIVE_TO_PARENT, 0.05f)
        anim.duration = 1000
        anim.repeatCount = -1
        anim.repeatMode = Animation.REVERSE
        anim.interpolator = BounceInterpolator()
        anim.fillAfter = true
        image.startAnimation(anim)
        /*image.setOnClickListener {
            var anim = TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f,
                    TranslateAnimation.RELATIVE_TO_PARENT, -0.05f, TranslateAnimation.RELATIVE_TO_PARENT, 0.05f)
            anim.duration = 1000
            anim.repeatCount = -1
            anim.repeatMode = Animation.REVERSE
            anim.interpolator = BounceInterpolator()
            anim.fillAfter = true
            image.startAnimation(anim)
        }*/




        WeatherDriver.weatherDriver.subscribe {

            x= it.x
            y = it.y

            Log.d("tag", "========================" + x + "======================" + y)
            val call1 = networkService.postTimeWeather(x.toFloat(), y.toFloat())
            disposable = call1.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            { WeatherTimeModel ->
                                val call = networkService.postTempWeather(WeatherTimeModel.data[0].temp, WeatherTimeModel.data[0].weather)
                                disposable = call.subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                                                { WeatherCommentModel ->
                                                    home_weather_alarm.text = WeatherCommentModel.data.weatherTextTemp + "\n" + WeatherCommentModel.data.weatherTextWeather
                                                }, { fail -> Log.i("TodayFragment", fail.message) })
                                weather_item_time1.text = WeatherTimeModel.data[0].hour + "시"
                                weather_item_temp1.text = WeatherTimeModel.data[0].temp.toString() + "º"
                                weather_item_weather1.setImageResource(weatherimg[WeatherTimeModel.data[0].weather])
                                weather_item_time2.text = WeatherTimeModel.data[1].hour + "시"
                                weather_item_temp2.text = WeatherTimeModel.data[1].temp.toString() + "º"
                                weather_item_weather2.setImageResource(weatherimg[WeatherTimeModel.data[1].weather])
                                weather_item_time3.text = WeatherTimeModel.data[2].hour + "시"
                                weather_item_temp3.text = WeatherTimeModel.data[2].temp.toString() + "º"
                                weather_item_weather3.setImageResource(weatherimg[WeatherTimeModel.data[2].weather])
                                weather_item_time4.text = WeatherTimeModel.data[3].hour + "시"
                                weather_item_temp4.text = WeatherTimeModel.data[3].temp.toString() + "º"
                                weather_item_weather4.setImageResource(weatherimg[WeatherTimeModel.data[3].weather])
                                weather_item_time5.text = WeatherTimeModel.data[4].hour + "시"
                                weather_item_temp5.text = WeatherTimeModel.data[4].temp.toString() + "º"
                                weather_item_weather5.setImageResource(weatherimg[WeatherTimeModel.data[4].weather])
                                weather_item_time6.text = WeatherTimeModel.data[5].hour + "시"
                                weather_item_temp6.text = WeatherTimeModel.data[5].temp.toString() + "º"
                                weather_item_weather6.setImageResource(weatherimg[WeatherTimeModel.data[5].weather])
                            }, { fail -> Log.i("WeatherFragment", fail.message) })
            val call2 = networkService.postWeather(x, y, 2)
            disposable = call2.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            { WeatherModel ->
                                home_weather_temp.setText(WeatherModel.data.currentTemp.toString())
                                home_weather_weather.setText(weatherStr[WeatherModel.data.currentWeather])
                                frag_today_weatherimg.setImageResource(weatherIcon[WeatherModel.data.currentWeather])
                                home_weather_prob.setText(WeatherModel.data.currentPop.toString())
                                home_weather_hum.setText(WeatherModel.data.currentReh.toString())
                                home_weather_tempmax.setText(WeatherModel.data.tempAm.toString())
                                home_weather_tempmin.setText(WeatherModel.data.tempAf.toString())

                            }, { /*fail -> Log.i("WeatherFragment", fail.message)*/ })

        }


        return view
    }

    override fun onPause() {
        super.onPause()
        val image : ImageView = view!!.findViewById(R.id.frag_today_weatherimg)
        image.clearAnimation()
    }

    override fun onResume() {
        super.onResume()
        val image : ImageView = view!!.findViewById(R.id.frag_today_weatherimg)
        var anim = TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, -0.05f, TranslateAnimation.RELATIVE_TO_PARENT, 0.05f)
        anim.duration = 1000
        anim.repeatCount = -1
        anim.repeatMode = Animation.REVERSE
        anim.interpolator = BounceInterpolator()
        anim.fillAfter = true
        image.startAnimation(anim)
    }

    /*  fun time() {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val CurDateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        val strCurDate = CurDateFormat.format(date)
    }*/

    fun doYearMonthDay(): String {
        val formatter = SimpleDateFormat("MM월 dd일 E요일", Locale.KOREA)
        val date = Date()
        return formatter.format(date)
    }


    fun getAddress(context: Context, item : WeatherDriverItem): String {
        var nowAddress = "현재 위치를 확인할 수 없습니다."
        val geocoder = Geocoder(context, Locale.KOREA)
        var address = geocoder.getFromLocation(item.x, item.y, 1)
        try {
            if (address != null && address.size > 0) {
                val currentLocationAddress = address.get(0).subLocality
                nowAddress = currentLocationAddress
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return nowAddress
    }

    override fun onStart() {
        super.onStart()

        home_weather_change.setOnClickListener(this)
        home_weather_grid.setOnClickListener(this)

        home_weather_date.text = doYearMonthDay()

    }
}
