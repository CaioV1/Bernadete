package com.digitalindividual.model

/**
 * Created by 17170077 on 18/10/2018.
 */
data class Filtro(var id: Int,
                  var idTipoFiltro: Int,
                  var filtro: String,
                  var tipoFiltro: String,
                  var listaFiltro: ArrayList<String>){

    var idFiltroPeca = 0

}