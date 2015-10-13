package com.santong.nock.utils;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.santong.nock.model.NockPlan;

/**
 * Created by santong.
 * At 15/10/1 23:20
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    public static final String DATABASE_NAME = "db_Nock";

    public static final String TABLE_NAME_PLAN = "nock_Plan";

    private static final String CREATE_TABLE_PLAN = "create table " + TABLE_NAME_PLAN + "("
            + "id integer primary key autoincrement,"
            + "title varchar(50),"
            + "description varchar(100),"
            + "start_date varchar(20),"
            + "end_date varchar(20),"
            + "record_days integer)";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PLAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 返回单个Plan对象
     * */
    public static NockPlan Cursor2Plan(Cursor cursor) {
        NockPlan plan = new NockPlan();
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                plan.setPlanId(cursor.getInt(cursor.getColumnIndex("id")));
                plan.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                plan.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                plan.setStartDate(DateUtils.getDateFromStr(cursor.getString(cursor.getColumnIndex("start_date"))));
                plan.setEndDate(DateUtils.getDateFromStr(cursor.getString(cursor.getColumnIndex("end_date"))));
                plan.setRecordDays(cursor.getInt(cursor.getColumnIndex("record_days")));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return plan;
    }
}
