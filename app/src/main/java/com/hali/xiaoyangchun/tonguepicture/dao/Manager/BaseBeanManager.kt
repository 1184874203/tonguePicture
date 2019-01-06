package com.hali.xiaoyangchun.tonguepicture.dao.Manager

import org.greenrobot.greendao.AbstractDao

open class BaseBeanManager<T, K>(private var mDao: AbstractDao<T, K>) {
    fun save(vararg items: T) {
        for (item in items) {
            mDao.insert(item)
        }
    }
    fun save(items: List<T>) = mDao.insertInTx(items)

    fun saveOrUpdate(vararg items: T) {
        for (item in items) {
            mDao.insertOrReplace(item)
        }
    }

    fun deleteByKey(key: K) = mDao.deleteByKey(key)

    fun delete(item: T) = mDao.delete(item)

    fun deleteAll() = mDao.deleteAll()

    fun query(key: K) = mDao.load(key)

    fun queryAll() = mDao.loadAll()

    fun queryBuilder() = mDao.queryBuilder()

    fun count() = mDao.count()

    fun refresh(item: T) = mDao.refresh(item)

    fun detach(item: T) = mDao.detach(item)
}