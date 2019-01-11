package com.hali.xiaoyangchun.tonguepicture.model.net

import com.hali.xiaoyangchun.tonguepicture.model.net.callback.JsonCallback
import com.hali.xiaoyangchun.tonguepicture.model.net.interfaces.GsonBean
import com.hali.xiaoyangchun.tonguepicture.model.net.interfaces.OkGoInterface
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import java.io.File

object OkGoWorker {
    val JSONSTRING_TAG = "jsonString"
    fun <D: GsonBean> GET(requestCode: Int, url: String, param: Map<String, String>, tag: Any, okGoInterface: OkGoInterface<D>) {
        OkGo.get<D>(url).tag(tag).params(param)
                .execute(object : JsonCallback<D>(){
                    override fun onSuccess(response: Response<D>) {
                        okGoInterface.onSuccess(response, requestCode)
                    }

                    override fun onError(response: Response<D>?) {
                        super.onError(response)
                        okGoInterface.onError(response.toString())
                    }
                })
    }

    fun <D: GsonBean> POST(requestCode: Int, url: String, json: String, okGoInterface: OkGoInterface<D>) {
        OkGo.post<D>(url).params(JSONSTRING_TAG, json)
                .execute(object : JsonCallback<D>() {
                    override fun onSuccess(response: Response<D>) {
                        okGoInterface.onSuccess(response, requestCode)
                    }

                    override fun onError(response: Response<D>?) {
                        super.onError(response)
                        okGoInterface.onError(response.toString())
                    }
                })
    }

    fun uploadFiles(requestCode: Int, url: String, files: List<File>, okGoInterface: OkGoInterface<String>) {
        var request = OkGo.post<String>(url)
        for (i in 0 until files.size) {
            request.params("file$i", files[i])
        }
        request.execute(object : StringCallback(){
            override fun onSuccess(response: Response<String>) {
                okGoInterface.onSuccess(response, requestCode)
            }

            override fun onError(response: Response<String>) {
                super.onError(response)
                okGoInterface.onError(response.toString())
            }
        })
    }
}