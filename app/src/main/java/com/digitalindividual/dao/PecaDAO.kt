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
import com.digitalindividual.util.ImageConvert
import org.jetbrains.anko.db.*
import java.io.ByteArrayOutputStream

/**
 * Created by 17170077 on 17/10/2018.
 */
class PecaDAO {

    companion object {

        val instance = PecaDAO()

    }

    fun inserir(context:Context, peca: Peca): Long{

        val database = DBHelper.getInstance(context)

        var idPeca: Long = 0

        database.use {

            idPeca = insert("tbl_peca",
                               "nome" to peca.nome,
                                       "descricao" to peca.descricao,
                                       "imagem" to ImageConvert.turnToBytes(peca.imagem),
                                       "id_categoria" to peca.idCategoria)

        }

//        val values = ContentValues()
//
//        values.put("nome", peca.nome)
//        values.put("descricao", peca.descricao)
//        values.put("id_categoria", peca.idCategoria)
//        values.put("imagem", ImageConvert.turnToBytes(peca.imagem))
//
//        var idPeca: Long = database.insert("tbl_peca", null, values)
//
//        database.close()

        database.close()

        return idPeca

    }

    fun atualizar(context: Context, peca: Peca) : Boolean{

        val database = DBHelper.getInstance(context)

        var idPeca: Int = 0

        var envio = false

        try {

            database.use {

                update("tbl_peca",
                        "nome" to peca.nome,
                        "descricao" to peca.descricao,
                        "imagem" to ImageConvert.turnToBytes(peca.imagem),
                        "id_categoria" to peca.idCategoria).whereSimple("id_peca = ${peca.id}").exec()

            }

            envio = true

        } catch (e: Exception){

            envio = false

        }

        database.close()

//        val values = ContentValues()
//
//        values.put("nome", peca.nome)
//        values.put("descricao", peca.descricao)
//        values.put("id_categoria", peca.idCategoria)
//        values.put("imagem", ImageConvert.turnToBytes(peca.imagem))
//
//        val id = database.update("tbl_peca", values, "_id_peca = ?", arrayOf(peca.id.toString()))
//
//        return id != -1

        return envio

    }

    fun remover(context: Context, idPeca: Int): Boolean{

        val database = DBHelper.getInstance(context)

        var id = 0

        database.use {

            id = delete("tbl_peca", "id_peca = ${idPeca}")

        }

//        database = DBHelper(context).writableDatabase
//
//        var id = database.delete("tbl_peca", "_id_peca = ?", arrayOf(idPeca.toString()))
//
        database.close()

        return id != -1

    }

    fun obterPecas(context: Context): ArrayList<Peca>{

//        val database = DBHelper.getInstance(context)
//
//        var listaPecas = ArrayList<Peca>()
//
//        database.use {
//
//            select("tbl_peca INNER JOIN tbl_categoria").exec {
//
//                var parser = rowParser{
//
//                    idPeca: Int,
//                    nome: String,
//                    descricao: String,
//                    imagem: Bitmap,
//                    idCategoria: Int,
//                    categoria: String->
//
//                    Peca(idPeca, idCategoria, nome, descricao, categoria, imagem)
//
//                }
//
//                listaPecas = parseList(parser) as ArrayList<Peca>
//
//            }
//
//        }
//
//        return listaPecas

        var listaPecas = ArrayList<Peca>()

        val database = DBHelper(context).readableDatabase

        var SQL = "SELECT p.*, c.nome FROM tbl_peca AS p INNER JOIN tbl_categoria AS c ON p.id_categoria = c.id_categoria ORDER BY id_peca DESC"

        var cursor = database.rawQuery(SQL, null)

        while (cursor.moveToNext()){

            var peca = Peca(cursor.getInt(0), cursor.getInt(4), cursor.getString(1), cursor.getString(2), cursor.getString(5), ImageConvert.turnToBitmap(cursor.getBlob(3)))

            listaPecas.add(peca)

        }

        cursor.close()

        database.close()

        return listaPecas

    }

    fun obterUm(context: Context, idPeca: Int): Peca? {

//        val database = DBHelper.getInstance(context)
//
//        var peca:Peca? = null
//
//        database.use {
//
//            select("tbl_peca INNER JOIN tbl_categoria").whereArgs("id_peca = ${idPeca}").exec {
//
//                var parser = rowParser{
//
//                    idPeca: Int,
//                    nome: String,
//                    descricao: String,
//                    imagem: Bitmap,
//                    idCategoria: Int,
//                    categoria: String->
//
//                    Peca(idPeca, idCategoria, nome, descricao, categoria, imagem)
//
//                }
//
//                peca = parseSingle(parser)
//
//            }
//
//        }
//
//        return peca

        var peca :Peca? = null

        val database = DBHelper(context).readableDatabase

        var SQL = "SELECT p.*, c.nome FROM tbl_peca AS p INNER JOIN tbl_categoria AS c ON p.id_categoria = c.id_categoria WHERE p.id_peca = ${idPeca} ORDER BY id_peca DESC"

        Log.d("77777777777777777", "$SQL")

        var cursor = database.rawQuery(SQL, null)

        if(cursor.moveToNext()){

            peca = Peca(cursor.getInt(0), cursor.getInt(4), cursor.getString(1), cursor.getString(2), cursor.getString(5), ImageConvert.turnToBitmap(cursor.getBlob(3)))

        }

        cursor.close()

        database.close()

        return peca

    }

    fun obterCategoria(context: Context): ArrayList<Categoria>{

        val database = DBHelper.getInstance(context)

        var listaPecas = ArrayList<Categoria>()

        database.use {

            select("tbl_categoria").exec {

                var parser = rowParser{

                    idCategoria: Int,
                    nome: String->

                    Categoria(idCategoria, nome)

                }

                listaPecas = parseList(parser) as ArrayList<Categoria>

            }

        }

        database.close()

        return listaPecas

//        var listaCategoria = ArrayList<Categoria>()
//
//        database = DBHelper(context).readableDatabase
//
//        var SQL = "SELECT * FROM tbl_categoria"
//
//        var cursor = database.rawQuery(SQL, null)
//
//        while (cursor.moveToNext()){
//
//            var categoria = Categoria(cursor.getInt(0), cursor.getString(1))
//
//            listaCategoria.add(categoria)
//
//        }
//
//        cursor.close()
//
//        return listaCategoria

    }

}