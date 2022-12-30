package com.example.myapp

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class HoursList : AppCompatActivity() {
    @SuppressLint("Range", "RestrictedApi", "ResourceType", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hours_list)
        val context = applicationContext
        val lista: ArrayList<DayClass> = ArrayList<DayClass>()
        var miesiac: String = ""
        var sumWeek: Int = 0;
        var sumSaturday: Int = 0;
        var sumSunday: Int = 0;

        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("key")
            Log.i("", (extras.get("key") as Int).toString())
            try {
                miesiac = Months.values()[(extras.get("key") as Int) - 1].toString()
            } catch (e: ArrayIndexOutOfBoundsException) {
                val toast: Toast =
                    Toast.makeText(context, "Wybierz dzień najpierw", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

        val parent = findViewById<View>(R.id.cycki) as LinearLayout



        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val li = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        val myDB = openOrCreateDatabase("my.db", MODE_PRIVATE, null)
        val piedziesiatki = findViewById<TextView>(R.id.textView14);
        val setki = findViewById<TextView>(R.id.textView15);
        val dwusetki = findViewById<TextView>(R.id.textView16);
        val licz = findViewById<Button>(R.id.button3);
        val premia = findViewById<EditText>(R.id.editTextTextPersonName)






        licz.setOnClickListener {
            var wyplata: Double = 0.0;
            var pln: Double = 0.0;
            var zwykle: Double = 0.0;
            var sob: Double = 0.0;
            var nie: Double = 0.0;


            var wynik: Double = 0.0;
            pln = (4200 / 20) / 8.toDouble();
            if (sumWeek > 0) {
                zwykle = sumWeek * (pln + (pln * 0.50))
            }
            if (sumSaturday > 0) {
                sob = sumSaturday * (pln + (pln * 2))
            }
            if (sumSunday > 0) {
                nie = sumSunday * (pln + (pln * 3))
            }
            try {
                val Cycki: Int = Integer.parseInt(premia.text.toString())
                wyplata = (4200 + Cycki + zwykle + sob + nie);
                wynik = wyplata - (wyplata * 0.17);
            } catch (e: NumberFormatException) {
                val toast: Toast =
                    Toast.makeText(context, "Pole premia jest puste", Toast.LENGTH_SHORT)
                toast.show()
            }
            val toast: Toast =
                Toast.makeText(context, "Wypłata: " + wynik.toString() + " zł", Toast.LENGTH_SHORT)
            toast.show()

        }

        try {
            val myCursor: Cursor = myDB.rawQuery(
                "select DISTINCT  Data,Godziny,Week,Saturday,Sunday from $miesiac",
                null
            )

            if (myCursor.moveToFirst()) {
                do {
                    lp.setMargins(0, 0, 30, 30);


                    li.setMargins(0, 0, 0, 30)

                    val data: String = myCursor.getString(myCursor.getColumnIndex("Data"))
                    val nadgodziny: Int = myCursor.getInt(myCursor.getColumnIndex("Godziny"))
                    val week: Int = myCursor.getInt(myCursor.getColumnIndex("Week"))
                    val saturday: Int = myCursor.getInt(myCursor.getColumnIndex("Saturday"))
                    val sunday: Int = myCursor.getInt(myCursor.getColumnIndex("Sunday"))

                    val frame: LinearLayout = LinearLayout(applicationContext)
                    val buttonframe: LinearLayout = LinearLayout(applicationContext)



                    frame.layoutParams = lp;
                    frame.orientation = LinearLayout.VERTICAL


                    val delete: Button = Button(ContextThemeWrapper(context, R.style.updateButton))
                    delete.text = "Usuń"
                    delete.setOnClickListener {
                        deleteValue(data, frame, parent, miesiac)
                    }
                    val field: EditText = EditText(ContextThemeWrapper(context, R.style.field))
                    field.width = 150

                    val update: Button = Button(ContextThemeWrapper(context, R.style.deleteButton))
                    update.text = "Update"
                    update.width = 5;
                    update.setOnClickListener {
                        updateValue(data, miesiac, field)


                    }


                    val godziny: String = myCursor.getString(myCursor.getColumnIndex("Godziny"))

                    val date: TextView = TextView(applicationContext)
                    date.text = "Data:" + data
                    date.textSize = 24F
                    date.setTextColor(Color.WHITE)

                    val hours: TextView = TextView(applicationContext)
                    hours.text = "Liczba nadgodzin: " + godziny;
                    hours.setTextColor(Color.WHITE)

                    val day: TextView = TextView(applicationContext)
                    day.setTextColor(Color.WHITE)
                    when (week) {
                        1 -> {
                            day.text = "Pn-pt"
                            val cycki = DayClass(data, nadgodziny, true, false, false)
                            lista.add(cycki)
                            when (cycki.week) {
                                true -> {
                                    sumWeek += cycki.nadgodziny;
                                }
                            }
                        }


                    }
                    when (saturday) {
                        1 -> {
                            day.text = "Sobota"
                            val cycki = DayClass(data, nadgodziny, true, true, false)
                            lista.add(cycki)
                            when (cycki.saturday) {
                                true -> {
                                    sumSaturday += cycki.nadgodziny;
                                }
                            }

                        }

                    }
                    when (sunday) {
                        1 -> {
                            day.text = "Niedzila"
                            val cycki = DayClass(data, nadgodziny, true, false, true)
                            lista.add(cycki)
                            when (cycki.sunday) {
                                true -> {
                                    sumSunday += cycki.nadgodziny;
                                }
                            }
                        }
                    }




                    frame.addView(date)
                    buttonframe.addView(delete)
                    buttonframe.addView(update)
                    buttonframe.addView(field)

                    frame.addView(hours)
                    frame.addView(day)
                    frame.addView(buttonframe)


                    parent.addView(frame, lp)


                } while (myCursor.moveToNext())


            }
            piedziesiatki.text = "Piędziesiątki: " + sumWeek.toString();
            setki.text = "Setki: " + sumSaturday.toString();
            dwusetki.text = "Dwusetki: " + sumSunday.toString();
        } catch (e: ArrayIndexOutOfBoundsException) {
            val toast: Toast = Toast.makeText(context, "Wybierz dzień najpierw", Toast.LENGTH_SHORT)
            toast.show()

        } catch (e: SQLiteException) {

        }

    } fun deleteValue(dative: String, frame: LinearLayout, parent: LinearLayout, miesiac: String){
        val myDB = openOrCreateDatabase("my.db", MODE_PRIVATE, null)
    myDB.execSQL("delete from $miesiac where Data ='$dative'")
        val context = applicationContext
        parent.removeView(frame)
        val toast: Toast = Toast.makeText(
            context,
            "Usunięto nadgodziny z dnia: "+dative,
            Toast.LENGTH_SHORT
        )
        toast.show()

    }
    fun updateValue(dative: String, miesiac: String,edit:EditText){
        val context=applicationContext
        try {

            val myDB = openOrCreateDatabase("my.db", MODE_PRIVATE, null)
            var godz: Int = Integer.parseInt(edit.text.toString())
            myDB.execSQL("update $miesiac set Godziny = $godz where data='$dative'  ")
            this.finish();

        }catch (e:java.lang.NumberFormatException){

            val toast: Toast = Toast.makeText(context, "Pole update jest puste", Toast.LENGTH_SHORT)
            toast.show()

        }

    }



}


