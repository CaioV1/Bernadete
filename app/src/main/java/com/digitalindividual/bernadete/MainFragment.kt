package com.digitalindividual.bernadete

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.digitalindividual.adapters.ClosetAdapter
import com.digitalindividual.dao.PecaDAO
import com.digitalindividual.model.Peca
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import org.jetbrains.anko.find
import org.jetbrains.anko.intentFor

class MainFragment : Fragment() {

    private var idPeca: Int = 0
    private var position: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_main, container, false)

        var vpCloset = view.findViewById<ViewPager>(R.id.vp_closet)

        val menuFlutuante = view.find<FloatingActionMenu>(R.id.floating_menu)
        val btnRemover = view.find<FloatingActionButton>(R.id.floating_button_delete)
        val btnAdicionar = view.find<FloatingActionButton>(R.id.floating_button_add)
        val btnEditar = view.find<FloatingActionButton>(R.id.floating_button_edit)

        var pecaDAO = PecaDAO.instance

        val listaPecas = pecaDAO.obterPecas(view.context)

//        idPeca = listaPecas.get(0).id

        val vpAdapter: ClosetAdapter = ClosetAdapter(view.context, listaPecas)

        vpCloset.adapter = vpAdapter

        vpCloset.setCurrentItem(activity.intent.getIntExtra("posicao", 0), true)

        btnAdicionar.setOnClickListener(View.OnClickListener {

            val intent = Intent(view.context, CadastroPeca::class.java)

            startActivity(intent)

        })

        btnRemover.setOnClickListener(View.OnClickListener {

            val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)

            var peca: Peca? = pecaDAO.obterUm(view.context, idPeca)

            builder.setMessage("Deseja realmente excluir a peça de roupa ${peca?.nome}?")

            builder.setPositiveButton("Sim") { dialogInterface, i ->

                if(pecaDAO.remover(view.context, idPeca)){

                    Toast.makeText(view.context, "Removido com sucesso.", Toast.LENGTH_SHORT).show()

                    startActivity(view.context.intentFor<MainActivity>())

                } else {

                    Toast.makeText(view.context, "Ocorreu um erro tente novamente.", Toast.LENGTH_LONG).show()

                }

            }

            builder.setNegativeButton("Não"){dialogInterface, i ->

                Toast.makeText(view.context, "Remoção cancelada", Toast.LENGTH_SHORT).show()

            }

            builder.show()

        })

        btnEditar.setOnClickListener(View.OnClickListener {

            val intent = Intent(view.context, CadastroPeca::class.java)

            intent.putExtra("idPeca", idPeca)
            intent.putExtra("posicao", this.position)

            startActivity(intent)

        })

        vpCloset.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{

            override fun onPageScrollStateChanged(state: Int) {

                Log.d("d",state.toString())

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                idPeca = listaPecas.get(position).id

                this@MainFragment.position = position

                Toast.makeText(view.context, "Posição = ${position} | ID = ${idPeca} | Tamanho = ${listaPecas.size} | Nome = ${listaPecas.get(position).nome}", Toast.LENGTH_LONG).show()

            }

            override fun onPageSelected(position: Int) {

                Log.d("p",position.toString())

            }

        })

        return view

 }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

}
