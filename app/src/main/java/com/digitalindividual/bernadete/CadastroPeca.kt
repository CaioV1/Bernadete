package com.digitalindividual.bernadete

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.digitalindividual.adapters.CategoriaAdapter
import com.digitalindividual.adapters.FiltroAdapter
import com.digitalindividual.dao.FiltroDAO
import com.digitalindividual.dao.PecaDAO
import com.digitalindividual.model.Categoria
import com.digitalindividual.model.Filtro
import com.digitalindividual.model.Peca
import com.digitalindividual.util.DataHolder
import com.digitalindividual.util.ImageConvert
import org.jetbrains.anko.*
import java.io.FileNotFoundException
import java.io.InputStream

class CadastroPeca : AppCompatActivity() {

    var COD_GALLERY: Int = 1
    lateinit var imagePeca: ImageView
    val filtroDAO = FiltroDAO.instance
    val listaImagens = DataHolder.instance.listaDrawable
    val listaFiltro = DataHolder.instance.listaFiltro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro_peca)

        if(intent.getIntExtra("idFiltro", 0) == 0){

            listaFiltro.clear()

        }

        val btnFiltro = findViewById<Button>(R.id.btn_filtro)
        val btnSalvar = find<Button>(R.id.btn_salvar)

        val spinner = find<Spinner>(R.id.spn_categoria)

        var listView = findViewById<ListView>(R.id.list_selected_filter)

        val txtNome = find<TextView>(R.id.txt_peca_nome)
        val txtDescricao = find<TextView>(R.id.txt_peca_descricao)
        val txtTitulo = find<TextView>(R.id.txt_titulo_cadastro)

        imagePeca = findViewById<ImageView>(R.id.img_piece)

        val pecaDAO = PecaDAO()

        val listaCategoria = pecaDAO.obterCategoria(this)

        val adapter = CategoriaAdapter(this, listaCategoria)

        spinner.adapter = adapter

        var filtro: Filtro? = null

        val idPeca = intent.getIntExtra("idPeca", 0)

        var peca: Peca? = null

        if(idPeca != 0){

            peca = pecaDAO.obterUm(this, idPeca)

            txtNome.setText(peca?.nome)
            txtDescricao.setText(peca?.descricao)
            imagePeca.setImageBitmap(peca?.imagem)

            spinner.setSelection(getPosition(spinner, peca?.idCategoria))

            if(intent.getIntExtra("idFiltro", 0) == 0) {

                listaFiltro.clear()

                toast("${idPeca} e ${peca?.nome}")

                var listaTemp = filtroDAO.obterPorPeca(this, peca?.id)

                listaTemp.forEach {

                    Log.d("Filtro", it.filtro)
                    listaFiltro.add(it)

                }

            }

            var filtroAdapter = FiltroAdapter(this, listaFiltro, "Filtro")

            var layout: ViewGroup.LayoutParams = listView.layoutParams

            var tamanho = listaFiltro.size * 250

            layout.height = tamanho

            listView.layoutParams = layout

            listView.adapter = filtroAdapter

            txtTitulo.setText(resources.getString(R.string.title_update_piece))

        } else {

            txtTitulo.setText(resources.getString(R.string.title_register_piece))

        }

        if(intent.getIntExtra("idFiltro", 0) != 0){

            listaFiltro.forEach {

                Log.d("Filtros Intent", it.filtro)

            }

            filtro = filtroDAO.obterUm(this, intent.getIntExtra("idFiltro", 0))

            var posicao = intent.getIntExtra("posicao", -1)

            var peca = intent.getSerializableExtra("ObjetoPeca") as Peca

            var drawable = listaImagens.get(0)

            listaImagens.clear()

            peca.imagem = ImageConvert.turnToBitmap(drawable)

            txtNome.setText(peca.nome)
            txtDescricao.setText(peca.descricao)
            imagePeca.setImageBitmap(peca.imagem)

            toast(peca.nome.toString())

            if(posicao != -1){

                listaFiltro.set(posicao, filtro as Filtro)

            } else {

                listaFiltro.add(filtro as Filtro)

            }

            var filtroAdapter = FiltroAdapter(this, listaFiltro, "Filtro")

            var layout: ViewGroup.LayoutParams = listView.layoutParams

            var tamanho = listaFiltro.size * 250

            layout.height = tamanho

            listView.layoutParams = layout

            listView.adapter = filtroAdapter

        }

        btnFiltro.setOnClickListener(View.OnClickListener {

            var categoria: Categoria = spinner.selectedItem as Categoria

            listaImagens.add(imagePeca.drawable)

            var peca = Peca(idPeca, categoria.idCategoria, txtNome.text.toString(), txtDescricao.text.toString(), "",null)

            var intent = Intent(this, com.digitalindividual.bernadete.Filtro::class.java)

            var listaId = ArrayList<Int>()

            listaFiltro.forEach {

                listaId.add(it.idTipoFiltro)

            }

            intent.putExtra("Lista", listaId)
            intent.putExtra("ObjetoPeca", peca)
            intent.putExtra("Dado", "Tipo")
            intent.putExtra("id", 0)
            intent.putExtra("idPeca", idPeca)

            startActivity(intent)

        })

        btnSalvar.setOnClickListener(View.OnClickListener {

            var categoria: Categoria = spinner.selectedItem as Categoria

            var pecaDAO = PecaDAO.instance

            val bitmap:BitmapDrawable = imagePeca.drawable as BitmapDrawable

            var peca: Peca = Peca(0,
                                   categoria.idCategoria,
                                   txtNome.text.toString(),
                                   txtDescricao.text.toString(),
                                   categoria.nome,
                                   bitmap.bitmap)

            if(idPeca == 0){

                val idPeca = pecaDAO.inserir(this, peca).toInt()

//                longToast(idPeca.toString())

                if(filtroDAO.inserirFiltros(this, listaFiltro, idPeca)){

                    toast("Cadastrado com sucesso")

                } else {

                    longToast("Ocorreu um erro. Tente Novamente")

                }

            } else {

                peca.id = idPeca

                if(pecaDAO.atualizar(this, peca) && filtroDAO.atualizar(this, listaFiltro, idPeca)){

                    toast("Atualizado com sucesso")

                } else {

                    longToast("Ocorreu um erro. Tente Novamente")

                }

            }

            startActivity(intentFor<MainActivity>("posicao" to intent.getIntExtra("posicao", 0)))

        })

    }

    override fun onBackPressed() {

        super.onBackPressed()

        startActivity(intentFor<MainActivity>())

    }

    fun getPosition(spinner: Spinner, idItem: Int?): Int{

        var categoria: Categoria? = null

        var posicao = 0

        var i = 0

        while(i < spinner.adapter.count){

            categoria = spinner.getItemAtPosition(i) as Categoria?

            if(idItem == categoria?.idCategoria){

                posicao = i

            }

            i++

        }

        return posicao

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
