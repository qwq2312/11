package com.example.weatherdemo;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

/*************数据库工具类****************/
public class MydbHelper extends SQLiteOpenHelper {

    public static final String cityCode = "citycode";
    public static final String ymd = "ymd";
    public static final String TYPE = "type";
    public static final String HIGH = "high";
    public static final String LOW = "low";
    public static final String FL = "fl";
    public static final String FX = "fx";
    public static final String SUNRISE = "sunrise";
    public static final String SUNSET = "sunset";
    public static final String NOTICE= "notice";
    private final Context context;
    private SQLiteDatabase db;

    static String CREATE_TABLE = "create table weather(" +
            "cityCode integer primary key autoincrement," +
            ymd + " text," +
            TYPE + " text," +
            HIGH + " text," +
            LOW + " text," +
            FL + " text," +
            FX + " text," +
            SUNRISE + " text," +
            SUNSET + " text," +
            NOTICE + " text" +
            ")";

    public MydbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public MydbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, Context context1) {
        super(context, name, factory, version);
        this.context = context1;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

        Toast.makeText(context, "数据库创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS weather");
        db.execSQL(CREATE_TABLE);
        Toast.makeText(context, "数据库升级成功", Toast.LENGTH_SHORT).show();
    }

}