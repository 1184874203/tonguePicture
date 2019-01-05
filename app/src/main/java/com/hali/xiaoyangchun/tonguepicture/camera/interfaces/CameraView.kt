package com.hali.xiaoyangchun.tonguepicture.camera.interfaces

interface CameraView {

    fun onResume()

    fun onPause()

    fun takePicture()

    fun takePicture(takePictureCallback: TakePictureCallback)

    fun switchCameraFacing()

    interface TakePictureCallback {
        fun onFinish(data: ByteArray?, facing: Int)
    }
}