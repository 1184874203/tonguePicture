package com.hali.xiaoyangchun.tonguepicture.model.net

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import java.lang.reflect.Type

class JsonCreater {
    companion object {
        var gson = Gson()
        fun create() = gson
        fun <T> fromJson(json: String, type: Class<T>) = create().fromJson<T>(json, type)
        fun <T> fromJson(json: String, type: Type) = create().fromJson<T>(json, type)
        fun <T> fromJson(jsonReader: JsonReader, type: Type) = create().fromJson<T>(jsonReader, type)
        fun toJson(any: Any) = create().toJson(any)
    }
}