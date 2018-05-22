package com.example.luisfernandomedinallorenti.jcalendario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDSqlite extends SQLiteOpenHelper{
    private String sql="create table eventos("+
            "id integer primary key autoincrement,"+
            "fecha date not null,"+
            "nombre varchar(50) not null,"+
            "descripcion text,"+
            "tipo varchar(50),"+
            "hraNotificacion time)";
    public BDSqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
