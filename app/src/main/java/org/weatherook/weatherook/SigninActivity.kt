package org.weatherook.weatherook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_signin.*
import org.weatherook.weatherook.ui.activity.MainActivity
import kotlin.jvm.java

class SigninActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){

            signin_btn -> {
                val intent1 = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent1)
            }
            signup_btn -> {
                val intent = Intent(applicationContext, SignupActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        signup_btn.setOnClickListener(this)
        signin_btn.setOnClickListener(this)
    }
}