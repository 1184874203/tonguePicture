package com.hali.xiaoyangchun.tonguepicture.utils

import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.graphics.Bitmap
import java.io.BufferedOutputStream


object FileUtil {
    fun savePicture(data: ByteArray?, action: (file: File) -> Unit): String? {
        if (data == null) {
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
            fos.write(data)
            fos.flush()
            fos.close()
            action(file)
            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    @Throws(IOException::class)
    fun saveFile(bm: Bitmap, path: String, onfinish: () -> Unit) {
        val file = File(path)
        if (!file.exists()) {
            file.mkdir()
        }
        val bos = BufferedOutputStream(FileOutputStream(file))
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos)
        bos.flush()
        bos.close()
        onfinish()
    }
}