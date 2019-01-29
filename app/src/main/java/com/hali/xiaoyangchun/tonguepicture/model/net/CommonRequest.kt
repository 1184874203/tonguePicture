package com.hali.xiaoyangchun.tonguepicture.model.net

import com.hali.xiaoyangchun.tonguepicture.bean.ImageUploadBean
import com.hali.xiaoyangchun.tonguepicture.model.net.callback.JsonCallback
import com.hali.xiaoyangchun.tonguepicture.model.net.interfaces.OkGoInterface
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import java.io.File

object CommonRequest {

    private val POST_UPLOAD_FILE = "file"
    private val POST_JSON = "jsonString"

    private fun <T> checkData(res: Response<MainResult<T>>): T? {
        var body = res.body()
        if (body == null) return null
        if (body.code == Constant.NET_CODE_SUCCESS && Constant.NET_MESSAGE_SUCCESS.equals(body.message)) {
            return body.data as T
        }
        return null
    }

    fun uploadImage(callback: OkGoInterface<ImageUploadBean>, file: File) {
        OkGo.post<MainResult<ImageUploadBean>>(RequestConstant.REQUEST_URL_UPLOADIMAGE)
                .params(POST_UPLOAD_FILE, file)
                .execute(object : JsonCallback<MainResult<ImageUploadBean>>() {
                    override fun onSuccess(response: Response<MainResult<ImageUploadBean>>) {
                        callback.onSuccess(checkData(response),RequestConstant.REQUESTCODE_UPLOADIMAGE)
                    }
                    override fun onError(response: Response<MainResult<ImageUploadBean>>?) {
                        super.onError(response)
                        callback.onError(response.toString())
                    }
                })
    }
}