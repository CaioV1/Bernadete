package com.digitalindividual.bernadete

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.digitalindividual.adapters.PieceAdapter
import com.digitalindividual.dao.NotificacaoDAO
import com.digitalindividual.dao.PecaDAO
import com.digitalindividual.model.Categoria
import com.digitalindividual.model.Notificacao
import com.digitalindividual.model.Peca
import com.digitalindividual.util.DateConvert
import org.jetbrains.anko.alert
import org.jetbrains.anko.customView
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class RegisterNotification : AppCompatActivity() {

    lateinit var txtData: TextView
    lateinit var txtHora:TextView

    lateinit var txtDescricao: EditText
    lateinit var txtTitulo: EditText
    lateinit var txtLocal: EditText

    lateinit var spinner: Spinner

    val notificacaoDAO = NotificacaoDAO.instance
    val pecaDAO = PecaDAO.instance

    lateinit var adapter: PieceAdapter

    val calendar: Calendar = Calendar.getInstance()

    val dateFormat = "dd/MM/yyyy"
    val hourFormat = "HH:mm"

    val simpleDate = SimpleDateFormat(dateFormat)
    val simpleHour = SimpleDateFormat(hourFormat)

    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener

    var idPeca = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_notification)

        txtTitulo = find<EditText>(R.id.txt_titulo_notifi)
        txtDescricao = find<EditText>(R.id.txt_descricao_notifi)
        txtLocal = find<EditText>(R.id.txt_local)

        txtData = findViewById<TextView>(R.id.txt_data_notificacao)
        txtHora = find<TextView>(R.id.txt_hora_notificacao)

        val listaPecas = pecaDAO.obterPecas(this)

        adapter = PieceAdapter(this, listaPecas)

        spinner = find<Spinner>(R.id.sp_peca)

        spinner.adapter = adapter

        val currentdate = System.currentTimeMillis()

        txtData.setText(simpleDate.format(currentdate))
        txtHora.setText(simpleHour.format(currentdate))

        dateSetListener = object : DatePickerDialog.OnDateSetListener{

            override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {

                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                updateDate()

            }

        }

        timeSetListener = object : TimePickerDialog.OnTimeSetListener{

            override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {

                calendar.set(Calendar.HOUR, hour)
                calendar.set(Calendar.MINUTE, minute)

                updateTime()

            }

        }

    }

    fun updateDate(){

        txtData.setText(simpleDate.format(calendar.time))

    }

    fun updateTime(){

        txtHora.setText(simpleHour.format(calendar.time))

    }

    fun openDatePicker(view: View){

        DatePickerDialog(this, R.style.DialogTheme, dateSetListener ,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH)).show()

    }

    fun openTimePicker(view: View){

        TimePickerDialog(this, R.style.DialogTheme, timeSetListener, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false).show()

    }

    fun getPosition(spinner: Spinner, idItem: Int?): Int{

        var peca: Peca? = null

        var posicao = 0

        var i = 0

        while(i < spinner.adapter.count){

            peca = spinner.getItemAtPosition(i) as Peca?

            if(idItem == peca?.id){

                posicao = i

            }

            i++

        }

        return posicao

    }

    fun insertNotification(view: View){

        val data = DateConvert.stringToSQL(txtData.text.toString())

        val notificacao = Notificacao(0, txtTitulo.text.toString(), data.toLong(), txtDescricao.text.toString(), txtLocal.text.toString())

        val peca = spinner.selectedItem as Peca

        notificacao.idPeca = peca.id

//        val peca = spinner.getItemAtPosition(getPosition()) as Peca

        toast(notificacaoDAO.inserir(this, notificacao).toString())

    }

}
