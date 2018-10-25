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
import com.digitalindividual.util.DataHolder
import org.jetbrains.anko.intentFor

class Filtro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtro)

        var listView = findViewById<ListView>(R.id.list_filtro)

        val tipo: String = intent.getStringExtra("Dado")

        val pecaDAO = PecaDAO()

        var context: Context = this

        var lista = ArrayList<Filtro>()

        if(tipo == "Tipo"){

            lista = pecaDAO.obterTipoFiltro(this)

        } else {

            lista = pecaDAO.obterFiltros(context, intent.getIntExtra("id", 1))

        }

        val adapter: FiltroAdapter = FiltroAdapter(context, lista, tipo)

        listView.adapter = adapter

        listView.setOnItemClickListener{adapterView, view, i, l ->

            var filtro: Filtro = adapter.getItem(i) as Filtro

            if(tipo != "Tipo"){

                startActivity(intentFor<CadastroPeca>("Dado" to "Filtro", "id" to filtro.id))


            } else {

                startActivity(intentFor<com.digitalindividual.bernadete.Filtro>("Dado" to "Filtro", "id" to filtro.idTipoFiltro))

            }

        }


    }
}
