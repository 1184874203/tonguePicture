package com.hali.xiaoyangchun.tonguepicture.camera.presenter

import android.graphics.Matrix
import android.hardware.Camera
import com.hali.xiaoyangchun.tonguepicture.camera.interfaces.CameraView
import com.hali.xiaoyangchun.tonguepicture.utils.FileUtil

class CameraPresenter {
    private var cameraView: CameraView? = null
    private var fileName: String? = null

    fun attachView(cameraView: CameraView) {
        this.cameraView = cameraView
    }

    fun detachView() {
        this.cameraView = null
    }

    fun isViewAttached() = cameraView != null

    fun switchCameraFacing() {
        if (isViewAttached()) {
            cameraView!!.switchCameraFacing()
        }
    }

    fun setFileName(fileName: String) {
        this.fileName = FileUtil.picturePath + fileName
    }

    fun takePicture() {
        cameraView!!.takePicture(object : CameraView.TakePictureCallback {
            override fun onFinish(data: ByteArray?, facing: Int) {
                var matrix = Matrix()
                if (facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    matrix.setRotate(90f)
                } else {
                    matrix.setRotate(-90f)
                }
                FileUtil.savePicture(data, fileName, matrix)
            }
        })
    }

}