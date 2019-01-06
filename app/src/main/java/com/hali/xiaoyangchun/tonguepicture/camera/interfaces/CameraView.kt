package com.hali.xiaoyangchun.tonguepicture.camera.interfaces

import android.graphics.Bitmap

interface CameraView {

    fun onResume()

    fun onPause()

    fun takePicture()

    fun takePicture(takePictureCallback: TakePictureCallback)

    fun switchCameraFacing()

    interface TakePictureCallback {
        fun onFinish(bitmap: Bitmap)
    }
}