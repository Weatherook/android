package org.weatherook.weatherook.api.camera

import android.content.Context
import android.media.Image
import android.util.Log

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import android.net.Uri
import android.content.Intent
import org.weatherook.weatherook.ui.activity.WriteActivity


/**
 * Saves a JPEG [Image] into the specified [File].
 */
internal class ImageSaver(
        private val context: Context,
        /**
         * The JPEG image
         */
        private val image: Image,

        /**
         * The file we save the image into.
         */
        private val file: File
) : Runnable {

    override fun run() {
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        var output: FileOutputStream? = null
        try {
            output = FileOutputStream(file).apply {
                write(bytes)
            }
        } catch (e: IOException) {
            Log.e(TAG, e.toString())
        } finally {
            image.close()
            /*val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val uri = Uri.fromFile(file)
            mediaScanIntent.data = uri'
            context.sendBroadcast(mediaScanIntent)*/
            output?.let {
                try {
                    it.close()
                } catch (e: IOException) {
                    Log.e(TAG, e.toString())
                }
            }
            try {
                val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                val contentUri = Uri.fromFile(file);
                scanIntent.setData(contentUri);
                context.sendBroadcast(scanIntent);
            } catch (e: Exception) {
                e.printStackTrace()
            }
            /*val activity = Intent(context, WriteActivity::class.java)
            activity.putExtra("imageuri",uri)
            context.startActivity(activity)*/
        }
    }

    companion object {
        /**
         * Tag for the [Log].
         */
        private val TAG = "ImageSaver"
    }
}