package com.hali.xiaoyangchun.tonguepicture.dao.Manager

import android.content.Context

class ManagerFactory(private var context: Context) {
    private var mUserManager: UserManager? = null

    companion object {
        private var mInstance: ManagerFactory? = null
        fun getInstance(context: Context): ManagerFactory {
            if (mInstance == null) {
                synchronized(ManagerFactory.javaClass) {
                    if (mInstance == null) {
                        mInstance = ManagerFactory(context)
                    }
                }
            }
            return mInstance!!
        }
    }

    @Synchronized fun getUserManager(): UserManager {
        if (mUserManager == null) {
            mUserManager = UserManager(DaoManager.getInstance(context).getDaoSession().userDao)
        }
        return mUserManager!!
    }
}