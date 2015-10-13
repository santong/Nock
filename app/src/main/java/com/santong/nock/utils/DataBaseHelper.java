package com.santong.nock.utils;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.santong.nock.model.NockPlan;

import java.util.Date;

/**
 * Created by santong.
 * At 15/10/1 23:20
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    public static DataBaseHelper dbHelper = null;

    private static final int DATEBASE_VERSION = 1;

    private static final String DATABASE_NAME = "db_Nock";

    public static final String TABLE_NAME_PLAN = "nock_Plan";

    private static final String CREATE_TABLE_PLAN = "create table " + TABLE_NAME_PLAN + "("
            + "id integer primary key autoincrement,"
            + "title varchar(50),"
            + "description varchar(100),"
            + "start_date varchar(20),"
            + "end_date varchar(20),"
            + "record_days integer)";

    public DataBaseHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATEBASE_VERSION);
        mContext = context;
    }

    public static DataBaseHelper GetInstance(Context context, SQLiteDatabase.CursorFactory factory) {
        if (dbHelper == null) {
            dbHelper = new DataBaseHelper(context, factory);
        }
        return dbHelper;
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
     */
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

    public boolean UpdatePlan(int ID, Date endDate, String description) {
        String[] args = {String.valueOf(ID)};
        String endDateStr = DateUtils.formatDate(endDate);

        ContentValues values = new ContentValues();
        values.put("end_date", endDateStr);
        values.put("description", description);

        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_NAME_PLAN, values, "id=?", args);
        return db.update(TABLE_NAME_PLAN, values, "id=?", args) == 1;
    }
}
