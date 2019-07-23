package com.hali.xiaoyangchun.tonguepicture.ui.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
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

    private val REQUEST_CODE_PICK = 0x0001

    protected lateinit var progressDialog: Dialog

    override fun getLayoutId() = R.layout.fragment_camera

    override fun initViews() {
        cameraPresenter = CameraPresenter(activity!!)
        mCameraView = findView(R.id.camera)
        cropImgPresenter = CropImgPresenter(activity!!)
        mCropImageView = findView(R.id.cropImageView)
        progressDialog = Dialog(mActivity, R.style.progress_dialog)
    }

    override fun initData() {
        findView<FloatingActionButton>(R.id.take_picture).setOnClickListener{
            cameraPresenter.takePicture()
        }
        findView<View>(R.id.facing_switch).setOnClickListener{
            cameraPresenter.switchCameraFacing()
        }
        findView<ImageButton>(R.id.buttonDone).setOnClickListener {
            showProgressDialog()
            cropImgPresenter.cropImage()
        }
        findView<ImageButton>(R.id.buttonPickImage).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_CODE_PICK)
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

    fun showProgressDialog() {
        progressDialog.setContentView(R.layout.dialog_progress_layout)
        progressDialog.setCancelable(false)
        progressDialog.findViewById<TextView>(R.id.tv_progressmsg).setText("处理中")
        progressDialog.window.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.show()
    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun onPictureTaken(cameraView: CameraView?, data: ByteArray?) {
        if (data == null) return
        imagePath = FileUtil.savePicture(data) {
            cropImgPresenter.loadImage(it)
            mCropImageView.visibility = View.VISIBLE
        }
        findView<ImageButton>(R.id.buttonDone).visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PICK) {
            val uri = data?.data
            cropImgPresenter.loadImage(uri!!)
            mCropImageView.visibility = View.VISIBLE
            findView<ImageButton>(R.id.buttonDone).visibility = View.VISIBLE
            imagePath = FileUtil.getRealFilePathFromUri(mActivity, uri)
        }
    }

    override fun onSuccess(cropped: Bitmap?) {
        FileUtil.saveFile(cropped!!, imagePath!!) {
            hideProgressDialog()
            SingleFAHelper.gotoTonguePicCommitFragment(activity!!, imagePath!!)
            activity!!.finish()
        }
    }

    override fun onError(e: Throwable?) {

    }
}