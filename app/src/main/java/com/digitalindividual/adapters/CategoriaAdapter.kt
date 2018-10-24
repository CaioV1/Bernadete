package com.digitalindividual.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.digitalindividual.bernadete.R
import com.digitalindividual.model.Categoria
import com.digitalindividual.model.Promocao
import com.squareup.picasso.Picasso

class CategoriaAdapter : BaseAdapter{

    private var listaCategoria = ArrayList<Categoria>()
    private var context: Context

    constructor(context: Context, listaCategoria: ArrayList<Categoria>):super(){

        this.listaCategoria = listaCategoria
        this.context = context

    }

    override fun getView(p0: Int, convertView: View?, p2: ViewGroup?): View? {

        var view: View? = convertView

        if(convertView == null){

            view = LayoutInflater.from(context).inflate(R.layout.list_spinner, p2, false)

        }

        var item: Categoria = getItem(p0) as Categoria

        val txtCategoria = view?.findViewById<TextView>(R.id.txt_categoria)

        txtCategoria?.setText(item.nome)

        return view

    }

    override fun getCount(): Int {

        return listaCategoria.size

    }

    override fun getItem(p0: Int): Any {

        return listaCategoria[p0]

    }

    override fun getItemId(p0: Int): Long {

        return p0.toLong()

    }

}
