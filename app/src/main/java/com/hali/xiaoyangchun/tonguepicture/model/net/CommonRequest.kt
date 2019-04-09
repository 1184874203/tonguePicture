package com.hali.xiaoyangchun.tonguepicture.model.net

import com.hali.xiaoyangchun.tonguepicture.bean.ImageUploadBean
import com.hali.xiaoyangchun.tonguepicture.bean.User
import com.hali.xiaoyangchun.tonguepicture.model.net.callback.JsonCallback
import com.hali.xiaoyangchun.tonguepicture.model.net.interfaces.OkGoInterface
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import java.io.File

object CommonRequest {

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

    fun uploadImage(callback: OkGoInterface, file: File) {
        OkGo.post<MainResult<ImageUploadBean>>(RequestConstant.REQUEST_URL_UPLOADIMAGE)
                .params(POST_UPLOAD_FILE, file)
                .execute(object : JsonCallback<MainResult<ImageUploadBean>>() {
                    override fun onSuccess(response: Response<MainResult<ImageUploadBean>>) {
                        callback.onSuccess(checkData(response), RequestConstant.REQUESTCODE_UPLOADIMAGE)
                    }

                    override fun onError(response: Response<MainResult<ImageUploadBean>>?) {
                        super.onError(response)
                        callback.onError(response.toString())
                    }
                })
    }

    fun postTongueInfo(user: User, callback: OkGoInterface) {
        OkGo.post<MainResult<Void>>(RequestConstant.REQUEST_URL_POST_tONGUEINFO)
                .params(POST_JSON, JsonCreater.toJson(user))
                .execute(object : JsonCallback<MainResult<Void>>() {
                    override fun onSuccess(response: Response<MainResult<Void>>) {
                        callback.onSuccess(checkData(response), RequestConstant.REQUESTCODE_POST_TONGUEINFO)
                    }

                    override fun onError(response: Response<MainResult<Void>>?) {
                        super.onError(response)
                        callback.onError(response.toString())
                    }
                })
    }

    fun getTongueDetial(id: String, callback: OkGoInterface) {
        OkGo.get<MainResult<User>>(RequestConstant.REQUEST_URL_TONGUEPICTURE_DETIAL)
                .params(GET_DETIAL_BYID, id)
                .execute(object : JsonCallback<MainResult<User>>() {
                    override fun onSuccess(response: Response<MainResult<User>>) {
                        callback.onSuccess(checkData(response), RequestConstant.REQUESTCODE_TONGUEPICTURE_DETIAL)
                    }

                    override fun onError(response: Response<MainResult<User>>?) {
                        super.onError(response)
                        callback.onError(response.toString())
                    }
                })
    }
}