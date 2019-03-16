package com.hali.xiaoyangchun.tonguepicture.interfaces

import com.hali.xiaoyangchun.tonguepicture.camera.CameraView

interface CameraAction {
    fun onPictureTaken(cameraView: CameraView?, data: ByteArray?)
}