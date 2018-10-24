package com.digitalindividual.model

import android.graphics.Bitmap

/**
 * Created by 17170077 on 17/10/2018.
 */
data class Peca(var id: Int,
                var idTipoFiltro: Int,
                var idFiltro: Int,
                var idCategoria: Int,
                var nome: String,
                var descricao: String,
                var categoria: String,
                var filtro: String,
                var tipoFiltro: String,
                var imagem: Bitmap){

}
