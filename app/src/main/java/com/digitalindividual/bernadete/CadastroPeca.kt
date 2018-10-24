package com.digitalindividual.bernadete

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.digitalindividual.adapters.CategoriaAdapter
import com.digitalindividual.dao.PecaDAO
import com.digitalindividual.model.Categoria
import com.digitalindividual.model.Peca
import org.jetbrains.anko.*
import java.io.FileNotFoundException
import java.io.InputStream

class CadastroPeca : AppCompatActivity() {

    var COD_GALLERY: Int = 1
    lateinit var imagePeca: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro_peca)

//        val btnFiltro = findViewById<Button>(R.id.btn_filtro)
        val btnSalvar = find<Button>(R.id.btn_salvar)

        val spinner = find<Spinner>(R.id.spn_categoria)

        val txtNome = find<TextView>(R.id.txt_peca_nome)
        val txtDescricao = find<TextView>(R.id.txt_peca_descricao)
        val txtTitulo = find<TextView>(R.id.txt_titulo_cadastro)

        imagePeca = findViewById<ImageView>(R.id.img_piece)

        val pecaDAO = PecaDAO()

        val listaCategoria = pecaDAO.obterCategoria(this)

        val adapter = CategoriaAdapter(this, listaCategoria)

        spinner.adapter = adapter

        val idPeca = intent.getIntExtra("idPeca", 0)

        var peca: Peca? = null

        if(idPeca != 0){

            peca = pecaDAO.obterUm(this, idPeca)

            txtNome.setText(peca?.nome)
            txtDescricao.setText(peca?.descricao)
            imagePeca.setImageBitmap(peca?.imagem)

            txtTitulo.setText(resources.getString(R.string.title_update_piece))

        } else {

            txtTitulo.setText(resources.getString(R.string.title_register_piece))

        }

        toast(intent.getIntExtra("idPeca", 0).toString())

        btnSalvar.setOnClickListener(View.OnClickListener {

            var categoria: Categoria = spinner.selectedItem as Categoria

            var pecaDAO = PecaDAO.instance

//            var bitmap = Bitmap.createBitmap(imagePeca.drawable.intrinsicWidth, imagePeca.drawable.intrinsicHeight, Bitmap.)

            val bitmap:BitmapDrawable = imagePeca.drawable as BitmapDrawable

            var peca: Peca = Peca(0,
                                  0,
                                  0,
                                   categoria.idCategoria,
                                   txtNome.text.toString(),
                                   txtDescricao.text.toString(),
                                   categoria.nome,
                                  "",
                                  "",
                                   bitmap.bitmap)

            if(idPeca == 0){

                if(pecaDAO.inserir(this, peca)){

                    toast("Cadastrado com sucesso")

                } else {

                    longToast("Ocorreu um erro. Tente Novamente")

                }

            } else {

                peca.id = idPeca

                if(pecaDAO.atualizar(this, peca)){

                    toast("Atualizado com sucesso")

                } else {

                    longToast("Ocorreu um erro. Tente Novamente")

                }

            }

            startActivity(intentFor<MainActivity>())

        })

    }

    fun openGallery(view: View){

        var intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")

        startActivityForResult(intent, COD_GALLERY)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == COD_GALLERY){

            if(resultCode == Activity.RESULT_OK){

                try {

                    val inputStream: InputStream = contentResolver.openInputStream(data?.data)

                    val imagem = BitmapFactory.decodeStream(inputStream)

                    imagePeca.setImageBitmap(imagem)

                } catch (e: FileNotFoundException){

                    e.printStackTrace()

                }

            }

        }

    }

}
