package com.hali.xiaoyangchun.tonguepicture.model.net

class MainResult<T> {
    var code: Int = 0
    var message: String = "success"
    var data: T? = null
    override fun toString(): String {
        return "MainResult(code=$code, message='$message', data=$data)"
    }
}