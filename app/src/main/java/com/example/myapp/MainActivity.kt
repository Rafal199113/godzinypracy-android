package com.example.myapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*


class MainActivity : AppCompatActivity() {
    lateinit var shared : SharedPreferences
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var pas:String=""
        // get reference to button
        val btn_click_me = findViewById(R.id.button) as Button
        val myDB = openOrCreateDatabase("my.db", MODE_PRIVATE, null)
        myDB.execSQL("create table if not exists password (pass varchar(20))")
        val row1 = ContentValues()
        row1.put("pass", "pochwa")
       // myDB.insert("password", null, row1);
        val myCursor: Cursor = myDB.rawQuery("select pass from password", null)
        if (myCursor.moveToFirst()) {
            do {
                val data: String = myCursor.getString(myCursor.getColumnIndex("pass"))
              pas=data
                Log.i("cipa",data)

            } while (myCursor.moveToNext())
        }
        myCursor.close()
        btn_click_me.setOnClickListener {
            val getPassword = findViewById(R.id.editTextTextPassword) as EditText
            //getPassword.text.toString().equals(pas)
          if(true) {

              val intent = Intent(this, kalendar::class.java)
              startActivity(intent)










          }else{
              val context = applicationContext
              val toast: Toast = Toast.makeText(context, "Złe hasło", Toast.LENGTH_SHORT)
              toast.show()
          }



        }


  val reset = findViewById(R.id.textView2) as TextView;
        reset.setOnClickListener{
            val intent = Intent(this, ResetPass::class.java)
            startActivity(intent)

        }

}}