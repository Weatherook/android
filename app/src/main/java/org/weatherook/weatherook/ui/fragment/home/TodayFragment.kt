package org.weatherook.weatherook.ui.fragment.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_weather_today.*
import kotlinx.android.synthetic.main.fragment_weather_today.view.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.WeatherAdapter
import org.weatherook.weatherook.item.WeatherItem
import org.weatherook.weatherook.singleton.weatherDriver

class TodayFragment : Fragment(), View.OnClickListener {

    private var isWeatherButtons : Boolean = false
    override fun onClick(v: View) {
        if (!isWeatherButtons) {
            isWeatherButtons = true
            home_weather_alarm.visibility = View.INVISIBLE
            home_weather_recycler.visibility = View.VISIBLE

        } else {
            isWeatherButtons = false
            home_weather_alarm.visibility = View.VISIBLE
            home_weather_recycler.visibility = View.INVISIBLE
        }
    }

    lateinit var weatherItems : ArrayList<WeatherItem>
    lateinit var weatherAdapter : WeatherAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = View.inflate(activity!!, R.layout.fragment_weather_today, null)
        view.home_weather_location
        weatherDriver.weatherDriver.subscribe {
            if(it!=null){
                view.home_weather_location.text = it
            }
        }
        val image : ImageView = view.findViewById(R.id.frag_today_weatherimg)
        image.setOnClickListener {
            var anim = TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f,
                    TranslateAnimation.RELATIVE_TO_PARENT, -0.05f, TranslateAnimation.RELATIVE_TO_PARENT, 0.05f)
            anim.duration = 1000
            anim.repeatCount = -1
            anim.repeatMode = Animation.REVERSE
            anim.interpolator = BounceInterpolator()
            anim.fillAfter =true
            image.startAnimation(anim)
        }
        return view
    }

    override fun onStart() {
        super.onStart()

        home_weather_change.setOnClickListener(this)

        weatherItems = ArrayList()

        weatherItems.add(WeatherItem("지금", R.drawable.main_sun2,"25º"))
        weatherItems.add(WeatherItem("1시", R.drawable.main_sun2,"25º"))
        weatherItems.add(WeatherItem("2시", R.drawable.main_rain_2,"25º"))
        weatherItems.add(WeatherItem("3시", R.drawable.main_rain_2,"25º"))
        weatherItems.add(WeatherItem("4시", R.drawable.main_sun2,"25º"))
        weatherItems.add(WeatherItem("5시", R.drawable.main_cloud_sun_2,"25º"))
        weatherItems.add(WeatherItem("6시", R.drawable.main_sun2,"25º"))
        weatherItems.add(WeatherItem("1시", R.drawable.main_sun2,"25º"))
        weatherItems.add(WeatherItem("2시", R.drawable.main_rain_2,"25º"))
        weatherItems.add(WeatherItem("3시", R.drawable.main_rain_2,"25º"))
        weatherItems.add(WeatherItem("4시", R.drawable.main_sun2,"25º"))
        weatherItems.add(WeatherItem("지금", R.drawable.main_cloud_sun_2,"25º"))
        weatherAdapter = WeatherAdapter(weatherItems)
        weatherAdapter.setOnItemClickListener(this)
        home_weather_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        home_weather_recycler.adapter = weatherAdapter
    }
}