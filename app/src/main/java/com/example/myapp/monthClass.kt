package com.example.myapp

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

enum class Months {
    January,
    February,
    March ,
    April ,
    May ,
    June ,
    July,
    August,
    September,
    October,
    November,
    December
}
class monthClass(val db:SQLiteDatabase) {
    val CountOfDays:Int=0;

    val DaysArray = arrayOf<DayClass>();

    val Premia:Int=0
    var Data:Long = 0

    constructor(data: Long,db:SQLiteDatabase ) : this(db) {
          this.Data=data;



      }
    @SuppressLint("Range")
    fun addToDatabase()
    {



        val sdf = SimpleDateFormat("M")
        val date = sdf.format(this.Data).toInt()

        Log.i("",date.toString())


        db.execSQL("create table if not exists "+Months.values()[date-1].toString()+" ("
                +"Data String," +
                "Godziny Integer," +
                "Week Integer," +
                "Saturday Integer," +
                "Sunday Integer  )")









    }
    fun addDayToDatabase(){
        val sdf = SimpleDateFormat("M")
        val date = sdf.format(this.Data).toInt()
        //db.execSQL("omsert into "+Months.values()[date-1].toString()+" values")

    }
}