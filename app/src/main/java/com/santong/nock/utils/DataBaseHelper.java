package com.santong.nock.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.santong.nock.framework.NockApplication;
import com.santong.nock.model.NockPlan;

/**
 * Created by santong.
 * At 15/10/1 23:20
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static DataBaseHelper dbHelper = null;

    private static final int DATEBASE_VERSION = 1;

    private static final String DATABASE_NAME = "db_Nock";

    public static final String TABLE_NAME_PLAN = "nock_Plan";

    // 计划表
    private static final String CREATE_TABLE_PLAN = "create table " + TABLE_NAME_PLAN + "("
            + "id integer primary key autoincrement,"
            + "title varchar(50),"
            + "description varchar(100),"
            + "start_date varchar(20),"
            + "end_date varchar(20),"
            + "record_days integer,"
            + "state integer default 0,"
            + "last_date varchar(20) default 0)";

    /**
     * 已完成计划表
     * 增加打卡率，去掉打卡状态
     * 点击完成报告就可以将计划从PLAN表里面删掉,转存到PLAN_HISTORY表里
     */
    private static final String CREATE_TABLE_PLAN_HISTORY = "create table " + TABLE_NAME_PLAN + "("
            + "id integer primary key,"
            + "title varchar(50),"
            + "description varchar(100),"
            + "start_date varchar(20),"
            + "end_date varchar(20),"
            + "record_days integer,"
            + "record_rate integer"
            + ")";

    public DataBaseHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATEBASE_VERSION);
    }

    public synchronized static DataBaseHelper GetInstance(Context context, SQLiteDatabase.CursorFactory factory) {
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
                plan.setLastDate(DateUtils.getDateFromStr(cursor.getString(cursor.getColumnIndex("last_date"))));

                plan.setRecordDays(cursor.getInt(cursor.getColumnIndex("record_days")));

                int flag = cursor.getInt(cursor.getColumnIndex("state"));
                if (flag == 1)
                    plan.setState(true);
                else
                    plan.setState(false);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return plan;
    }

    public boolean DeletePlan(int ID) {
        String[] args = {String.valueOf(ID)};

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME_PLAN, "id=?", args);

        return db.delete(TABLE_NAME_PLAN, "id=?", args) == 0;
    }

    public boolean UpdatePlan(NockPlan plan) {
        String[] args = {String.valueOf(plan.getPlanId())};
        ContentValues values = getPlanValues(plan);

        SQLiteDatabase db = getWritableDatabase();
        return db.update(TABLE_NAME_PLAN, values, "id=?", args) == 1;
    }

    private ContentValues getPlanValues(NockPlan plan) {
        ContentValues values = new ContentValues();
        values.clear();
        values.put("id", plan.getPlanId());
        values.put("title", plan.getTitle());
        values.put("description", plan.getDescription());
        values.put("start_date", DateUtils.formatDate(plan.getStartDate()));
        values.put("end_date", DateUtils.formatDate(plan.getEndDate()));
        values.put("record_days", plan.getRecordDays());
        if (plan.isFinished())
            values.put("state", 1);
        else
            values.put("state", 0);
        values.put("last_date", DateUtils.formatDate(plan.getLastDate()));

        return values;
    }

    public NockPlan getPlan(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME_PLAN + " where id = " + id, null);

        return Cursor2Plan(cursor);
    }
}