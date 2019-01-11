package com.hali.xiaoyangchun.tonguepicture.model.net.Convert

import com.google.gson.reflect.TypeToken
import com.hali.xiaoyangchun.tonguepicture.model.net.JsonCreater
import com.hali.xiaoyangchun.tonguepicture.model.net.MainResult
import com.hali.xiaoyangchun.tonguepicture.model.net.interfaces.GsonBean
import com.lzy.okgo.convert.Converter
import okhttp3.Response


class JsonConvert<bean: GsonBean> : Converter<bean> {
    var mainRresult = MainResult<bean>()

    override fun convertResponse(response: Response): bean {
        mainRresult.code = response.code()
        mainRresult.message = response.message()
        var jsonString = response.body().toString()
        var result: bean = JsonCreater.fromJson(jsonString, (object : TypeToken<bean>(){}).type)
        mainRresult.data = result
        return result
    }
}