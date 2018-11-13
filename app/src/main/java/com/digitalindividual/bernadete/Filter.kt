package com.digitalindividual.bernadete

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.digitalindividual.adapters.FiltroAdapter
import com.digitalindividual.dao.FiltroDAO
import com.digitalindividual.dao.PecaDAO
import com.digitalindividual.model.Filtro
import com.digitalindividual.model.Peca
import com.digitalindividual.util.DataHolder
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class Filtro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtro)

        var listView = findViewById<ListView>(R.id.list_filtro)

        val tipo: String = intent.getStringExtra("Dado")

        val filtroDAO = FiltroDAO()

        var context: Context = this

        var lista = ArrayList<Filtro>()

        if(tipo == "Tipo"){

            lista = filtroDAO.obterTipoFiltro(this)

        } else {

            lista = filtroDAO.obterTodos(context, intent.getIntExtra("id", 1))

        }

        val adapter: FiltroAdapter = FiltroAdapter(context, lista, tipo)

        listView.adapter = adapter

        listView.setOnItemClickListener{adapterView, view, i, l ->

            var filtro: Filtro = adapter.getItem(i) as Filtro

            var lista = intent.getIntegerArrayListExtra("Lista")

            var posicao = -1

            if(filtro.idTipoFiltro in lista){

                posicao = lista.indexOf(filtro.idTipoFiltro)

            }

            if(tipo != "Tipo"){

                var peca = intent.getSerializableExtra("ObjetoPeca") as Peca
                val idPeca = peca.id

                startActivity(intentFor<CadastroPeca>("Dado" to "Filtro", "idFiltro" to filtro.id, "posicao" to posicao, "ObjetoPeca" to peca, "idPeca" to idPeca))


            } else {

                val lista = intent.getIntegerArrayListExtra("Lista")
                val peca = intent.getSerializableExtra("ObjetoPeca")

                startActivity(intentFor<com.digitalindividual.bernadete.Filtro>("Dado" to "Filtro", "id" to filtro.idTipoFiltro, "Lista" to lista, "ObjetoPeca" to peca))

            }

        }


    }
}
