package com.digitalindividual.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.digitalindividual.model.Filtro
import com.digitalindividual.model.Peca
import com.digitalindividual.util.DBHelper
import com.digitalindividual.util.ImageConvert
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * Created by 17170077 on 30/10/2018.
 */
class FiltroDAO {

    companion object {

        val instance = FiltroDAO()

    }

    fun inserirFiltros(context: Context, listaFiltro: ArrayList<Filtro>, idPeca: Int): Boolean{

        var listaId = ArrayList<Long>()

        val database = DBHelper.getInstance(context)


        database.use {

            listaFiltro.forEach {

                listaId.add(insert("tbl_peca_filtro",
                        "id_filtro" to it.id,
                                "id_peca" to idPeca))

            }

        }

        var envio = false

        listaId.forEach {

//            Log.d("$$$$$$$$$$!!!!!!", it.toString())

            envio = it.toInt() != -1

        }

        database.close()

        return envio

    }

    fun atualizar(context: Context, listaFiltro: ArrayList<Filtro>, idPeca: Int) : Boolean{

        remover(context, idPeca)

        var envio = inserirFiltros(context, listaFiltro, idPeca)

        return envio

    }

    fun obterTodos(context: Context, idTipo: Int): ArrayList<Filtro>{

        val listaFiltro = ArrayList<Filtro>()

        var database = DBHelper(context).readableDatabase

        val SQL: String = "SELECT f.*, t.nome FROM tbl_filtro AS f INNER JOIN tbl_tipo_filtro AS t ON f.id_tipo = t.id_tipo WHERE f.id_tipo = " + idTipo

        val cursor: Cursor = database.rawQuery(SQL, null)

        while(cursor.moveToNext()){

            val filtro = Filtro(cursor.getInt(0), cursor.getInt(2), cursor.getString(1), cursor.getString(3), ArrayList())

            listaFiltro.add(filtro)

        }

        cursor.close()

        database.close()

        return listaFiltro

    }

    fun obterUm(context: Context, idFiltro: Int): Filtro?{

        var filtro: Filtro? = null

        var database = DBHelper(context).readableDatabase

        val SQL: String = "SELECT f.*, t.nome FROM tbl_filtro AS f INNER JOIN tbl_tipo_filtro AS t ON f.id_tipo = t.id_tipo WHERE f.id_filtro = " + idFiltro

        val cursor: Cursor = database.rawQuery(SQL, null)

        if(cursor.moveToNext()){

            filtro = Filtro(cursor.getInt(0), cursor.getInt(2), cursor.getString(1), cursor.getString(3), ArrayList())

            Log.d("!!", cursor.getString(3))

        }

        cursor.close()

        database.close()

        return filtro

    }

    fun obterPorPeca(context: Context, idPeca: Int?): ArrayList<Filtro>{

        var listaFiltro = ArrayList<Filtro>()

        var database = DBHelper(context).readableDatabase

        val SQL: String = "SELECT f.*, t.nome FROM tbl_filtro AS f " +
                "INNER JOIN tbl_tipo_filtro AS t ON f.id_tipo = t.id_tipo " +
                "INNER JOIN tbl_peca_filtro AS pf ON f.id_filtro = pf.id_filtro " +
                "INNER JOIN tbl_peca AS p ON p.id_peca = pf.id_peca " +
                "WHERE p.id_peca = ${idPeca}"

        val cursor: Cursor = database.rawQuery(SQL, null)

        while(cursor.moveToNext()){

            var filtro = Filtro(cursor.getInt(0), cursor.getInt(2), cursor.getString(1), cursor.getString(3), ArrayList())

            Log.d("!!", cursor.getString(3))

            listaFiltro.add(filtro)

        }

        cursor.close()

        database.close()

        return listaFiltro

    }

    fun obterTipoFiltro(context: Context): ArrayList<Filtro>{

        val listaTipo = ArrayList<Filtro>()

        val listaFiltro = ArrayList<String>()

        val database = DBHelper(context).readableDatabase

        var SQL: String = "SELECT * FROM tbl_tipo_filtro"

        var cursor: Cursor = database.rawQuery(SQL, null)

        var cursorFiltro: Cursor

        while(cursor.moveToNext()){

            SQL = "SELECT nome FROM tbl_filtro WHERE id_tipo = "+cursor.getInt(0)

            cursorFiltro = database.rawQuery(SQL, null)

            listaFiltro.clear()

            while(cursorFiltro.moveToNext()){

                val string = cursorFiltro.getString(0)

                listaFiltro.add(string)

            }

            cursorFiltro.close()

            var filtro = Filtro(0, cursor.getInt(0), "", cursor.getString(1), listaFiltro)

            Log.d("!!", filtro.listaFiltro.get(0))

            listaTipo.add(filtro)

//            Log.d("!!", filtro.listaFiltro?.get(0))

        }

        cursor.close()

        database.close()

        return listaTipo

    }

    private fun remover(context: Context, idPeca: Int): Boolean{

        val database = DBHelper.getInstance(context)

        var id = 0

        database.use {

            id = delete("tbl_peca_filtro", "id_peca = $idPeca")

        }

        database.close()

        return id != -1


    }

}