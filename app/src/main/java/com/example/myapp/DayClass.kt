package com.example.myapp

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.os.strictmode.SqliteObjectLeakedViolation
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class DayClass (){
         var dataa:String = "";
         var nadgodziny:Int=0;
         var week:Boolean=false;
         var saturday:Boolean=false;
         var sunday:Boolean=false;
    constructor( dataa:String, nadgodziny:Int, week:Boolean, saturday:Boolean, sunday:Boolean) : this() {
          this.dataa=dataa;
          this.nadgodziny=nadgodziny;
          this.week=week;
          this.saturday=saturday;
          this.sunday=sunday;

    }


}