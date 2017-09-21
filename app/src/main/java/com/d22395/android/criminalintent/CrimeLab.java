package com.d22395.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.d22395.android.criminalintent.database.CrimeBaseHelper;
import com.d22395.android.criminalintent.database.CrimeCursorWrapper;
import com.d22395.android.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by D22395 on 2017/7/25.
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;

//    private List<Crime> mCrimes;
    /*
    使用SQLiteDatabase创建crime数据库
     */
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(CrimeTable.NAME, null, values);
//        mCrimes.add(c);
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    public void deleteCrime(Crime crime) {
//        mCrimes.remove(i);
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.delete(CrimeTable.NAME,
                CrimeTable.Cols.UUID + "= ?",
                new String[]{uuidString});
    }
/*
    getWritableDatabase()做如下工作
        1、打开指定目录的数据库，若不存在则先创建数据库文件
        2、若是首次创建数据库，则调用OnCreate方法，并保存最新版本号
        3、如果已创建则检查

        SQLiteOpenHelper中的两个非常重要的实例方法。
        getReadableDatabase()和getWriteableDatabase(),
        这两个方法都可以创建和打开一个现有的数据库，
        区别在于当数据库不可写入的时候getReadableDatabase()方法返回的对象将
        以只读的方式去打开数据库，而getWriteDatabase()将出现异常
 */
    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
//        mCrimes = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            Crime crime = new Crime();
//            crime.setTitle("Crime #" + i);
//            crime.setSolved(i % 2 == 0); // Every other one
//            mCrimes.add(crime);
//            Log.d("CrimeLab","列表:"+ mCrimes.size());
//        }
    }
/*
    遍历查询取出所有的crime
 */
    public  List<Crime> getCrimes() {
//        return mCrimes;
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime()); // 从数据库中查找数据并添加到crime中
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id) { // 只取出已存在的首条记录
//        for (Crime crime : mCrimes) {
//            if (crime.getId().equals(id)){
//                return crime;
//            }
//        }
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Crime crime) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, crime.getPhotoFilename());
    }



    /*
        将查询返回的curse封装到CrimeCursorWrapper 类中
        最后调用getCrime方法遍历取出Crime
     */
//    private Cursor queryCrimes (String whereClause, String[] whereArgs) {
      private CrimeCursorWrapper queryCrimes (String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,// Colums - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new CrimeCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());

        return values;
    }

}
