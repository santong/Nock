package com.santong.nock.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.santong.nock.model.NockNotice;
import com.santong.nock.model.NockPlan;

/**
 * Created by santong.
 * At 15/10/1 23:20
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static DataBaseHelper dbHelper = null;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "db_Nock";

    public static final String TABLE_NAME_PLAN = "nock_Plan";

    public static final String TABLE_NAME_NOTICE = "nock_notice";

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

    private static final String CREATE_TABLE_NOTICE = "create table " + TABLE_NAME_NOTICE + "("
            + "id integer primary key autoincrement,"
            + "title varchar(50),"
            + "content varchar(140),"
            + "create_date varchar(20),"
            + "notice_date varchar(20),"
            + "state integer default 0)";

    public DataBaseHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
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
        db.execSQL(CREATE_TABLE_NOTICE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean savePlan(NockPlan plan) {
        // TODO: 待实现，应把AddPlanAcivity中的savePlan()方法移至这里
        return true;
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

    public NockNotice Cursor2Notice(Cursor cursor) {
        NockNotice nockNotice = new NockNotice();
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {

                nockNotice.setNoticeId(cursor.getInt(cursor.getColumnIndex("id")));

                nockNotice.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                nockNotice.setContent(cursor.getString(cursor.getColumnIndex("content")));

                nockNotice.setCreateDate(DateUtils.getDateFromStr(cursor.getString(cursor.getColumnIndex("create_date"))));
                nockNotice.setNoticeDate(DateUtils.getDateFromStr(cursor.getString(cursor.getColumnIndex("notice_date"))));

                int flag = cursor.getInt(cursor.getColumnIndex("state"));
                if (flag == 1)
                    nockNotice.setIsComplete(true);
                else
                    nockNotice.setIsComplete(false);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return nockNotice;
    }

    public boolean saveNotice(NockNotice notice) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        if (!TextUtils.isEmpty(notice.getTitle()))
            values.put("title", notice.getTitle());
        else
            values.put("title", "未设置title");
        if (!TextUtils.isEmpty(notice.getContent()))
            values.put("content", notice.getContent());
        else
            values.put("content", "未设置content");
        if (null != notice.getCreateDate())
            values.put("create_date", DateUtils.formatDate(notice.getCreateDate()));
        if (null != notice.getNoticeDate())
            values.put("notice_date", DateUtils.formatDate(notice.getNoticeDate()));
        if (notice.isComplete())
            values.put("state", true);
        else
            values.put("state", false);

        db.insert(TABLE_NAME_NOTICE, null, values);

        values.clear();
        return true;
    }

    private ContentValues getNoticeValue(NockNotice notice) {
        ContentValues values = new ContentValues();
        values.clear();
        values.put("id", notice.getNoticeId());
        values.put("title", notice.getTitle());
        values.put("content", notice.getContent());
        values.put("create_date", DateUtils.formatDate(notice.getCreateDate()));
        values.put("notice_date", DateUtils.formatDate(notice.getNoticeDate()));
        if (notice.isComplete())
            values.put("state", 1);
        else
            values.put("state", 0);

        return values;
    }

    public boolean updateNotice(NockNotice notice) {
        String[] args = {String.valueOf(notice.getNoticeId())};
        ContentValues values = getNoticeValue(notice);

        SQLiteDatabase db = getWritableDatabase();
        return db.update(TABLE_NAME_NOTICE, values, "id=?", args) == 1;
    }

    public NockNotice getNotice(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME_NOTICE + " where id = " + id, null);

        return Cursor2Notice(cursor);
    }

    public boolean delNotice(int id) {
        String[] args = {String.valueOf(id)};

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME_NOTICE, "id=?", args);

        return db.delete(TABLE_NAME_NOTICE, "id=?", args) == 0;
    }
}