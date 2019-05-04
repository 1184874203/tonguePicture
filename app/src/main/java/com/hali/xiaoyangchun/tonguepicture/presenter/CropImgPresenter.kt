package com.hali.xiaoyangchun.tonguepicture.presenter

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.net.Uri
import android.os.Bundle
import com.hali.xiaoyangchun.tonguepicture.view.simplecropview.CropImageView
import com.hali.xiaoyangchun.tonguepicture.view.simplecropview.callback.CropCallback
import com.hali.xiaoyangchun.tonguepicture.view.simplecropview.callback.LoadCallback
import java.io.File

class CropImgPresenter(private var mContext: Activity) {

    private val KEY_FRAME_RECT = "FrameRect"
    private val KEY_SOURCE_URI = "SourceUri"

    private var mCropView: CropImageView? = null
    private var mCompressFormat = Bitmap.CompressFormat.JPEG
    private var mFrameRect: RectF? = null
    private var mSourceUri: Uri? = null

    fun attachView(cropView: CropImageView?, bundle: Bundle?) {
        if (cropView == null) return
        this.mCropView = cropView
        if (bundle != null) {
            mFrameRect = bundle.getParcelable(KEY_FRAME_RECT)
            mSourceUri = bundle.getParcelable(KEY_SOURCE_URI)
        }
    }

    fun saveInstanceState(outState: Bundle) {
        outState.putParcelable(KEY_FRAME_RECT, mCropView?.actualCropRect)
        outState.putParcelable(KEY_SOURCE_URI, mCropView?.sourceUri)
    }

    fun loadImage(file: File) {
        mSourceUri = createTempUri(file)
        mCropView!!.load(mSourceUri)
                .initialFrameRect(mFrameRect)
                .useThumbnail(true)
                .execute(loadCallback)
    }

    fun loadImage(uri: Uri) {
        mSourceUri = uri
        mCropView!!.load(mSourceUri)
                .initialFrameRect(mFrameRect)
                .useThumbnail(true)
                .execute(loadCallback)
    }

    private fun createTempUri(file: File): Uri {
        return Uri.fromFile(file)
    }

    fun cropImage() {
        mCropView!!.crop(mSourceUri).execute(cropCallback)
    }

    var loadCallback: LoadCallback? = null
    var cropCallback: CropCallback? = null
}