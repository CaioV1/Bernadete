package com.digitalindividual.model

/**
 * Created by 17170077 on 17/10/2018.
 */
data class Notificacao(var id: Int,
                       var titulo: String,
                       var data: Long,
                       var local: String,
                       var descricao: String){

    var peca: String = ""
    var idPeca: Int = 0
    var hora: Int = 0

}
