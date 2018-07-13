package org.weatherook.weatherook.ui.fragment

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.location.*
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.api.network.NetworkService
import org.weatherook.weatherook.adapter.viewpager.FollowingPagerAdapter
import org.weatherook.weatherook.adapter.viewpager.HomePagerAdapter
import org.weatherook.weatherook.singleton.WeatherDriver
import java.util.*
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator
import org.weatherook.weatherook.adapter.viewpager.RecommendPagerAdapter
import org.weatherook.weatherook.item.LatLongItem
import org.weatherook.weatherook.item.WeatherDriverItem
import org.weatherook.weatherook.singleton.LatLongDriver
import org.weatherook.weatherook.singleton.tokenDriver
import org.weatherook.weatherook.ui.activity.PopupActivity
import org.weatherook.weatherook.utils.CustomViewPager


class HomeFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {

    }

    var token: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = View.inflate(activity, R.layout.fragment_home, null)

        tokenDriver.tokenDriver.subscribe {
            token = it
            Log.i("myfrag", token)
        }

        val permissionlistener = object : PermissionListener {
            override fun onPermissionGranted() {
                //Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show()
                startLocationUpdates()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
                Toast.makeText(activity, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(activity)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("권한을 주지 않으면 사용할 수 없습니다.")
                .setPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .check()
        return view
    }

    override fun onStart() {
        super.onStart()
        //  val following_recycle: RecyclerView = view!!.findViewById(R.id.home_following_recycler)

/*
            home_scroll.setOnTouchListener { v, event ->  Log.d("tag",home_tab.top.toString())
                true }
*/
        //      home_scroll.pageScroll(View.FOCUS_UP)
        //    home_scroll.isSmoothScrollingEnabled = true
        //   home_scroll.

        val extensiblePageIndicator = view!!.findViewById(R.id.flexibleIndicator) as ExtensiblePageIndicator
        val viewPager = view!!.findViewById<ViewPager>(R.id.weather_viewPager)
        val adapter = HomePagerAdapter(childFragmentManager)


        viewPager.adapter = adapter
        viewPager.currentItem = 1
        extensiblePageIndicator.initViewPager(viewPager)

        val rviewPager = view!!.findViewById<CustomViewPager>(R.id.recommend_viewPager)
        val radapter = RecommendPagerAdapter(childFragmentManager)

        rviewPager.adapter = radapter
        rviewPager.setPagingEnabled(false)
       /* if(home_tab.selectedTabPosition==1){
            if(token==null){
                val intent1 = Intent(context, PopupActivity::class.java)
                startActivity(intent1)
            }
        }*/

        val fviewPager = view!!.findViewById<ViewPager>(R.id.home_following_viewPager)
        val fadapter = FollowingPagerAdapter(childFragmentManager)

        fviewPager.adapter = fadapter
        home_tab.setupWithViewPager(fviewPager)

        fviewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {




            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //
            }

            override fun onPageSelected(position: Int) {
                //
                if (position == 1) {
                    if(token==null){
                        val intent1 = Intent(context, PopupActivity::class.java)
                        startActivity(intent1)
                        fviewPager.currentItem = 0
                    }

                }
            }
        })

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {




            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //
            }

            override fun onPageSelected(position: Int) {
                //
                if (position == 2) {
                    rviewPager.currentItem = 1
                } else if (position == 1) {
                    rviewPager.currentItem = 0
                }
            }
        })
    }


    lateinit var locationRequest: LocationRequest
    private val UPDATE_INTERVAL = (10 * 1000).toLong()  //10초
    private val FASTEST_INTERVAL: Long = 2000

    protected fun startLocationUpdates() {
        // 업데이트 받을 location request 생성
        locationRequest = LocationRequest()
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setInterval(UPDATE_INTERVAL)
        locationRequest.setFastestInterval(FASTEST_INTERVAL)
        // location request를 사용하는 LocationSettingsRequest 객체 생성
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(activity!!)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        @Suppress
        FusedLocationProviderClient(activity!!).requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                onLocationChanged(locationResult!!.lastLocation)
            }

            override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
                try {
                    if (!locationAvailability!!.isLocationAvailable) {
                        Toast.makeText(activity!!, "위치 불러오기에 실패함", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, Looper.myLooper())

    }

    fun onLocationChanged(location: Location) {
        try {
            getAddress(activity!!, location.latitude, location.longitude)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getAddress(context: Context, lat: Double, lng: Double): String {
        var nowAddress = "현재 위치를 확인할 수 없습니다."
        val geocoder = Geocoder(context, Locale.KOREA)
        var address = geocoder.getFromLocation(lat, lng, 1)
        try {
            if (address != null && address.size > 0) {
                val currentLocationAddress = address.get(0).subLocality
                WeatherDriver.weatherDriver.onNext(WeatherDriverItem(lat, lng))
                LatLongDriver.LatLongDriver.onNext(LatLongItem(lat,lng))
                //Toast.makeText(activity!!, currentLocationAddress, Toast.LENGTH_SHORT).show()
                nowAddress = currentLocationAddress
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return nowAddress
    }

    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null

    /*fun signin() {

        val call = networkService.getOneBoard(1)
        disposable = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ success->
                },{})
    }*/


}
