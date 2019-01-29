package com.hali.xiaoyangchun.tonguepicture.model.net.callback

import com.google.gson.stream.JsonReader
import com.hali.xiaoyangchun.tonguepicture.model.net.JsonCreater
import com.lzy.okgo.callback.AbsCallback
import okhttp3.Response
import java.lang.reflect.ParameterizedType

abstract class JsonCallback<T> : AbsCallback<T>(){

    override fun convertResponse(response: Response): T {
        val genType = javaClass.genericSuperclass
        val params = (genType as ParameterizedType).actualTypeArguments
        val type = params[0] as? ParameterizedType ?: throw IllegalStateException("没有泛型参数")
        val body = response.body()!!
        val jsonReader = JsonReader(body.charStream())
        val result = JsonCreater.fromJson<T>(jsonReader, type)
        response.close()
        return result
    }
}