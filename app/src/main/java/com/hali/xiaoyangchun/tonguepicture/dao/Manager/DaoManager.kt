package com.hali.xiaoyangchun.tonguepicture.dao.Manager

import android.content.Context
import com.hali.xiaoyangchun.tonguepicture.dao.DaoMaster
import com.hali.xiaoyangchun.tonguepicture.dao.DaoSession

class DaoManager(private var context: Context) {
    companion object {
        private val DB_NAME = "tonguePic.db"
        private var mDaoManager: DaoManager? = null
        fun getInstance(context: Context): DaoManager {
            if (mDaoManager == null) {
                synchronized(DaoManager.javaClass) {
                    if (mDaoManager == null) {
                        mDaoManager = DaoManager(context)
                    }
                }
            }
            return mDaoManager!!
        }
    }

    private var mHelper: DaoMaster.DevOpenHelper? = null
    private var mDaoMaster: DaoMaster? = null
    private var mDaoSession: DaoSession? = null

    @Synchronized fun getDaoSession(): DaoSession {
        if (mDaoSession == null) {
            mDaoSession = getDaoMaster().newSession()
        }
        return mDaoSession!!
    }

    @Synchronized fun closeDB() {
        closeHelper()
        closeDaoSeesion()
    }

    fun getDaoMaster(): DaoMaster {
        if (mDaoMaster == null) {
            mHelper = DaoMaster.DevOpenHelper(context, DB_NAME, null)
            mDaoMaster = DaoMaster(mHelper!!.writableDb)
        }
        return mDaoMaster!!
    }

    fun closeDaoSeesion() {
        if (mDaoSession != null) {
            mDaoSession!!.clear()
            mDaoSession = null
        }
    }

    fun closeHelper() {
        if (mHelper != null) {
            mHelper!!.close()
            mHelper = null
        }
    }
}