package com.muibsols.iptracker.Sync;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ContactsModel.class}, version = 1)
public abstract class BaseDB extends RoomDatabase {
    public static  BaseDB baseDataDatabase;

    public abstract ContactsModelDAO contactsModelDAO();

    public static synchronized BaseDB getBaseDataDatabase(Context context) {
        if (baseDataDatabase == null) {
            baseDataDatabase = Room.databaseBuilder(context.getApplicationContext(), BaseDB.class, "baseDB")
                    .addCallback(callback)
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return baseDataDatabase;
    }

    private static Callback callback = new Callback() {
        @Override // androidx.room.RoomDatabase.Callback
        public void onCreate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            super.onCreate(supportSQLiteDatabase);
        }

        @Override // androidx.room.RoomDatabase.Callback
        public void onOpen(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            super.onOpen(supportSQLiteDatabase);
        }
    };

}
