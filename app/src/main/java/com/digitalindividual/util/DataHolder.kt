package com.digitalindividual.util

import com.digitalindividual.model.Filtro


/**
 * Created by 17170077 on 24/10/2018.
 */
public class DataHolder private constructor(){

    private object Holder { val INSTANCE = DataHolder()}

    companion object {

        val instance: DataHolder by lazy { Holder.INSTANCE }

    }

    var listaFiltro = ArrayList<Filtro>()

}