package com.hali.xiaoyangchun.tonguepicture.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.hali.xiaoyangchun.tonguepicture.bean.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER".
*/
public class UserDao extends AbstractDao<User, Long> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property PicPath = new Property(1, String.class, "PicPath", false, "PIC_PATH");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Age = new Property(3, int.class, "age", false, "AGE");
        public final static Property Sex = new Property(4, String.class, "sex", false, "SEX");
        public final static Property OtherString = new Property(5, String.class, "otherString", false, "OTHER_STRING");
    }


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"PIC_PATH\" TEXT," + // 1: PicPath
                "\"NAME\" TEXT," + // 2: name
                "\"AGE\" INTEGER NOT NULL ," + // 3: age
                "\"SEX\" TEXT," + // 4: sex
                "\"OTHER_STRING\" TEXT);"); // 5: otherString
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, User entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String PicPath = entity.getPicPath();
        if (PicPath != null) {
            stmt.bindString(2, PicPath);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
        stmt.bindLong(4, entity.getAge());
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(5, sex);
        }
 
        String otherString = entity.getOtherString();
        if (otherString != null) {
            stmt.bindString(6, otherString);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String PicPath = entity.getPicPath();
        if (PicPath != null) {
            stmt.bindString(2, PicPath);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
        stmt.bindLong(4, entity.getAge());
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(5, sex);
        }
 
        String otherString = entity.getOtherString();
        if (otherString != null) {
            stmt.bindString(6, otherString);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // PicPath
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.getInt(offset + 3), // age
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // sex
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // otherString
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setPicPath(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAge(cursor.getInt(offset + 3));
        entity.setSex(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setOtherString(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(User entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(User entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(User entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
