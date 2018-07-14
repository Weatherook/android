package org.weatherook.weatherook.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_write.*
import org.weatherook.weatherook.R
import android.util.Log
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.weatherook.weatherook.api.network.NetworkService
import org.weatherook.weatherook.singleton.urlDriver
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class WriteActivity : AppCompatActivity(),View.OnClickListener {
    override fun onClick(v: View?) {8
        when (v) {
        /* write_public ->{
                write_public.isSelected = true
            }*/
        }
    }

    var style: ArrayList<TextView> = ArrayList()

    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null

    var token : String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MSwiaWF0IjoxNTMxMzMyMzk0LCJleHAiOjE1MzM5MjQzOTR9.DdpDh2ob-Qg7S9OAQPuoLz4dpUpgMuUuJRwZTbvE3Rk"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        write_date_cal.text = intent.getStringExtra("date")
        write_date_txt.text = intent.getStringExtra("location")
        write_temp_txt.text = intent.getStringExtra("temp")
        write_weather_txt.text = intent.getStringExtra("weather")

        style.add(write_style1)
        style.add(write_style2)
        style.add(write_style3)
        style.add(write_style4)
        style.add(write_style5)
        style.add(write_style6)

        my_setting_btn.setOnClickListener {
            val options = BitmapFactory.Options()
            //options.inSampleSize = 4
            bitmap = BitmapFactory.decodeFile(intent.getStringExtra("url"), options)
            if(bitmap==null){
                Log.i("bitmap","null")
            }
            /*val call2 = networkService.postBoard(token!!,bitmap!!,"dafsd",37f,126f,"PUBLIC", arrayListOf("댄디"),0,10,20,"2018. 7. 13 오후 11:59:00")
            disposable = call2.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            { WeatherModel ->

                            }, { fail -> Log.i("writeActivity", fail.message) })
            Log.i("bitmap url",intent.getStringExtra("url")+"////"+imageFile)*/
            postBoard()
            //persistImage(bitmap!!,"dfaf.jpg")
        }

        write_style1.setOnClickListener {
            if (!write_style1.isSelected) {
                write_style1.isSelected = true
                write_style1.setTextColor(resources.getColor(android.R.color.white))
            } else {
                write_style1.isSelected = false
                write_style1.setTextColor(resources.getColor(R.color.colorAccent))
            }
        }


        for (st in style) {
            st.setOnClickListener {
                if (!st.isSelected) {
                    st.isSelected = true
                    st.setTextColor(resources.getColor(android.R.color.white))
                } else {
                    st.isSelected = false
                    st.setTextColor(resources.getColor(R.color.gray))
                }
            }
        }
    }
    var bitmap :Bitmap?=null

    override fun onStart() {
        super.onStart()
        write_public.setOnClickListener {
            if (!write_public.isSelected) {
                write_public.isSelected = true
            } else {
                write_public.isSelected = false
            }
        }
    }

    var imageFile :File ?=null
    fun postBoard(){
        //val options = BitmapFactory.Options()

        /*var input: InputStream? = null // here, you need to get your context.
        try {
            input = contentResolver.openInputStream(Uri.parse(intent.getStringExtra("url")))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }*/

        val options = BitmapFactory.Options()
        if(intent.getStringExtra("imageuri")==null){
            val bitmap = BitmapFactory.decodeFile(intent.getStringExtra("url"), options)
        }else{
            val bitmap = BitmapFactory.decodeFile(intent.getStringExtra("imageuri"), options)
        }
        //val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
        val baos = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
        val photo = File(intent.getStringExtra("url"))
        val image = MultipartBody.Part.createFormData("board_img", photo.name, photoBody)
        val desc = RequestBody.create(MediaType.parse("text/plain"),
                editText.text.toString())
        val auth = RequestBody.create(MediaType.parse("text/plain"),
                "PRIVATE")
        val date = RequestBody.create(MediaType.parse("text/plain"),
                SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.KOREA).format(Calendar.getInstance().time))
        Log.i("date", SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.KOREA).format(Calendar.getInstance().time))
        /*val stylelist = RequestBody.create(MediaType.parse("text/plain"),
                arrayListOf("댄디").toString())*/
        val weather = RequestBody.create(MediaType.parse("text/plain"),
                0.toString())
        val tempmin = RequestBody.create(MediaType.parse("text/plain"),
                10.toString())
        val tempmax = RequestBody.create(MediaType.parse("text/plain"),
                20.toString())


        val postBoardResponse = networkService.postBoard(token!!, image, desc,auth,date, null,weather,tempmin,tempmax)
        disposable = postBoardResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        { WeatherModel ->
                            finish()
                        }, { fail -> Log.i("writeActivity", fail.message) })

    }
}
