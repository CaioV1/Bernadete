package com.digitalindividual.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.digitalindividual.model.Categoria
import com.digitalindividual.model.Filtro
import com.digitalindividual.model.Peca
import com.digitalindividual.util.DBHelper
import java.io.ByteArrayOutputStream

/**
 * Created by 17170077 on 17/10/2018.
 */
class PecaDAO {

    private lateinit var database: SQLiteDatabase

    companion object {

        val instance = PecaDAO()

    }

    fun inserir(context:Context, peca: Peca): Boolean{

        database = DBHelper(context).writableDatabase

        val values = ContentValues()

        values.put("nome", peca.nome)
        values.put("descricao", peca.descricao)
        values.put("id_categoria", peca.idCategoria)
        values.put("imagem", turnToBytes(peca.imagem))

        var idPeca: Long = database.insert("tbl_peca", null, values)

        if(idPeca.toInt() == -1){

            return false

        } else {

//            values?.clear()
//
//            var idPecaFiltro: Array<Long>? = null
//
//            listaFiltro.forEach {
//
//                values?.put("id_filtro", it)
//                values?.put("id_peca", idPeca)
//
//                idPecaFiltro?.plus(database.insert("tbl_peca_filtro", null, values))
//
//            }
//
//            idPecaFiltro?.forEach {
//
//                if(it.toInt() == -1){
//
//                    return false
//
//                }
//
//            }
//
//            values?.clear()
//
//            var idPecaNotificacao: Array<Long>? = null
//
//            peca.idNotificacao.forEach {
//
//                values?.put("id_filtro", it)
//                values?.put("id_peca", idPeca)
//
//                idPecaNotificacao?.plus(database.insert("tbl_peca_notificacao", null, values))
//
//            }
//
//            idPecaNotificacao?.forEach {
//
//                if(it.toInt() == -1){
//
//                    return false
//
//                }

            return true

            }

        //}

    }

    fun atualizar(context: Context, peca: Peca) : Boolean{

        database = DBHelper(context).writableDatabase

        val values = ContentValues()

        values.put("nome", peca.nome)
        values.put("descricao", peca.descricao)
        values.put("id_categoria", peca.idCategoria)
        values.put("imagem", turnToBytes(peca.imagem))

        val id = database.update("tbl_peca", values, "_id_peca = ?", arrayOf(peca.id.toString()))

        return id != -1

    }

    fun remover(context: Context, idPeca: Int): Boolean{

        database = DBHelper(context).writableDatabase

        var id = database.delete("tbl_peca", "_id_peca = ?", arrayOf(idPeca.toString()))

        return id != -1

    }

    fun obterPecas(context: Context): ArrayList<Peca>{

        var listaPecas = ArrayList<Peca>()

        database = DBHelper(context).readableDatabase

        var SQL = "SELECT p.*, c.nome FROM tbl_peca AS p INNER JOIN tbl_categoria AS c ON p.id_categoria = c._id_categoria ORDER BY _id_peca DESC"

        var cursor = database.rawQuery(SQL, null)

        while (cursor.moveToNext()){

            var peca = Peca(cursor.getInt(0), 0, 0, cursor.getInt(3), cursor.getString(1), cursor.getString(2), cursor.getString(5), "", "", turnToBitmap(cursor.getBlob(4)))

            listaPecas.add(peca)

        }

        cursor.close()

        return listaPecas

    }

    fun obterUm(context: Context, idPeca: Int): Peca? {

        var peca :Peca? = null

        database = DBHelper(context).readableDatabase

        var SQL = "SELECT p.*, c.nome FROM tbl_peca AS p INNER JOIN tbl_categoria AS c ON p.id_categoria = c._id_categoria WHERE _id_peca = ${idPeca} ORDER BY _id_peca DESC"

        var cursor = database.rawQuery(SQL, null)

        if(cursor.moveToNext()){

            peca = Peca(cursor.getInt(0), 0, 0, cursor.getInt(3), cursor.getString(1), cursor.getString(2), cursor.getString(5), "", "", turnToBitmap(cursor.getBlob(4)))

        }

        cursor.close()

        return peca

    }

    fun obterFiltro(context: Context): ArrayList<Filtro>{

        val listaFiltro = ArrayList<Filtro>()

        database = DBHelper(context).readableDatabase

        val SQL: String = "SELECT f.*, t.nome FROM tbl_filtro AS f INNER JOIN tbl_tipo_filtro AS t ON f.id_tipo = t._id_tipo"

        val cursor:Cursor = database.rawQuery(SQL, null)

        while(cursor.moveToNext()){

            val filtro = Filtro(cursor.getInt(0), cursor.getInt(2), cursor.getString(1), cursor.getString(3), ArrayList())

            Log.d("!!", cursor.getString(3))

            listaFiltro.add(filtro)

        }

        cursor.close()

        return listaFiltro

    }

    fun obterTipoFiltro(context: Context): ArrayList<Filtro>{

        val listaTipo = ArrayList<Filtro>()

        val listaFiltro = ArrayList<String>()

        database = DBHelper(context).readableDatabase

        var SQL: String = "SELECT * FROM tbl_tipo_filtro"

        var cursor:Cursor = database.rawQuery(SQL, null)

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

        return listaTipo

    }

    fun obterCategoria(context: Context): ArrayList<Categoria>{

        var listaCategoria = ArrayList<Categoria>()

        database = DBHelper(context).readableDatabase

        var SQL = "SELECT * FROM tbl_categoria"

        var cursor = database.rawQuery(SQL, null)

        while (cursor.moveToNext()){

            var categoria = Categoria(cursor.getInt(0), cursor.getString(1))

            listaCategoria.add(categoria)

        }

        cursor.close()

        return listaCategoria

    }

    private fun turnToBytes(bitmap: Bitmap): ByteArray{

        var stream: ByteArrayOutputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        return stream.toByteArray()

    }

    private fun turnToBitmap(img: ByteArray): Bitmap{

        return BitmapFactory.decodeByteArray(img, 0 , img.size)

    }

}