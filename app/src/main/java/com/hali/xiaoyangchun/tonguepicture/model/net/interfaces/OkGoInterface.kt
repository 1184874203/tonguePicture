package com.hali.xiaoyangchun.tonguepicture.model.net.interfaces

interface OkGoInterface {

    fun onSuccess(response: Any?, requestCode: Int)

    fun onError(error: String)

}