package org.weatherook.weatherook

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_signup_b.*

class SignupBFragment: Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
       when(v){
           signup_women -> {
               signup_women.isSelected = true
               signup_man.isSelected = false
           }

           signup_man -> {
               signup_women.isSelected = false
               signup_man.isSelected = true
           }
       }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_signup_b, container, false)

        return v
    }

    override fun onStart() {
        super.onStart()

        signup_women.isSelected = true

        signup_women.setOnClickListener(this)
        signup_man.setOnClickListener(this)

    }

}