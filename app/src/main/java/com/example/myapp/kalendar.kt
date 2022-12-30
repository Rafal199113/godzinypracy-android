package com.example.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class kalendar : AppCompatActivity() {
    @SuppressLint("WrongConstant", "Range", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kalendar)
        var dataa=""
        var month:Int=0;
        val context = applicationContext
        val myDB = openOrCreateDatabase("my.db", MODE_PRIVATE, null)
        val kalendarz = findViewById<CalendarView>(R.id.kalendarz);
        val num = arrayOf<String>("Pn-Pt","sobota","niedziela")

        val addMonth = findViewById<Button>(R.id.addmonth);
        val enterValues = findViewById<LinearLayout>(R.id.enterDayValues)
        val data = findViewById<TextView>(R.id.textView7)
        val numberpicker = findViewById<NumberPicker>(R.id.picker)
        val daypickey= findViewById<NumberPicker>(R.id.picker2)
        daypickey.maxValue = 2;
        daypickey.minValue = 0;
            daypickey.displayedValues=num

        val printHoursButton= findViewById<Button>(R.id.button5);
            printHoursButton.setOnClickListener {
                Log.i("", month.toString())
                if(month>0){
                val intent = Intent(this, HoursList::class.java)
                intent.putExtra("key", month)
                startActivity(intent)}
            }

        val addButton= findViewById<Button>(R.id.button4);
            addButton.setOnClickListener {
                try {
                    val miesiac: String = Months.values()[month - 1].toString()
                    when (daypickey.value) {
                        0 ->   myDB.execSQL("insert or replace  into $miesiac values('" + dataa + "'," + numberpicker.value + ",1,0,0)")
                        1 ->   myDB.execSQL("insert or replace  into $miesiac values('" + dataa + "'," + numberpicker.value + ",0,1,0)")
                        2 ->   myDB.execSQL("insert or replace  into $miesiac values('" + dataa + "'," + numberpicker.value + ",0,0,1)")

                    }
                    val toast: Toast = Toast.makeText(
                        context,
                        "Dodano "+ numberpicker.value+ " godzin w dniu "+dataa ,
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                } catch (e: ArrayIndexOutOfBoundsException) {
                    val toast: Toast = Toast.makeText(
                        context,
                        "Wybierz pierw dzień dopiero dodaj do bazy...",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }catch(e:SQLiteException){
                    val toast: Toast = Toast.makeText(
                        context,
                        "W bazie niema jeszcze tego miesiąca",
                        Toast.LENGTH_SHORT
                    )
                }
            }
        val picker = findViewById(R.id.picker) as NumberPicker
        picker.minValue=1;
        picker.maxValue=8








        kalendarz.setOnDateChangeListener(CalendarView.OnDateChangeListener { _, i, il, i2 ->
            month = il + 1
            val date = "$i/$month/$i2"
            val sdf = SimpleDateFormat("yyyy/MM/dd")

            Log.i("", month.toString())

            data.text=date;
            val data:Date=Date(date)
            dataa=date



        })


        addMonth.setOnClickListener {
            val month = monthClass(kalendarz.date, myDB)
            month.addToDatabase()

        }
    }




}