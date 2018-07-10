package org.weatherook.weatherook.ui.fragment.camera

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.weatherook.weatherook.R

class CameraFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = View.inflate(activity, R.layout.fragment_camera, null)
        return view
    }
}