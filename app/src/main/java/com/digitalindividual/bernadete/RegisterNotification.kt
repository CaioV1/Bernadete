package com.digitalindividual.bernadete

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import org.jetbrains.anko.alert
import org.jetbrains.anko.customView
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class RegisterNotification : AppCompatActivity() {

    lateinit var datePicker: DatePickerDialog
    val calendar: Calendar = Calendar.getInstance()
    lateinit var txtData: EditText
    val dateFormat = "dd.MM.yyyy"
    val simpleDate = SimpleDateFormat(dateFormat, Locale.GERMAN)
    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_notification)

        txtData = findViewById<EditText>(R.id.txt_data_notificacao)

        val currentdate = System.currentTimeMillis()
        val dateString = simpleDate.format(currentdate)
        txtData.setText(dateString)

        dateSetListener = object : DatePickerDialog.OnDateSetListener{

            override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {

                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                updateDate()

            }

        }

    }

    fun updateDate(){

        txtData.setText(simpleDate.format(calendar.time))

    }

    fun teste(view: View){

        DatePickerDialog(this, R.style.DialogTheme, dateSetListener ,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH)).show()
//)

//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
//
//            updateDate()
//
//        }, year, month, day)
//
//        datePicker.show()
//
//        toast("UHHHHHHHH")

    }

}
