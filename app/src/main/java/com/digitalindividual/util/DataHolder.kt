package com.digitalindividual.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.digitalindividual.model.Filtro
import com.digitalindividual.model.Peca


/**
 * Created by 17170077 on 24/10/2018.
 */
class DataHolder private constructor(){

    private object Holder { val INSTANCE = DataHolder()}

    companion object {

        val instance: DataHolder by lazy { Holder.INSTANCE }

    }

    var listaFiltro = ArrayList<Filtro>()
    var listaDrawable = ArrayList<Drawable>()

//    var byteArray = ByteArray(100)
//        get() = field
//        set(value) {
//
//            field = value
//
//        }

//    var peca: Peca = Peca()

}