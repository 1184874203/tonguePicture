package com.hali.xiaoyangchun.tonguepicture.dao.Manager

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(private var context: Context) {
    private val PREFERENCE_NAME = "tonguePic_SaveInfo"

    init {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        editor = mSharedPreferences!!.edit()
    }

    companion object {
        private lateinit var mSharedPreferences: SharedPreferences
        private var mPreferenceManager: PreferenceManager? = null
        private lateinit var editor: SharedPreferences.Editor
        fun getInstance(context: Context): PreferenceManager {
            if (mPreferenceManager == null) {
                synchronized(PreferenceManager.javaClass) {
                    if (mPreferenceManager == null) {
                        mPreferenceManager = PreferenceManager(context)
                    }
                }
            }
            return mPreferenceManager!!
        }
    }
    private var SHARED_KEY_FIRST_INIT = "shared_key_first_init"
    private var SHARED_KEY_LAST_USER_NAME = "shared_key_last_user_name"
    private var SHARED_KEY_LAST_USER_AGE = "shared_key_last_user_age"
    private var SHARED_KEY_LAST_USER_SEX = "shared_key_last_user_sex"
    private var SHARED_KEY_LAST_USER_OTHERSTRING = "shared_key_last_user_otherString"

    private fun apply(action: () -> Unit) {
        action()
        editor.apply()
    }

    fun setLastUserName(name: String) =
            apply {
                editor.putString(SHARED_KEY_LAST_USER_NAME, name)
            }

    fun setLastUserAge(age: String) =
            apply {
                editor.putString(SHARED_KEY_LAST_USER_AGE, age)
            }

    fun setLastUserSex(sex: String) =
            apply {
                editor.putString(SHARED_KEY_LAST_USER_SEX, sex)
            }

    fun setLastUserOtherString(string: String) =
            apply {
                editor.putString(SHARED_KEY_LAST_USER_OTHERSTRING, string)
            }

    fun setUserFirstInited() =
            apply {
                editor.putBoolean(SHARED_KEY_FIRST_INIT, false)
            }

    fun isUserFirstInit() = mSharedPreferences.getBoolean(SHARED_KEY_FIRST_INIT, true)

    fun getLastUserName() = mSharedPreferences.getString(SHARED_KEY_LAST_USER_NAME, "")

    fun getLastUserAge() = mSharedPreferences.getString(SHARED_KEY_LAST_USER_AGE, "")

    fun getLastUserSex() = mSharedPreferences.getString(SHARED_KEY_LAST_USER_SEX, "")

    fun getLastUserOtherString() = mSharedPreferences.getString(SHARED_KEY_LAST_USER_OTHERSTRING, "")
}