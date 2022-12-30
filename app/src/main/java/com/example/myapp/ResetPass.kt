package com.example.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ResetPass : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pass)
     var pas:String=""
        val context = applicationContext

        val getOldPas = findViewById(R.id.oldpas) as EditText;
        val getNewPas= findViewById(R.id.newPass)as EditText;
        val reset = findViewById(R.id.reset) as Button;

        val myDB = openOrCreateDatabase("my.db", MODE_PRIVATE, null)
        val myCursor: Cursor = myDB.rawQuery("select pass from password", null)
        if (myCursor.moveToFirst()) {
            do {
                val data: String = myCursor.getString(myCursor.getColumnIndex("pass"))
                pas=data;


            } while (myCursor.moveToNext())
       reset.setOnClickListener{
           val newPass:String = getNewPas.text.toString();
           if(getOldPas.text.toString().equals(pas)&&getNewPas.text.equals("")){
               val myDB = openOrCreateDatabase("my.db", MODE_PRIVATE, null)
               myDB.execSQL("update password set pass = "+"'"+ newPass+"'"+" where pass="+"'"+getOldPas.text.toString()+"'")
               val intent = Intent(this, MainActivity::class.java)
               startActivity(intent)

           }else{


               val toast: Toast = Toast.makeText(context, "Złe stare hasło lub pole nowe hasło jest puste", Toast.LENGTH_SHORT)
               toast.show()
           }




       }


    }
}}