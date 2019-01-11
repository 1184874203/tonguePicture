package com.hali.xiaoyangchun.tonguepicture.model.net.interfaces

import com.lzy.okgo.model.Response

interface OkGoInterface<T> {

    fun onSuccess(response: Response<T>, requestCode: Int)

    fun onError(error: String)

}