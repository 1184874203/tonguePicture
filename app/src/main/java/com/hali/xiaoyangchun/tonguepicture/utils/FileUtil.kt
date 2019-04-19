package com.hali.xiaoyangchun.tonguepicture.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object FileUtil {

    fun getRealFilePathFromUri(context: Context, uri: Uri?): String? {
        if (null == uri) return null
        val scheme = uri.scheme
        var data: String? = null
        if (scheme == null) {
            data = uri.path
        } else if (ContentResolver.SCHEME_FILE.equals(scheme, ignoreCase = true)) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme, ignoreCase = true)) {
            val cursor = context.contentResolver.query(uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }

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