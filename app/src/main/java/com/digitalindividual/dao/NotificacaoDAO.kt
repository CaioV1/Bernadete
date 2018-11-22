package com.digitalindividual.dao

import android.content.Context
import android.util.Log
import com.digitalindividual.model.Notificacao
import com.digitalindividual.model.Peca
import com.digitalindividual.util.DBHelper
import com.digitalindividual.util.ImageConvert
import org.jetbrains.anko.db.insert

/**
 * Created by 17170077 on 13/11/2018.
 */
class NotificacaoDAO {

    companion object {

        val instance = NotificacaoDAO()

    }

    fun inserir(context: Context, notificacao: Notificacao): Boolean{

        val database = DBHelper.getInstance(context)

        var id:Long = 0

        database.use {

            id = insert("tbl_notificacao",
                    "titulo" to notificacao.titulo,
                    "data" to notificacao.data,
                    "local" to notificacao.local,
                    "descricao" to notificacao.descricao)

        }

        var idRelacao:Long = 0

        database.use {

            idRelacao = insert("tbl_peca_notificacao",
                            "id_notificacao" to id,
                                    "id_peca" to notificacao.idPeca)

        }

        database.close()

        return id.toInt() != -1 || idRelacao.toInt() != -1

    }

    fun obterTodos(context: Context): ArrayList<Notificacao>{

        var listaNotificacao = ArrayList<Notificacao>()

        val database = DBHelper(context).readableDatabase

        var SQL = "SELECT n.*, p.id_peca, p.nome FROM tbl_notificacao AS n " +
                  "INNER JOIN tbl_peca_notificacao AS pn ON n.id_notificacao = pn.id_notificacao " +
                  "INNER JOIN tbl_peca AS p ON p.id_peca = pn.id_peca ORDER BY n.data DESC"

        var cursor = database.rawQuery(SQL, null)

        while (cursor.moveToNext()){

            var notificacao = Notificacao(cursor.getInt(0), cursor.getString(1), cursor.getLong(2), cursor.getString(3), cursor.getString(4))

            notificacao.idPeca = cursor.getInt(5)
            notificacao.peca = cursor.getString(6)

            listaNotificacao.add(notificacao)

        }

        cursor.close()

        database.close()

        return listaNotificacao

    }

    fun obterPorPeca(context: Context, id: Int): ArrayList<Notificacao>{

        var listaNotificacao = ArrayList<Notificacao>()

        val database = DBHelper(context).readableDatabase

        var SQL = "SELECT n.*, p.id_peca, p.nome FROM tbl_notificacao AS n " +
                "INNER JOIN tbl_peca_notificacao AS pn ON n.id_notificacao = pn.id_notificacao " +
                "INNER JOIN tbl_peca AS p ON p.id_peca = pn.id_peca WHERE pn.id_peca = ${id} ORDER BY n.data DESC"

        var cursor = database.rawQuery(SQL, null)

        while (cursor.moveToNext()){

            var notificacao = Notificacao(cursor.getInt(0), cursor.getString(1), cursor.getLong(2), cursor.getString(3), cursor.getString(4))

            notificacao.idPeca = cursor.getInt(5)
            notificacao.peca = cursor.getString(6)

            Log.d("!!!!!!!!!", notificacao.idPeca.toString())

            listaNotificacao.add(notificacao)

        }

        cursor.close()

        database.close()

        return listaNotificacao

    }

}