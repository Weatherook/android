package org.weatherook.weatherook.ui.fragment.camera

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.GalleryRecyclerviewAdapter
import org.weatherook.weatherook.api.glide.GlideApp
import org.weatherook.weatherook.singleton.urlDriver.urlDriver
import java.util.*

class GalleryFragment : Fragment(), View.OnClickListener {

    var myUrls: ArrayList<String> = ArrayList()
    lateinit var galleryRecyclerviewAdapter: GalleryRecyclerviewAdapter

    val imageWidthPixels = 320
    val imageHeightPixels = 240

    override fun onClick(v: View?) {
        //do nothing
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = View.inflate(activity, R.layout.fragment_gallery, null)
        val sizeProvider: ListPreloader.PreloadSizeProvider<Any> = FixedPreloadSizeProvider<Any>(imageWidthPixels, imageHeightPixels)
        val modelProvider: ListPreloader.PreloadModelProvider<Any> = MyPreloadModelProvider()
        val preloader: RecyclerViewPreloader<Any> = RecyclerViewPreloader<Any>(GlideApp.with(this), modelProvider, sizeProvider, 10)

        val galleryRv: RecyclerView = view!!.findViewById(R.id.camhome_gallery_rv)

        val permissionlistener = object : PermissionListener {
            override fun onPermissionGranted() {
                //Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show()
                galleryRv.addOnScrollListener(preloader)
                galleryRv.addOnScrollListener(mScrollListener)
                galleryRv.layoutManager = GridLayoutManager(activity, 3)
                galleryRecyclerviewAdapter = GalleryRecyclerviewAdapter(myUrls, context!!)
                myUrls = getAllShownImagesPath()
                galleryRecyclerviewAdapter.setOnItemClickListener(this@GalleryFragment)
                galleryRv.adapter = galleryRecyclerviewAdapter
                urlDriver.onNext(myUrls.get(0))
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
                Toast.makeText(activity, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(activity)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("권한을 주지 않으면 사용할 수 없습니다.")
                .setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()
        return view
    }

    var mScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            if (!recyclerView!!.canScrollVertically(1)) {
                Log.i("gallery", "top")
                maxUri += 18
                myUrls = getAllShownImagesPath()
                galleryRecyclerviewAdapter.notifyDataSetChanged()
                //Top of list
            } else if (!recyclerView!!.canScrollVertically(1)) {
                //Bottom of list
                Log.i("gallery", "bottom")
            }
        }
    }

    private inner class MyPreloadModelProvider : ListPreloader.PreloadModelProvider<Any> {
        override fun getPreloadItems(position: Int): MutableList<Any> {
            val url = myUrls.get(position)
            if (TextUtils.isEmpty(url)) {
                return Collections.emptyList()
            }
            return Collections.singletonList(url)
        }

        override fun getPreloadRequestBuilder(item: Any): RequestBuilder<*>? {
            return GlideApp.with(
                    this@GalleryFragment.context!!
            ).load(item).override(imageWidthPixels, imageHeightPixels)
        }
    }

    var maxUri: Int = 18

    private fun getAllShownImagesPath(): ArrayList<String> {
        val uri: Uri
        val cursor: Cursor
        val column_index_data: Int
        val column_index_folder_name: Int
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String? = null
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        cursor = context!!.contentResolver.query(uri, projection, null, null, null)
        cursor.moveToLast()

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor.moveToPrevious() && myUrls.size < maxUri) {
            absolutePathOfImage = cursor.getString(column_index_data)

            if (!myUrls.contains(absolutePathOfImage)) {
                myUrls.add(absolutePathOfImage)
                galleryRecyclerviewAdapter.notifyDataSetChanged()
            }
        }
        return myUrls
    }

    override fun onDetach() {
        super.onDetach()

    }
}