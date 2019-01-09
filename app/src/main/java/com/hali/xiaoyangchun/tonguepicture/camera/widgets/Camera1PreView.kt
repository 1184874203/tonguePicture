package com.hali.xiaoyangchun.tonguepicture.camera.widgets

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.hardware.Camera
import android.util.AttributeSet
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.hali.xiaoyangchun.tonguepicture.camera.interfaces.CameraView
import java.io.IOException
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.ArrayList

class Camera1PreView : SurfaceView, CameraView, SurfaceHolder.Callback {

    companion object {
        private val TAG = javaClass.simpleName
    }

    private lateinit var mContext: Context
    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defstyleAttr: Int): super(context, attrs, defstyleAttr) {
        mContext = context
        initCamera()
    }

    var mCamera: Camera? = null
    var mCameraCount = 1
    var mCurrentCameraFacing = Camera.CameraInfo.CAMERA_FACING_FRONT
    lateinit var mSurfaceHolder: SurfaceHolder
    val isPictureCaptureInProgess = AtomicBoolean(false)
    var takePictureCallback: CameraView.TakePictureCallback? = null
    var mCameraParameters: Camera.Parameters? = null
    val mPreviewSizes = ArrayList<AspectRatio>()
    val mPictureSizes = ArrayList<AspectRatio>()
    var mAspectRatio: AspectRatio? = null
    val DEFAULT_ASPECT_RATIO = AspectRatio(3, 4)
    var mDisplayOrientation: Int = 0
    var mRatioWidth = 0
    var mRatioHeight = 0

    fun initCamera() {
        mCamera = getCameraInstance()
        if (null == mCamera)
            return
        mCameraCount = Camera.getNumberOfCameras()
        mSurfaceHolder = holder
        mSurfaceHolder.addCallback(this)
        mSurfaceHolder.setKeepScreenOn(true)
        setOnClickListener({
            if (mCamera != null) {
                mCamera!!.autoFocus(null)
            }
        })
    }

    override fun onResume() {
        if (mCamera == null) {
            mCamera = getCameraInstance()
        }
    }

    override fun onPause() {
        releaseCamera()
    }

    override fun takePicture() {
        if (mCamera == null) {
            throw IllegalStateException("Camera is not ready. call start() before takePicture()")
        }
        takePictureInternal()
    }

    override fun takePicture(callback: CameraView.TakePictureCallback) {
        if (mCamera == null) {
            throw IllegalStateException("Camera is not ready. call start() before takePicture()")
        }
        takePictureCallback = callback
        takePictureInternal()
    }

    private fun takePictureInternal() {
        if (!isPictureCaptureInProgess.get()) {
            isPictureCaptureInProgess.set(true)
            mCamera!!.takePicture(null, null, mPictureCallback)
        }
    }

    override fun switchCameraFacing() {
        if (mCameraCount > 1) {
            mCurrentCameraFacing =
                    if (mCurrentCameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK)
                        Camera.CameraInfo.CAMERA_FACING_FRONT
                    else Camera.CameraInfo.CAMERA_FACING_BACK
        }
    }

    fun setAspectRatio(width: Int, height: Int) {
        if (width < 0 || height < 0) {
            throw IllegalArgumentException("size cannot be negative")
        }
        mRatioWidth = width
        mRatioHeight = height
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        if (mRatioWidth != 0 && mRatioHeight != 0) {
            if (width < height * mRatioWidth / mRatioHeight) {
                setMeasuredDimension(width, width * mRatioHeight / mRatioWidth)
            } else {
                setMeasuredDimension(height * mRatioWidth / mRatioHeight, height)
            }
        }
    }

    private fun getCameraInstance(): Camera {
        var camera: Camera? = null
        try {
            camera = Camera.open(mCurrentCameraFacing)
        } catch (e: Exception) {
            Log.e(TAG, "error open camera1(${mCurrentCameraFacing}) : ${e.message}")
        }
        try {
            if (camera == null) {
                camera = Camera.open()
            }
        } catch (e: Exception) {
            Log.e(TAG, "error open camera1() : ${e.message}")
        }
        return camera!!
    }

    private fun releaseCamera() {
        if (mCamera != null) {
            mCamera!!.stopPreview()
            mCamera!!.release()
            mCamera = null
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Log.i(TAG, "surfaceCreated")
        startPreview(holder)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        Log.i(TAG, "surfaceChanged format:$format, width:$width, height:$height, ")
        if (mSurfaceHolder.surface == null) {
            return
        }
        try {
            mCamera!!.stopPreview()
            startPreview(holder)
            setCameraParamters(width, height)
        } catch (e: Exception) {
            Log.e(TAG, "error starting camera1 preview in surfaceChanged: ${e.message}")
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Log.i(TAG, "surfaceDestroyed")
        releaseCamera()
    }

    private fun startPreview(holder: SurfaceHolder?) {
        if (mCamera == null) {
            mCamera = getCameraInstance()
        }
        try {
            mCamera!!.setPreviewDisplay(holder)
            mCamera!!.setDisplayOrientation(calcDisplayOrientation(mCurrentCameraFacing))
            mCamera!!.startPreview()
        } catch (e: IOException) {
            Log.e(TAG, "error setting camera1 preview: ${e.message}")
        }
    }

    private fun calcDisplayOrientation(cameraId: Int): Int {
        var info = Camera.CameraInfo()
        Camera.getCameraInfo(cameraId, info)
        var rotation = (mContext as Activity).windowManager.defaultDisplay.rotation
        var degress = 0
        when (rotation) {
            Surface.ROTATION_0 -> degress = 0
            Surface.ROTATION_90 -> degress = 90
            Surface.ROTATION_180 -> degress = 180
            Surface.ROTATION_270 -> degress = 270
        }
        mDisplayOrientation = degress
        var result: Int
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degress) % 360
            result = (360 - result) % 360
        } else {
            result = (info.orientation - degress + 360) % 360
        }
        return result
    }

    private fun chooseAspectRatio(): AspectRatio? {
        var r: AspectRatio? = null
        for (ratio in mPreviewSizes) {
            r = ratio
            if (ratio.equals(DEFAULT_ASPECT_RATIO)) {
                return ratio
            }
        }
        return r
    }

    private fun chooseOptimalSize(sizes: SortedSet<AspectRatio>, surfaceWidth: Int, surfaceHeight: Int): AspectRatio? {
        var desizeWidth: Int
        var desizeHeight: Int
        if (isLandscape(mDisplayOrientation)) {
            desizeHeight = surfaceWidth
            desizeWidth = surfaceHeight
        } else {
            desizeHeight = surfaceHeight
            desizeWidth = surfaceWidth
        }
        var result: AspectRatio? = null
        for (size in sizes) {
            if (desizeWidth <= size.width && desizeHeight <= size.height) {
                return size
            }
            result = size
        }
        return result
    }

    private fun isLandscape(orientationDegrees: Int)
             = (orientationDegrees == Surface.ROTATION_90 || orientationDegrees == Surface.ROTATION_270)

    private fun setCameraParamters(width: Int, height: Int) {
        if (mCamera == null) {
            return
        }
        mCameraParameters = mCamera!!.parameters
        mPreviewSizes.clear()
        for (size in mCameraParameters!!.supportedPreviewSizes) {
            mPreviewSizes.add(AspectRatio(size.width, size.height))
        }
        mPictureSizes.clear()
        for (size in mCameraParameters!!.supportedPictureSizes) {
            mPictureSizes.add(AspectRatio(size.width, size.height))
        }
        if (mAspectRatio == null) {
            mAspectRatio = DEFAULT_ASPECT_RATIO
        }
        fun getSortSet(list: ArrayList<AspectRatio>): SortedSet<AspectRatio> {
            var sortSet = TreeSet<AspectRatio>()
            list.forEach {
                if (it.ratio == mAspectRatio!!.ratio) {
                    sortSet.add(it)
                }
            }
            return sortSet
        }
        var sizes = getSortSet(mPreviewSizes)
        if (sizes == null) {
            mAspectRatio = chooseAspectRatio()
            sizes = getSortSet(mPreviewSizes)
        }
        var previewSize = chooseOptimalSize(sizes, width, height)
        val pictureSize = getSortSet(mPictureSizes).last()
        mCameraParameters!!.setPreviewSize(previewSize!!.width, previewSize!!.height)
        mCameraParameters!!.setPictureSize(pictureSize!!.width, pictureSize!!.height)
        mCameraParameters!!.pictureFormat = ImageFormat.JPEG
        mCameraParameters!!.jpegQuality = 100
        mCameraParameters!!.focusMode = Camera.Parameters.FOCUS_MODE_AUTO
        mCamera!!.parameters = mCameraParameters
        var orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setAspectRatio(previewSize.width, previewSize.height)
        } else {
            setAspectRatio(previewSize.height, previewSize.width)
        }
    }

    private var mPictureCallback = object : Camera.PictureCallback {
        override fun onPictureTaken(data: ByteArray?, camera: Camera?) {
            if (data == null) {
                Log.e(TAG, "error in taking picture, data in callback cannot be null")
                return
            }
            Log.d(TAG, "onPictureTaken start timestemp : ${System.currentTimeMillis()}")
            var bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
            var matrix = Matrix()
            if (mCurrentCameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                matrix.setRotate(90f)
            } else {
                matrix.postScale(1f, -1f)
                matrix.postRotate(-90f)
            }
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
            if (takePictureCallback != null) {
                takePictureCallback!!.onFinish(bitmap)
            }
            startPreview(mSurfaceHolder)
            isPictureCaptureInProgess.set(false)
            Log.d(TAG, "onPictureTaken end timestemp : ${System.currentTimeMillis()}")
        }
    }
}

class AspectRatio(var width: Int, var height: Int) {
    var ratio = height.toDouble() / width.toDouble()
    override fun equals(other: Any?): Boolean {
        if (other is AspectRatio) {
            return ratio == other.ratio
        }
        return false
    }
}

