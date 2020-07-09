package com.example.appqzx.activity.classtool;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private String create_office = "create table office("
            + "office_name varchar(20) primary key not null)";
    private String create_person = "create table persom("
            + "person_name varchar(20) primary key not null,"
            + "person_office varchar(20) not null)";
    private String create_work = "create table work("
            + "work_name varchar(20) primary key not null)";
    private String create_info = "create table info("
            + "info_id integer primary key autoincrement not null ,"
            + "office_name varchar(20) not null ,"
            + "person_name varchar(20) not null ,"
            + "work_name varchar(20) not null,"
            + "info_what varchar(20) not null,"
            + "info_cpntent varchar(100),"
            + "info_time datetime  not null)";

    /**
     *
     * @param context
     * @param name 数据库名字
     * @param factory 数据库进行查询的时候会返回一个cursor，这个cursor就是在上面的factory中产生的。
    如果有需求，可以自定义factory，这样返回的cursor就会符合自己的需求！
     * @param version 数据库版本号
     */
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_office);
        db.execSQL(create_person);
        db.execSQL(create_work);
        db.execSQL(create_info);
    }

    /**
     * 该方法会在数据库需要升级的时候调用
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE student ADD COLUMN other TEXT");
    }
}
