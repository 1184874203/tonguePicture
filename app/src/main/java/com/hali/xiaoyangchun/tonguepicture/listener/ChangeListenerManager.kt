package com.hali.xiaoyangchun.tonguepicture.listener

class ChangeListenerManager {
    private var listenerMap = HashMap<String, ArrayList<ChangeListenr>>()

    companion object {
        private val TAG = "ChangeListenerManager"

        val CHANGELISTENERMANAGER_DB_INSERT = "changeListenerManager_db_insert"

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
        var list = listenerMap.get(key)
        if (list == null) {
            list = ArrayList()
            listenerMap.put(key, list)
        }
        if (!list!!.contains(listener)) {
            list.add(listener)
        }
    }

    fun unregisterListener(listener: ChangeListenr) {
        for (key in listenerMap.keys) {
            var list = listenerMap.get(key)
            if (list!!.contains(listener)) {
                list.remove(listener)
            }
        }
    }

    fun notifyDataChanged(key: String, data: Any) {
        var list = listenerMap.get(key)
        if (list != null) {
            for (listener in list) {
                listener.onChange(key, data)
            }
        }
    }
}
