package com.hali.xiaoyangchun.tonguepicture.utils

import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileUtil {
    fun savePicture(bitmap: Bitmap?): String? {
        if (bitmap == null) {
            return null
        }
        var fileDir = File(Environment.getExternalStorageDirectory(), "tonguePic")
        if (!fileDir.exists()) {
            fileDir.mkdir()
        }
        var fileName = "${System.currentTimeMillis()}.jpg"
        var file = File(fileDir, fileName)
        var fos = FileOutputStream(file)
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}