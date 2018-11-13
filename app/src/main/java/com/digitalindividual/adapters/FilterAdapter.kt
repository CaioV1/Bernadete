package com.digitalindividual.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.digitalindividual.bernadete.R
import com.digitalindividual.dao.FiltroDAO
import com.digitalindividual.dao.PecaDAO
import com.digitalindividual.model.Filtro
import com.digitalindividual.model.Peca
import kotlinx.android.synthetic.main.activity_filtro.view.*

/**
 * Created by 17170077 on 18/10/2018.
 */
class FilterAdapter: BaseAdapter {

    private var context:Context
    private var arrayList = ArrayList<Filtro>()
    private var tipoDado: String = ""

    constructor(context: Context, arrayList: ArrayList<Filtro>, tipoDado: String):super(){

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

        if (tipoDado == "Tipo"){

            txtPrincipal?.setText(filtro.tipoFiltro)

            var filtros: String = ""

            var filtroDAO = FiltroDAO.instance

            var listaFiltros: ArrayList<Filtro> = filtroDAO.obterTodos(view?.context as Context, filtro.idTipoFiltro)

            filtros = listaFiltros.get(0).filtro + " | "
            filtros += listaFiltros.get(1).filtro + " | "
            filtros += listaFiltros.get(2).filtro

//            Log.d("@@@@@@@", it.filtro)

            txtSecundario?.setText(filtros)


        } else {

            txtPrincipal?.setText(filtro.filtro)
            txtSecundario?.setText(filtro.tipoFiltro)

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