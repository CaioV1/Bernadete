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
import com.digitalindividual.model.Promocao
import com.squareup.picasso.Picasso

/**
 * Created by 17170077 on 03/10/2018.
 */
class SaleAdapter : BaseAdapter{

    private var listaPromocoes = ArrayList<Promocao>()
    private var context:Context

    constructor(context: Context, listaPromocoes: ArrayList<Promocao>):super(){

        this.listaPromocoes = listaPromocoes
        this.context = context

    }

    override fun getView(p0: Int, convertView: View?, p2: ViewGroup?): View? {

        var view: View? = convertView

        if(convertView == null){

            view = LayoutInflater.from(context).inflate(R.layout.list_promocao, p2, false)

        }

        var item: Promocao = getItem(p0) as Promocao

        val txtNome = view?.findViewById<TextView>(R.id.txt_nome)
        val txtPreco = view?.findViewById<TextView>(R.id.txt_preco)
        val imgProduto = view?.findViewById<ImageView>(R.id.img_promocao)

        txtNome?.setText(item.nome)
        txtPreco?.setText("De R$"+item.preco.toString()+" por R$"+item.desconto.toString())

        Log.d("Imagem", item.caminhoImagem)

        Picasso.with(view?.context).load(view?.resources?.getString(R.string.ip_api_image)+item.caminhoImagem).into(imgProduto)

        return view

    }

    override fun getCount(): Int {

        return listaPromocoes.size

    }

    override fun getItem(p0: Int): Any {

        return listaPromocoes[p0]

    }

    override fun getItemId(p0: Int): Long {

        return p0.toLong()

    }

}