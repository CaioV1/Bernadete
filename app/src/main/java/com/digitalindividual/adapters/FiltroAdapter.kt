package com.digitalindividual.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.digitalindividual.bernadete.R
import com.digitalindividual.model.Filtro
import com.digitalindividual.model.Peca
import kotlinx.android.synthetic.main.activity_filtro.view.*

/**
 * Created by 17170077 on 18/10/2018.
 */
class FiltroAdapter: BaseAdapter {

    private var context:Context
    private var arrayList = ArrayList<Filtro>()
    private var tipoDado: Boolean = false

    constructor(context: Context, arrayList: ArrayList<Filtro>, tipoDado: Boolean):super(){

        this.tipoDado = tipoDado
        this.arrayList = arrayList
        this.context = context


    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {

        var view: View? = null

        if(view == null){

            view = LayoutInflater.from(context).inflate(R.layout.list_filtro, p2, false)

        }

        var filtro:Filtro = getItem(p0) as Filtro

        val txtPrincipal = view?.findViewById<TextView>(R.id.txt_principal)
        val txtSecundario = view?.findViewById<TextView>(R.id.txt_secundario)

        if (tipoDado){

            txtPrincipal?.setText(filtro.filtro)
            txtSecundario?.setText(filtro.tipoFiltro)

        } else {

            txtPrincipal?.setText(filtro.tipoFiltro)

            var filtros: String = ""

            filtro.listaFiltro?.forEach {

                filtros += it + " / "

            }

            txtSecundario?.setText(filtros)

        }

        return view

    }

    override fun getCount(): Int {

        return arrayList.size

    }

    override fun getItem(p0: Int): Any {

        return arrayList.get(p0)

    }

    override fun getItemId(p0: Int): Long {

        return p0.toLong()

    }

}