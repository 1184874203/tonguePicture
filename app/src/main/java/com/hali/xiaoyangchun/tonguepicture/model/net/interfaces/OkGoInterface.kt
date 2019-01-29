package com.hali.xiaoyangchun.tonguepicture.model.net.interfaces

interface OkGoInterface<T> {

    fun onSuccess(response: T?, requestCode: Int)

    fun onError(error: String)

}