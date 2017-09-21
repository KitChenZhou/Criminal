package com.d22395.android.criminalintent.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.d22395.android.criminalintent.database.CrimeDbSchema.CrimeTable;

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    /*
    构造方法：第一个参数Context，
            第二个参数数据库名，
            第三个参数cursor允许我们在查询数据的时候返回一个自定义的光标位置，一般传入的都是null，
            第四个参数表示目前库的版本号（用于对库进行升级）
     */
    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
         primary key 将id列设为主键    autoincrement表示id列是自增长的
          */
        db.execSQL("create table " + CrimeTable.NAME + "(" +
                "id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ", " +
                CrimeTable.Cols.SUSPECT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
/**
 以下工作借助SQLiteOpenHelper类处理
 1、确认目标数据库是够存在
 2、如果不存在，首先创建数据库，然后创建数据表并初始化数据
 3、如果存在，打开并确认CrimeDBSchema是否为最新，
 4、如果是旧版本，就先升级最新版本
 */