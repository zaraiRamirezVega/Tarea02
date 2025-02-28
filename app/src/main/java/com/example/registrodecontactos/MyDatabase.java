package com.example.registrodecontactos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "RegistroContactos.db";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_NOMBRE = "registro_contactos";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITULO = "book_titulo";
    private static final String COLUMN_AUTOR = "book_autor";
    private static final String COLUMN_PAGINA = "book_paginas";

    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NOMBRE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITULO + " TEXT, " +
                COLUMN_AUTOR + " TEXT, " +
                COLUMN_PAGINA + " INTEGER);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOMBRE);
        onCreate(db);
    }
}
