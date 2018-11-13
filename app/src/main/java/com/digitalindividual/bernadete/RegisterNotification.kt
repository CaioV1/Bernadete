package com.digitalindividual.bernadete

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import com.digitalindividual.dao.NotificacaoDAO
import com.digitalindividual.model.Notificacao
import com.digitalindividual.util.DateConvert
import org.jetbrains.anko.alert
import org.jetbrains.anko.customView
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class RegisterNotification : AppCompatActivity() {

    lateinit var txtTitulo: EditText
    lateinit var txtData: TextView
    lateinit var txtDescricao: EditText
    lateinit var txtLocal: EditText

    val notificacaoDAO = NotificacaoDAO.instance

    val calendar: Calendar = Calendar.getInstance()
    val dateFormat = "dd/MM/yyyy"
    val simpleDate = SimpleDateFormat(dateFormat)
    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_notification)

        txtTitulo = find<EditText>(R.id.txt_titulo_notifi)
        txtData = findViewById<TextView>(R.id.txt_data_notificacao)
        txtDescricao = find<EditText>(R.id.txt_descricao_notifi)
        txtLocal = find<EditText>(R.id.txt_local)

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

    fun openDatePicker(view: View){

        DatePickerDialog(this, R.style.DialogTheme, dateSetListener ,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH)).show()

    }

    fun insertNotification(view: View){

        val data = DateConvert.stringToSQL(txtData.text.toString())

        val notificacao = Notificacao(0, txtTitulo.text.toString(), data, txtDescricao.text.toString(), txtLocal.text.toString())

        toast(notificacaoDAO.inserir(this, notificacao).toString())

    }

}
