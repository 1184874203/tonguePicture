package com.hali.xiaoyangchun.tonguepicture.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream

object FileUtil {
    private val TAG = javaClass.simpleName
    val picturePath = Environment.getExternalStorageDirectory().toString() + File.separator + "tonguePic_"

    fun checkSDCard()
            = android.os.Environment.getExternalStorageDirectory().toString()
            .equals(android.os.Environment.MEDIA_MOUNTED)

    fun savePicture(data: ByteArray?, fileName: String?, matrix: Matrix) {
        if (data == null) {
            Log.e(TAG, "error during saving picture -> hasData:${data == null}")
            return
        }
        var _fileName = fileName
        if (_fileName == null) {
            _fileName = picturePath + System.currentTimeMillis() + ".jpg"
        }
        var bitmap = BitmapFactory.decodeByteArray(data,0,data.size)
        var file = File(_fileName)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        try {
            var out = FileOutputStream(file)
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.write(data)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}