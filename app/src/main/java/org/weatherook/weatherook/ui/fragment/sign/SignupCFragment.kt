package org.weatherook.weatherook

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function5
import io.reactivex.functions.Function9
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_signup_c.*
import org.w3c.dom.Text
import org.weatherook.weatherook.api.model.SignupModel
import org.weatherook.weatherook.api.network.NetworkService
import org.weatherook.weatherook.singleton.SignupDriver.signupDriver
import org.weatherook.weatherook.ui.activity.SigninActivity

class SignupCFragment : Fragment(), View.OnClickListener {
    var boolArr = arrayOf(false,false,false,false,false,false,false,false,false,false)
    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null
    var styleDriver : BehaviorSubject<Array<Boolean>> = BehaviorSubject.create()

    var style: ArrayList<TextView> = ArrayList()
    override fun onClick(v: View?) {
        when (v) {
            /*signup_join -> {
                //startActivity(Intent(context, SigninActivity::class.java))
                Log.d("temp", "시작")
                signup()
            }*/
        /*    signup_a -> {
                if(!boolArr[0])
                    boolArr[0] = true
                else
                    boolArr[0] = false
            }
            signup_b -> {
                if(!boolArr[1])
                    boolArr[1] = true
                else
                    boolArr[1] = false
            }
            signup_c -> {
                if(!boolArr[2])
                    boolArr[2] = true
                else
                    boolArr[2] = false
            }
            signup_d -> {
                if(!boolArr[3])
                    boolArr[3] = true
                else
                    boolArr[3] = false
            }
            signup_e -> {
                if(!boolArr[4])
                    boolArr[4] = true
                else
                    boolArr[4] = false
            }
            signup_f -> {
                if(!boolArr[5])
                    boolArr[5] = true
                else
                    boolArr[5] = false
            }
            signup_g -> {
                if(!boolArr[6])
                    boolArr[6] = true
                else
                    boolArr[6] = false
            }
            signup_h -> {
                if(!boolArr[7])
                    boolArr[7] = true
                else
                    boolArr[7] = false
            }
            signup_i -> {
                if(!boolArr[8])
                    boolArr[8] = true
                else
                    boolArr[8] = false
            }
            signup_j -> {
                if(!boolArr[9])
                    boolArr[9] = true
                else
                    boolArr[9] = false
            }
            */


        }
        styleDriver.onNext(boolArr)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_signup_c, container, false)

        return v
    }

    val canSubmit = arrayOf(false, false)

    val styleList = ArrayList<String>()
    override fun onStart() {
        super.onStart()

        //signup_join.setOnClickListener(this)

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

        /*var index = 0
        for (st in style) {
            st.setOnClickListener {
                if (!st.isSelected) {
                    st.isSelected = true
                    boolArr[index] = true
                    st.setTextColor(resources.getColor(android.R.color.white))
                } else {
                    st.isSelected = false
                    boolArr[index] = false
                    st.setTextColor(Color.parseColor("#aaaaaa"))
                }
            }
            index = index + 1
        }*/
        for(st in style){
            st.setOnClickListener {
                if(!st.isSelected){
                    st.isSelected = true
                    st.setTextColor(resources.getColor(android.R.color.white))
                    if(!styleList.contains(st.text.toString())){
                        styleList.add(st.text.toString())
                        if(styleList.size!=0){
                            signupDriver.onNext(true)
                        }
                        Log.i("stylelist",styleList.toString())
                    }
                }else{
                    st.isSelected=false
                    st.setTextColor(Color.parseColor("#aaaaaa"))
                    if(styleList.contains(st.text.toString())){
                        styleList.remove(st.text.toString())
                        Log.i("stylelist",styleList.toString())
                        if(styleList.size==0){
                            signupDriver.onNext(false)
                        }
                    }
                }
            }
        }
        val signupAgeObservable = RxTextView.textChanges(signup_age)

//        val signupAObservable = RxTextView.textChanges(signup_a)
//        val signupBObservable = RxTextView.textChanges(signup_b)
//        val signupCObservable = RxTextView.textChanges(signup_c)
//        val signupDObservable = RxTextView.textChanges(signup_d)
//        val signupEObservable = RxTextView.textChanges(signup_e)
//        val signupFObservable = RxTextView.textChanges(signup_f)
//        val signupGObservable = RxTextView.textChanges(signup_g)
//        val signupHObservable = RxTextView.textChanges(signup_h)
//        val signupIObservable = RxTextView.textChanges(signup_i)
//        val signupJObservable = RxTextView.textChanges(signup_j)

        signupAgeObservable.subscribe {
            val temp = it.toString()
            Log.d("rxtest", temp)
            if (temp.length == 0 || !checkInputOk(temp)) {
                canSubmit[0] = false
                signupDriver.onNext(false)
            } else {
                canSubmit[0] = true
                c_age=Integer.parseInt(it.toString())
                if (canSubmit[0]&&styleList.size!=0) {// && canSubmit[1]
                    Log.d("tag", "===================합격===================")
                    signupDriver.onNext(true)
                }
            }
        }

        /*
        styleDriver

        val check : Observable<Boolean> = Observable.combineLatest(
                styleDriver,
                signupAgeObservable,
                BiFunction { t1, t2 ->  }
        )
*/

        /*
        val style: Observable<Boolean> = Observable.combineLatest(
                signupAObservable,
                signupBObservable,
                signupCObservable,
                signupDObservable,
                signupEObservable,
                signupFObservable,
                signupGObservable,
                signupHObservable,
                signupIObservable,
                signupJObservable,

                Func10 { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10 ->
                    if (t1.toString().length < 6) {
                        false
                    } else {
                        t1.toString().equals(t2.toString())
                    }
                }
        )
        */
    }

    fun checkInputOk(nameInput: String): Boolean {
        var chrInput: Char

        for (i in 0 until nameInput.length) {
            chrInput = nameInput[i]
            if (chrInput.toInt() >= 0x21 && chrInput.toInt() <= 0x7E) {

            } else {
                return false
            }
        }
        return true
    }

    fun signup() {
        //val id = signin_id_tv.text.toString()
        //val pw = signin_pw_tv.text.toString()
//        val styleList = ArrayList<String>()
//        styleList.add("댄디")
//        styleList.add("빈티지")

        /*val styleList = ArrayList<String>()
        styleList.add(signup_a.text.toString())
        styleList.add("댄디")*/

        val signupModel = SignupModel("appjamidda","we0001", "여",22,162,50, styleList)
        Log.d("temp", Gson().toJson(signupModel))
        val call = networkService.postSignup(c_id!!,c_pw!!,c_age!!,c_gender!!,c_height!!,c_height!!,styleList)
        disposable = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ success ->
                    Log.d("temp", success.toString())
                    //startActivity(Intent(context, SigninActivity::class.java))
                }, { fail ->

                    Log.d("temp", fail.toString())
                    Log.d("temp",fail.message.toString())
                    Toast.makeText(context, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                })
    }
    var c_id :String ?=null
    var c_pw :String ?=null
    var c_gender :String ?=null
    var c_height :Int ?=null
    var c_weight :Int ?=null
    var c_age : Int?= null

    fun displayReceivedData1(id : String,pw:String, gender : String, height : Int, weight : Int){
        c_id = id
        c_pw = pw
        c_gender = gender
        c_height = height
        c_weight = weight
        Log.i("c_id",c_id)
        Log.i("c_pw",c_pw)
        Log.i("c_gender",c_gender)
        Log.i("c_height",c_height.toString())
        Log.i("c_weight",c_weight.toString())
    }
}