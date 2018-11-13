package com.digitalindividual.dao

import android.content.Context
import com.digitalindividual.model.Notificacao
import com.digitalindividual.util.DBHelper
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

        return id.toInt() != -1

    }

}