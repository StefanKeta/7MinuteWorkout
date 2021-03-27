package com.example.a7minuteworkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlLiteOpenHelper(context: Context, cursorFactory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME,cursorFactory, DATABASE_VERSION) {
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "SevenMinutesWorkout.db"
        private val TABLE_HISTORY = "history"
        private val COLUMN_ID = "_id"
        private val COLUMN_COMPLETED_DATE = "completed_date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_EXERCISE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_HISTORY +
                " ( " + COLUMN_ID + " INTEGER PRIMARY KEY ,"+ COLUMN_COMPLETED_DATE+ " TEXT)")
        db?.execSQL(CREATE_EXERCISE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY)
        onCreate(db)
    }

    fun addDate(date : String){
        val values = ContentValues()
        values.put(TABLE_HISTORY,date)
        val db = this.writableDatabase
        db.insert(TABLE_HISTORY,null,values)
        db.close()
    }

    fun getAllCompleteDates() : ArrayList<String>{
        val list = ArrayList<String>()
        val db  = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY",null)

        while (cursor.moveToNext()){
            var date = cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))
            list.add(date)
        }
        cursor.close()
        db.close()
        return list
    }


}