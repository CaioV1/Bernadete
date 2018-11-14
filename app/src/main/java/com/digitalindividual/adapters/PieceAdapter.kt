package com.digitalindividual.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.digitalindividual.bernadete.R
import com.digitalindividual.model.Categoria
import com.digitalindividual.model.Peca

/**
 * Created by 17170077 on 14/11/2018.
 */
class PieceAdapter : BaseAdapter{


    private var listaPecas = ArrayList<Peca>()
    private var context: Context

    var position = 0

    constructor(context: Context, listaPecas: ArrayList<Peca>):super(){

        this.listaPecas = listaPecas
        this.context = context

    }

    override fun getView(p0: Int, convertView: View?, p2: ViewGroup?): View? {

        var view: View? = convertView

        if(convertView == null){

            view = LayoutInflater.from(context).inflate(R.layout.list_peca, p2, false)

        }

        var item: Peca = getItem(p0) as Peca

        val txtPeca = view?.findViewById<TextView>(R.id.txt_nome_peca)

        txtPeca?.setText(item.nome)

        return view

    }

    override fun getCount(): Int {

        return listaPecas.size

    }

    override fun getItem(p0: Int): Any {

        return listaPecas[p0]

    }

    override fun getItemId(p0: Int): Long {

        return p0.toLong()

    }

}