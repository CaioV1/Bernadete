package com.digitalindividual.bernadete

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.digitalindividual.adapters.FiltroAdapter
import com.digitalindividual.dao.PecaDAO
import com.digitalindividual.model.Filtro

class Filtro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtro)

        var listView = findViewById<ListView>(R.id.list_filtro)

        val pecaDAO = PecaDAO.instance

        var context: Context = this

        val listaTipoFiltro: ArrayList<Filtro> = pecaDAO.obterTipoFiltro(this)

        listaTipoFiltro.forEach {

            it.listaFiltro?.forEach {

                Log.d("XXXXXXXXXXXXXXXXX", it)

            }

        }

        val adapter: FiltroAdapter = FiltroAdapter(context, listaTipoFiltro, false)

        listView.adapter = adapter

    }
}
