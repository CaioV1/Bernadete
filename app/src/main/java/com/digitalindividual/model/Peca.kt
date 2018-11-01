package com.digitalindividual.model

import android.graphics.Bitmap
import java.io.Serializable

/**
 * Created by 17170077 on 17/10/2018.
 */
data class Peca(var id: Int,
                var idCategoria: Int,
                var nome: String,
                var descricao: String,
                var categoria: String,
                var imagem: Bitmap?) : Serializable

//    constructor():this(0, 0, 0, 0, "", "", "", "", "", null as Bitmap)
