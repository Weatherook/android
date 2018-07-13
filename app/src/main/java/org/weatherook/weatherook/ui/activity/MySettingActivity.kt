package org.weatherook.weatherook.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_setting.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.weatherook.weatherook.R
import org.weatherook.weatherook.api.glide.GlideApp
import org.weatherook.weatherook.api.network.NetworkService
import org.weatherook.weatherook.item.UserSettingUpdateData
import org.weatherook.weatherook.singleton.tokenDriver
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class MySettingActivity : AppCompatActivity(), View.OnClickListener {

    private val REQ_CODE_SELECT_IMAGE = 100
   // private  var image : MultipartBody.Part? = null
    lateinit var data : Uri
    var mysetting_btn : ArrayList<TextView> = ArrayList()

    private var image : MultipartBody.Part? = null

    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null

    var token : String ?= null

var p : RequestBody?= null
    override fun onClick(p0: View?) {
        when(p0){
            my_setting_com_btn -> {
                updateProfile()
                finish()
                //일단 updateProfile
            }
            my_setting_change_profile -> {
                changeImage()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenDriver.tokenDriver.subscribe{
            token = it
            Log.i("grid", token)
        }
        if (token != null) {
            val call = networkService.getUserSetting(token!!)
            Log.d("tag", "============================token : " + token + "=============================")
            disposable = call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            { success ->
                                for (i in 0..success.data.showUserResult.size - 1) {
                                    GlideApp.with(applicationContext).load(success.data.showUserResult[i].userImg).into(my_setting_profile_img)
                                    my_setting_id.setText(success.data.showUserResult[i].userId)
                                    my_setting_gul.setText(success.data.showUserResult[i].userDesc)
                                    my_setting_age.setText(success.data.showUserResult[i].userAge.toString())
                                    my_setting_tall.setText(success.data.showUserResult[i].userHeight.toString())
                                    my_setting_weight.setText(success.data.showUserResult[i].userWeight.toString())
                                    my_setting_gender.setText(success.data.showUserResult[i].userGender)
                                }
                                for(i in 0..success.data.style.size-1){
                                    for(st in mysetting_btn ){
                                        if(st.text.equals(success.data.style[i])){
                                            st.isSelected = true
                                            st.setTextColor(resources.getColor(android.R.color.white))
                                        }

                                    }
                                }
                            }, { fail -> Log.i("urls_failed", fail.message) })

        }
        setContentView(R.layout.activity_my_setting)
        
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //if(ApplicationController.getInstance().is)
                    this.data = data!!.data
                    Log.v("이미지", this.data.toString())

                    val options = BitmapFactory.Options()

                    var input: InputStream? = null // here, you need to get your context.
                    try {
                        input = contentResolver.openInputStream(this.data)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                    val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                 //   val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                    val photo = File(this.data.toString()) // 가져온 파일의 이름을 알아내려고 사용합니다

                    Log.d("tag", "==========================" + this.data.toString() + "=========================")
                    val photoBody = RequestBody.create(MediaType.parse("multipart/form-data"), baos.toByteArray())
                    ///RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
                    // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
                    p = RequestBody.create(MediaType.parse("multipart/form-data"), baos.toByteArray())

                    //image = MultipartBody.Part.createFormData("photo", photo.name, photoBody)

                    //body = MultipartBody.Part.createFormData("image", photo.getName(), profile_pic);

                    Glide.with(this)
                            .load(data.data)
                            .into(my_setting_profile_img)

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }
    fun changeImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent,REQ_CODE_SELECT_IMAGE) //실행한 엑티비티에서 받아옴
    }

    override fun onStart() {
        super.onStart()
        my_setting_change_profile.setOnClickListener(this)
        my_setting_com_btn.setOnClickListener(this)

        mysetting_btn.add(my_setting_btn1)
        mysetting_btn.add(my_setting_btn2)
        mysetting_btn.add(my_setting_btn3)
        mysetting_btn.add(my_setting_btn4)
        mysetting_btn.add(my_setting_btn5)
        mysetting_btn.add(my_setting_btn6)
        mysetting_btn.add(my_setting_btn7)
        mysetting_btn.add(my_setting_btn8)
        mysetting_btn.add(my_setting_btn9)
        mysetting_btn.add(my_setting_btn10)

        for(st in mysetting_btn){
            st.setOnClickListener {
                if(!st.isSelected){
                    st.isSelected = true
                    st.setTextColor(resources.getColor(android.R.color.white))
                }else{
                    st.isSelected = false
                    st.setTextColor(resources.getColor(R.color.gray))
                }
            }
        }
    }

    fun updateProfile(){

        var desc = my_setting_gul.text.toString()
        var gender = my_setting_gender.text.toString()
        var age = my_setting_age.text.toString().toInt()
        var height = my_setting_tall.text.toString().toInt()
        var weight = my_setting_weight.text.toString().toInt()

        var styleList = ArrayList<String>()

   //     styleList.add("빈티지")
      //  styleList.add("그 외")
        for(st in mysetting_btn){
            if(st.isSelected){
                styleList.add(st.text.toString())
            }
        }
        if (token != null) {
            val call = networkService.putUserSetting(token!!, UserSettingUpdateData( desc, gender, age, p, height,weight,styleList))
            disposable = call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            {success->
                                Log.i("tag","풋 성공========================================================")
                            }, { fail -> Log.i("TodayFragment", fail.message) })

        }


    }
}
