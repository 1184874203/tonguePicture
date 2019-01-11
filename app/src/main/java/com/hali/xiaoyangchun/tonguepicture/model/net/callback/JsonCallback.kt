package com.hali.xiaoyangchun.tonguepicture.model.net.callback

import com.hali.xiaoyangchun.tonguepicture.model.net.Convert.JsonConvert
import com.hali.xiaoyangchun.tonguepicture.model.net.interfaces.GsonBean
import com.lzy.okgo.callback.AbsCallback
import okhttp3.Response

abstract class JsonCallback<bean: GsonBean> : AbsCallback<bean>(){

    var convert = JsonConvert<bean>()

    override fun convertResponse(response: Response): bean {
        var r = convert.convertResponse(response)
        response.close()
        return r
    }
}