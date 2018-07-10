package org.weatherook.weatherook.adapter.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.weatherook.weatherook.R
import org.weatherook.weatherook.item.WeatherItem
import org.weatherook.weatherook.viewholder.WeatherViewHolder

class WeatherAdapter(var weatherItems : ArrayList<WeatherItem>) : RecyclerView.Adapter<WeatherViewHolder>()  {

    private lateinit var onItemClick: View.OnClickListener

    fun setOnItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val mainView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_weather,parent,false)
        mainView.setOnClickListener(onItemClick)
        return WeatherViewHolder(mainView)
    }

    override fun getItemCount(): Int = weatherItems.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.weatherTime.text = weatherItems[position].time
        holder.weatherWeather.setImageResource(weatherItems[position].weather)
        holder.weatherTemp.text = weatherItems[position].temp

    }

}