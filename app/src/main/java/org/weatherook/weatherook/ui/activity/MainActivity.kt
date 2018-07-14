package org.weatherook.weatherook.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.singleton.tokenDriver
import org.weatherook.weatherook.ui.fragment.HomeFragment
import org.weatherook.weatherook.ui.fragment.bell.BellFragment
import org.weatherook.weatherook.ui.fragment.camera.CamHomeFragment
import org.weatherook.weatherook.ui.fragment.filter.FilterFragment
import org.weatherook.weatherook.ui.fragment.my.MyFragment
import org.weatherook.weatherook.ui.fragment.*
import java.text.SimpleDateFormat
import java.util.*



class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var fragmentManager: FragmentManager
//
    lateinit var homeFragment : Fragment
    val searchFragment = FilterFragment()
    lateinit var cameraFragment: Fragment

   lateinit var likedFragment : Fragment
   lateinit var myFragment :Fragment
   lateinit var fragmentTransaction: FragmentTransaction

    override fun onClick(v: View?) {

        when (v) { // 자바에서 switch와 같음
            main_btn_home -> {
                clearSelected()
                main_btn_home.isSelected = true
                replaceFragment(HomeFragment())
            }
            main_btn_search -> {
                if(intent.getStringExtra("token")== null) {
                    val intent1 = Intent(applicationContext, PopupActivity::class.java)
                    startActivity(intent1)
                } else {
                    clearSelected()
                    main_btn_search.isSelected = true
                    replaceFragment(FilterFragment())
                }
            }
            main_btn_add -> {
                if(intent.getStringExtra("token")== null) {
                    val intent1 = Intent(applicationContext, PopupActivity::class.java)
                    startActivity(intent1)
                } else {
                    clearSelected()
                    main_btn_add.isSelected = true
                    if (Build.VERSION.SDK_INT >= 21) {
                        replaceFragment(CamHomeFragment())
                    } else {
                        replaceFragment(FilterFragment())
                    }
                }
            }
            main_btn_news -> {
                if(intent.getStringExtra("token")== null) {
                    val intent1 = Intent(applicationContext, PopupActivity::class.java)
                    startActivity(intent1)
                } else {
                    clearSelected()
                    main_btn_news.isSelected = true
                    replaceFragment(BellFragment())
                }
            }
            main_btn_mine -> {
                if (intent.getStringExtra("token") == null) {
                    val intent1 = Intent(applicationContext, PopupActivity::class.java)
                    startActivity(intent1)
                } else {
                    clearSelected()
                    main_btn_mine.isSelected = true
                    replaceFragment(MyFragment())
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val view = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                // 23 버전 이상일 때 상태바 하얀 색상에 회색 아이콘 색상을 설정
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = Color.parseColor("#f2f2f2")
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            window.statusBarColor = Color.BLACK
        }

        val fragmentManager : FragmentManager = supportFragmentManager
        addFragment(HomeFragment())
        main_btn_home.isSelected = true
        val permissionlistener = object : PermissionListener {
            override fun onPermissionGranted() {
                //Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>) {
                Toast.makeText(this@MainActivity, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show()
                //Toast.makeText(this,"권한이 거절되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("권한을 주지 않으면 사용할 수 없습니다.")
                .setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check()
      //  addFragment(HomeFragment())
        /*
        val fragmentManager: FragmentManager = supportFragmentManager

        val homeFragment = HomeFragment()
        val searchFragment = FilterFragment()
        lateinit var cameraFragment: Fragment
        if (Build.VERSION.SDK_INT >= 21) {
            cameraFragment = CamHomeFragment()
        } else {
            cameraFragment = FilterFragment()
        }
        val likedFragment = BellFragment()
        val myFragment = MyFragment()
        fragmentManager.beginTransaction().replace(R.id.main_container, homeFragment).commit()
*/
        if (intent.getStringExtra("token") != null) {
            tokenDriver.tokenDriver.onNext(intent.getStringExtra("token"))
        }

        main_btn_home.setOnClickListener(this)
        main_btn_mine.setOnClickListener(this)
        main_btn_add.setOnClickListener(this)
        main_btn_search.setOnClickListener(this)
        main_btn_news.setOnClickListener(this)
    }

    fun clearSelected() {
        main_btn_home.isSelected = false
        main_btn_add.isSelected = false
        main_btn_mine.isSelected = false
        main_btn_search.isSelected = false
        main_btn_news.isSelected = false

    }

    fun addFragment(fragment : Fragment) : Unit{
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.add(R.id.main_container, fragment)
        transaction.commit()
    } // 변수명부터 넣고 타입 넣음 , unit 은 리턴 타입 없다는 뜻. 따라서 생략해도 됨.

    fun replaceFragment(fragment: Fragment) : Unit{
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.commit()
        // transaction.addToBackStack(null)
    }
/*
        val bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                var fragmentTransaction = fragmentManager.beginTransaction()

                when (item.getItemId()) {
                    R.id.action_home -> {
                        fragmentTransaction.replace(R.id.main_container, homeFragment).commit()
                    }
                        //741dff
                        // do something here
                    R.id.action_search ->
                        fragmentTransaction.replace(R.id.main_container, searchFragment).commit()
                        // do something here
                    R.id.action_camera ->
                        fragmentTransaction.replace(R.id.main_container, cameraFragment).commit()
                        // do something here
                    R.id.action_liked ->
                        fragmentTransaction.replace(R.id.main_container, likedFragment).commit()
                // do something here
                    R.id.action_my ->
                        fragmentTransaction.replace(R.id.main_container, myFragment).commit()
                // do something here
                }
                return true
            }
        })
    }
    */
}


