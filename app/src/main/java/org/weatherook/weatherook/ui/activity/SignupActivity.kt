package org.weatherook.weatherook.ui.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_signup.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.viewpager.SignupPagerAdapter

class SignupActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            signup_close -> {
                finish()
            }

        }
 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signup_close.setOnClickListener(this)

        val viewPager = findViewById<ViewPager>(R.id.signup_viewPager)
        val adapter = SignupPagerAdapter(supportFragmentManager)

        viewPager.adapter = adapter
    }
}
