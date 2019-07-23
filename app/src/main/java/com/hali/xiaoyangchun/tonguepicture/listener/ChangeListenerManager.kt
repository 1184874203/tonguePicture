package com.hali.xiaoyangchun.tonguepicture.listener

import android.os.Handler
import android.os.Looper

class ChangeListenerManager {
    private var listenerMap = HashMap<String, ArrayList<ChangeListenr>>()
    private val handler = Handler(Looper.getMainLooper())
    companion object {
        private val TAG = "ChangeListenerManager"

        val CHANGELISTENERMANAGER_DB_INSERT = "changeListenerManager_db_insert"
        val CHANGELISTENERMANAGER_DB_READ = "changeListenerManager_db_read"

        private var mListener: ChangeListenerManager? = null
        fun getInstance(): ChangeListenerManager {
            if (mListener == null) {
                synchronized(ChangeListenerManager.javaClass) {
                    if (mListener == null) {
                        mListener = ChangeListenerManager()
                    }
                }
            }
            return mListener!!
        }
    }

    fun registerListener(key: String, listener: ChangeListenr) {
        var list = listenerMap[key]
        if (list == null) {
            list = ArrayList()
            listenerMap.put(key, list)
        }
        if (!list.contains(listener)) {
            list.add(listener)
        }
    }

    fun unregisterListener(listener: ChangeListenr) {
        for (key in listenerMap.keys) {
            val list = listenerMap.get(key)
            if (list!!.contains(listener)) {
                list.remove(listener)
            }
        }
    }

    fun notifyDataChanged(key: String, data: Any) {
        val list = listenerMap.get(key)
        if (list != null) {
            for (listener in list) {
                handler.post {
                    listener.onChange(key, data)
                }
            }
        }
    }
}
