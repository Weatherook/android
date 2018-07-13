package org.weatherook.weatherook.ui.fragment.camera

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP
import kotlinx.android.synthetic.main.fragment_camhome.*
import kotlinx.android.synthetic.main.fragment_camhome.view.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.api.camera.CameraActivity
import org.weatherook.weatherook.api.camera.sticker.StickerImageView
import org.weatherook.weatherook.singleton.urlDriver


class CamHomeFragment : Fragment() , View.OnClickListener{

    override fun onClick(v: View?) {
        //do nothing
    }
    var stickerTabList = ArrayList<ImageView>()
    var stickerIDList = ArrayList<Int>()
    var stickerList = ArrayList<StickerImageView>()
    var onStickerList : ArrayList<StickerImageView>?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = View.inflate(activity, R.layout.fragment_camhome, null)

        val camhome_cam_txt : TextView = view.findViewById(R.id.camhome_cam_txt)

        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.camhome_bottom_container, GalleryFragment()).commit()

        camhome_cam_txt.setOnClickListener {
            val intent = Intent(activity, CameraActivity::class.java)
            startActivity(intent)
        }

        val camhome_next: TextView = view.findViewById(R.id.camhome_next)
        camhome_next.setOnClickListener {
            val linearlayout: LinearLayout = view.findViewById(R.id.linearLayout)
            linearlayout.visibility = View.GONE
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.camhome_bottom_container, WriteFragment()).commit()
        }

        val camhome_cancel: TextView = view.findViewById(R.id.camhome_cancel)
        camhome_cancel.setOnClickListener {
            val linearlayout: LinearLayout = view.findViewById(R.id.linearLayout)
            linearlayout.visibility = View.VISIBLE
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.camhome_bottom_container, GalleryFragment()).commit()
        }
        urlDriver.urlDriver.subscribe {
            try {
                if(it!=null){
                    Log.i("driver",it)
                    //GlideApp.with(activity!!).load(it).into(camhome_container)
                    camhome_container.setImage(ImageSource.uri(it))
                    camhome_container.setMinimumScaleType(SCALE_TYPE_CENTER_CROP)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val camhome_container: SubsamplingScaleImageView = view.findViewById(R.id.camhome_container)
        camhome_container.setOnClickListener {
            for(i in stickerList){
                i.shouldShowButtons(false)
            }
        }

        val canvas : FrameLayout = view.findViewById(R.id.sticker_frame)

        stickerTabList.add(view.sticker1)
        stickerTabList.add(view.sticker2)
        stickerTabList.add(view.sticker3)
        stickerTabList.add(view.sticker4)
        stickerTabList.add(view.sticker5)
        stickerTabList.add(view.sticker6)
        stickerTabList.add(view.sticker7)
        stickerTabList.add(view.sticker8)
        stickerTabList.add(view.sticker9)

        stickerIDList.add(R.drawable.camera_sticker)
        stickerIDList.add(R.drawable.camera_sticker2)
        stickerIDList.add(R.drawable.camera_sticker3)
        stickerIDList.add(R.drawable.camera_sticker4)
        stickerIDList.add(R.drawable.camera_sticker5)
        stickerIDList.add(R.drawable.camera_sticker6)
        stickerIDList.add(R.drawable.camera_sticker7)
        stickerIDList.add(R.drawable.camera_sticker8)
        stickerIDList.add(R.drawable.camera_sticker9)
        for(i in stickerTabList){
            i.setOnClickListener {
                val iv_sticker = StickerImageView(context!!)
                //iv_sticker.setImageDrawable(i.drawable)
                stickerList.add(iv_sticker)
                iv_sticker.iv_main!!.setOnClickListener{iv_sticker.shouldShowButtons(true) }
                iv_sticker.setImageResource(stickerIDList.get(stickerTabList.indexOf(i)))
                canvas.addView(iv_sticker)
                view.camhome_sticker_container.visibility=View.INVISIBLE
            }
        }
        view.sticker_btn.setOnClickListener {
            view.camhome_sticker_container.visibility=View.VISIBLE
        }

        /*val iv_sticker = StickerImageView(context!!)
        iv_sticker.setImageDrawable(getResources().getDrawable( R.drawable.moon))
        canvas.addView(iv_sticker)*/

        return view
    }
}