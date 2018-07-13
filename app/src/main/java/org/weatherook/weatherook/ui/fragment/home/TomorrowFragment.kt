package org.weatherook.weatherook.ui.fragment.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_weather_tomorrow.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.api.network.NetworkService
import org.weatherook.weatherook.singleton.LatLongDriver
import java.text.SimpleDateFormat
import java.util.*


class TomorrowFragment : Fragment() {

    var state = true
    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null

    var x: Double = 37.0
    var y: Double = 126.0

    var weatherStr = arrayOf("맑음", "맑음", "구름", "흐림", "비", "비", "눈")
    var weatherIcon = arrayOf(R.drawable.main_sun, R.drawable.main_sun, R.drawable.main_cloud_sun_2, R.drawable.main_cloud_2, R.drawable.main_cloud_2, R.drawable.main_rainy_big, R.drawable.main_snow_2)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = View.inflate(activity, R.layout.fragment_weather_tomorrow, null)

        LatLongDriver.LatLongDriver.subscribe {

                x = it.x
                y = it.y

                Log.d("tag", "========================" + x + "======================" + y)

                val call = networkService.postWeather(x, y, 3)
                disposable = call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                                { WeatherModel ->
                                    tomorrow_am_temp.setText(WeatherModel.data.tempAm.toString() + "º")
                                    tomorrow_pm_temp.setText(WeatherModel.data.tempAf.toString() + "º")
                                    tomorrow_am_image.setImageResource(weatherIcon[WeatherModel.data.weatherAm.toInt()])
                                    tomorrow_pm_image.setImageResource(weatherIcon[WeatherModel.data.weatherAf.toInt()])
                                    tomorrow_am_weather.setText(weatherStr[WeatherModel.data.weatherAm.toInt()])
                                    tomorrow_pm_weather.setText(weatherStr[WeatherModel.data.weatherAf.toInt()])
                                }, { /*fail -> Log.i("WeatherFragment", fail.message)*/ })



        }
        return view
    }

    fun tommorowdate() :String {
        val sdf = SimpleDateFormat("MM월 dd일 E요일", Locale.KOREA)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, +1)  // 오늘 날짜에서 하루를 뺌.
        val date = sdf.format(calendar.getTime())
        return date
    }

    override fun onStart() {
        super.onStart()

        home_weather_tommorow_date.text = tommorowdate()

    }
}