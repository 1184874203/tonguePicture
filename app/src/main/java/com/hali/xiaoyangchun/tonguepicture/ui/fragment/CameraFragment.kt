package com.hali.xiaoyangchun.tonguepicture.ui.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.ImageButton
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.camera.CameraView
import com.hali.xiaoyangchun.tonguepicture.interfaces.CameraAction
import com.hali.xiaoyangchun.tonguepicture.presenter.CameraPresenter
import com.hali.xiaoyangchun.tonguepicture.presenter.CropImgPresenter
import com.hali.xiaoyangchun.tonguepicture.ui.activity.SingleFAHelper
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseFragment
import com.hali.xiaoyangchun.tonguepicture.utils.FileUtil
import com.hali.xiaoyangchun.tonguepicture.view.simplecropview.CropImageView
import com.hali.xiaoyangchun.tonguepicture.view.simplecropview.callback.CropCallback

class CameraFragment : BaseFragment(), CameraAction, CropCallback {

    companion object {
        val CAMERAFRAGMENT_TAG = "CameraFragment_TAG"
    }

    private lateinit var cameraPresenter: CameraPresenter
    private lateinit var mCameraView: CameraView

    private lateinit var cropImgPresenter: CropImgPresenter
    private lateinit var mCropImageView: CropImageView

    private var imagePath: String? = null

    override fun getLayoutId() = R.layout.fragment_camera

    override fun initViews() {
        cameraPresenter = CameraPresenter(activity!!)
        mCameraView = findView(R.id.camera)
        cropImgPresenter = CropImgPresenter(activity!!)
        mCropImageView = findView(R.id.cropImageView)
    }

    override fun initData() {
        findView<FloatingActionButton>(R.id.take_picture).setOnClickListener{
            cameraPresenter.takePicture()
        }
        findView<View>(R.id.facing_switch).setOnClickListener{
            cameraPresenter.switchCameraFacing()
        }
        findView<ImageButton>(R.id.buttonDone).setOnClickListener {
            cropImgPresenter.cropImage()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cropImgPresenter.attachView(mCropImageView, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        cameraPresenter.attachView(mCameraView)
        cameraPresenter.mCameraAction = this
        cropImgPresenter.cropCallback = this
    }

    override fun onPause() {
        super.onPause()
        cameraPresenter.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        cropImgPresenter.saveInstanceState(outState)
    }

    override fun onPictureTaken(cameraView: CameraView?, data: ByteArray?) {
        if (data == null) return
        imagePath = FileUtil.savePicture(data) {
            cropImgPresenter.loadImage(it)
            mCropImageView.visibility = View.VISIBLE
        }
    }

    override fun onSuccess(cropped: Bitmap?) {
        FileUtil.saveFile(cropped!!, imagePath!!) {
            SingleFAHelper.gotoTonguePicCommitFragment(activity!!, imagePath!!)
        }
    }

    override fun onError(e: Throwable?) {

    }
}