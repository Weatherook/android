package org.weatherook.weatherook

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_signup_b.*
import kotlinx.android.synthetic.main.fragment_signup_c.*
import org.weatherook.weatherook.ui.activity.SigninActivity

class SignupCFragment : Fragment(), View.OnClickListener {

    var style : ArrayList<TextView> = ArrayList()
    override fun onClick(v: View?) {

        when(v){
            signup_join -> {
                startActivity(Intent(context, SigninActivity::class.java))
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_signup_c, container, false)

        return v
    }

    override fun onStart() {
        super.onStart()

        signup_join.setOnClickListener(this)

        style.add(signup_a)
        style.add(signup_b)
        style.add(signup_c)
        style.add(signup_d)
        style.add(signup_e)
        style.add(signup_f)
        style.add(signup_g)
        style.add(signup_h)
        style.add(signup_i)
        style.add(signup_j)

        for(st in style){
            st.setOnClickListener {
                if(!st.isSelected){
                    st.isSelected = true
                    st.setTextColor(resources.getColor(android.R.color.white))
                }else{
                    st.isSelected = false
                    st.setTextColor(Color.parseColor("#aaaaaa"))
                }
            }
        }
    }
}