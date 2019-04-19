package com.hali.xiaoyangchun.tonguepicture.model.net

import android.content.Context
import android.util.Log
import com.hali.xiaoyangchun.tonguepicture.model.net.interfaces.OkGoInterface
import com.lzy.okgo.model.Response
import okhttp3.*
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

object CommonRequest {

    val TAG = "CommonRequest"
    private val POST_UPLOAD_FILE = "file"
    private val POST_JSON = "jsonString"
    private val GET_DETIAL_BYID = "id"

    private fun <T> checkData(res: Response<MainResult<T>>): T? {
        var body = res.body()
        if (body == null) return null
        if (body.code == Constant.NET_CODE_SUCCESS && Constant.NET_MESSAGE_SUCCESS.equals(body.message)) {
            return body.data as T
        }
        return null
    }

    fun getTongueDetial(context: Context, id: String, callback: OkGoInterface?) {
        val client = OkHttpClient()
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        requestBody.addFormDataPart(POST_UPLOAD_FILE, id)
        val request = Request.Builder()
                .url(RequestConstant.REQUEST_URL_TONGUEPICTURE_DETIAL)
                .post(requestBody.build()).tag(context).build()
        client.newBuilder()
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                .build().newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.i(TAG, "onFailure")
                        callback?.onError("onFailure")
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: okhttp3.Response) {
                        if (response.isSuccessful) {
                            val str = response.body()!!.string()
                            callback?.onSuccess(str, RequestConstant.REQUESTCODE_TONGUEPICTURE_DETIAL)
                            Log.i(TAG, "body: $str")
                        } else {
                            Log.i(TAG, response.message() + " error : body " + response.body()!!.string())
                        }
                    }
                })

    }

    fun upLoadImage(context: Context, map: Map<String, Any>?, file: File?, callback: OkGoInterface?) {
        val client = OkHttpClient()
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        if (file != null) {
            val body = RequestBody.create(MediaType.parse("image/*"), file)
            requestBody.addFormDataPart(POST_UPLOAD_FILE, file.name, body)
        }
        if (map != null) {
            for ((key, value) in map) {
                requestBody.addFormDataPart(key, value.toString())
            }
        }
        val request = Request.Builder()
                .url(RequestConstant.REQUEST_URL_UPLOADIMAGE)
                .post(requestBody.build()).tag(context).build()
        client.newBuilder()
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                .build().newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.i(TAG, "onFailure")
                        callback?.onError("onFailure")
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: okhttp3.Response) {
                        if (response.isSuccessful) {
                            val str = response.body()!!.string()
                            callback?.onSuccess(str, RequestConstant.REQUESTCODE_UPLOADIMAGE)
                            Log.i(TAG, "body: $str")
                        } else {
                            Log.i(TAG, response.message() + " error : body " + response.body()!!.string())
                        }
                    }
                })

    }
}