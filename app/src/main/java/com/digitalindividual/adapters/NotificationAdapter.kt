package com.digitalindividual.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.digitalindividual.bernadete.R
import com.digitalindividual.model.Categoria
import com.digitalindividual.model.Notificacao
import com.digitalindividual.util.DateConvert

class NotificationAdapter: BaseAdapter{

    private var listaNotifi = ArrayList<Notificacao>()
    private var context: Context
    private var color = ""

    var position = 0

    constructor(context: Context, listaNotifi :ArrayList<Notificacao>, color: String):super(){

        this.listaNotifi = listaNotifi
        this.context = context
        this.color = color

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

        if(color == "white"){

            txtTitulo?.setTextColor(Color.WHITE)
            txtPeca?.setTextColor(Color.WHITE)
            txtData?.setTextColor(Color.WHITE)

        } else {

            txtTitulo?.setTextColor(Color.BLACK)
            txtPeca?.setTextColor(Color.BLACK)
            txtData?.setTextColor(Color.BLACK)

        }

        txtTitulo?.setText(item.titulo)
        txtPeca?.setText(item.peca)
        txtData?.setText(DateConvert.SQLToString(item.data))

        return view

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