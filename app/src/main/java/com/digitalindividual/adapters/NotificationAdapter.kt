package com.digitalindividual.adapters

import android.app.ActionBar
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.digitalindividual.bernadete.MainActivity
import com.digitalindividual.bernadete.R
import com.digitalindividual.dao.NotificacaoDAO
import com.digitalindividual.model.Categoria
import com.digitalindividual.model.Notificacao
import com.digitalindividual.model.Peca
import com.digitalindividual.util.AlarmReceiver
import com.digitalindividual.util.DateConvert
import org.jetbrains.anko.intentFor

class NotificationAdapter: BaseAdapter{

    private var listaNotifi = ArrayList<Notificacao>()
    private var context: Context
    private var color = ""
    private var adapter: NotificationAdapter

    var position = 0

    constructor(context: Context, listaNotifi :ArrayList<Notificacao>, color: String):super(){

        this.listaNotifi = listaNotifi
        this.context = context
        this.color = color
        this.adapter = this

    }

    override fun getView(p0: Int, convertView: View?, p2: ViewGroup?): View? {

        var view: View? = convertView

        if(convertView == null){

            view = LayoutInflater.from(context).inflate(R.layout.list_notification, p2, false)

        }

        var item: Notificacao = getItem(p0) as Notificacao

        val txtTitulo = view?.findViewById<TextView>(R.id.txt_titulo_list)
        val txtPeca = view?.findViewById<TextView>(R.id.txt_peca_list)
        val txtData = view?.findViewById<TextView>(R.id.txt_data_list)
        val imgDelete = view?.findViewById<ImageButton>(R.id.img_delete)

        if(color == "white"){

            txtTitulo?.setTextColor(Color.WHITE)
            txtPeca?.setTextColor(Color.WHITE)
            txtData?.setTextColor(Color.WHITE)
            imgDelete?.visibility = View.VISIBLE
            txtTitulo?.setText(item.titulo)
            txtPeca?.setText(item.peca)
            txtTitulo?.ellipsize = TextUtils.TruncateAt.END
            txtData?.setText(DateConvert.SQLToString(item.data))

        } else {

            txtTitulo?.setTextColor(Color.BLACK)
            txtPeca?.setTextColor(Color.BLACK)
            txtData?.setTextColor(Color.BLACK)
            imgDelete?.visibility = View.GONE
            txtTitulo?.setText(item.titulo)
            txtPeca?.setText(DateConvert.SQLToString(item.data))
            txtData?.visibility =  View.GONE
            txtTitulo?.setLayoutParams(LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))

        }

        imgDelete?.setOnClickListener {

            val notificacaoDAO = NotificacaoDAO.instance

            val builder: AlertDialog.Builder = AlertDialog.Builder(context, R.style.AlertDialogStyle)

            builder.setMessage("Deseja realmente excluir a notificação ${item.titulo}?")

            builder.setPositiveButton("Sim") { dialogInterface, i ->

                if(notificacaoDAO.remover(context, item.id)){

                    Toast.makeText(context, "Removido com sucesso.", Toast.LENGTH_SHORT).show()

                    listaNotifi.remove(item)

                    adapter.notifyDataSetChanged()

                    cancelNotification(item.id)

                } else {

                    Toast.makeText(context, "Ocorreu um erro tente novamente.", Toast.LENGTH_LONG).show()

                }

            }

            builder.setNegativeButton("Não"){dialogInterface, i ->

                Toast.makeText(context, "Remoção cancelada", Toast.LENGTH_SHORT).show()

            }

            builder.show()

        }

        return view

    }

    fun cancelNotification(id: Int){

        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val pending = PendingIntent.getBroadcast(
                context, id,
                Intent(context, AlarmReceiver::class.java), 0
        )

        manager.cancel(pending)
    }

    override fun getCount(): Int {

        return listaNotifi.size

    }

    override fun getItem(p0: Int): Any {

        return listaNotifi[p0]

    }

    override fun getItemId(p0: Int): Long {

        return p0.toLong()

    }

}