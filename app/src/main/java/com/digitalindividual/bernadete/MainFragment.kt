package com.digitalindividual.bernadete

import android.app.AlertDialog
import android.content.Context
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
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import com.digitalindividual.adapters.ClosetAdapter
import com.digitalindividual.adapters.NotificationAdapter
import com.digitalindividual.dao.NotificacaoDAO
import com.digitalindividual.dao.PecaDAO
import com.digitalindividual.model.Peca
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import org.jetbrains.anko.find
import org.jetbrains.anko.intentFor
import java.util.ArrayList

class MainFragment : Fragment() {

    private var idPeca: Int = 0
    private var position: Int = 0
    private var listaPecas = ArrayList<Peca>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_main, container, false)

        var vpCloset = view.findViewById<ViewPager>(R.id.vp_closet)

        val menuFlutuante = view.find<FloatingActionMenu>(R.id.floating_menu)
        val btnRemover = view.find<FloatingActionButton>(R.id.floating_button_delete)
        val btnAdicionar = view.find<FloatingActionButton>(R.id.floating_button_add)
        val btnEditar = view.find<FloatingActionButton>(R.id.floating_button_edit)

        val txtMessage = view.find<TextView>(R.id.txt_no_clothes)

        var pecaDAO = PecaDAO.instance

        listaPecas = pecaDAO.obterPecas(view.context)

        if(listaPecas.count() == 0){

            vpCloset.visibility = View.GONE
            txtMessage.visibility = View.VISIBLE
            txtMessage.visibility = View.VISIBLE


        }

//        idPeca = listaPecas.get(0).id

        val vpAdapter: ClosetAdapter = ClosetAdapter(view.context, listaPecas)

        vpCloset.adapter = vpAdapter

        vpCloset.setCurrentItem(activity.intent.getIntExtra("posicao", 0), true)

        val notificacao = activity.intent.getIntExtra("id", 0)

        if(notificacao != 0){

            vpCloset.setCurrentItem(findPosition(notificacao), true)

        }

        btnAdicionar.setOnClickListener(View.OnClickListener {

            val intent = Intent(view.context, RegisterPiece::class.java)

            startActivity(intent)

        })

        btnRemover.setOnClickListener(View.OnClickListener {

            val builder: AlertDialog.Builder = AlertDialog.Builder(view.context, R.style.AlertDialogStyle)

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

            val intent = Intent(view.context, RegisterPiece::class.java)

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

                //Toast.makeText(view.context, "Posição = ${position} | ID = ${idPeca} | Tamanho = ${listaPecas.size} | Nome = ${listaPecas.get(position).nome}", Toast.LENGTH_LONG).show()

            }

            override fun onPageSelected(position: Int) {

                Log.d("p",position.toString())

            }

        })

        return view

 }

    fun findPosition(id:Int): Int{

        for (peca in listaPecas){

            if(peca.id == id){

                position = listaPecas.indexOf(peca)

            }

        }

        return position

    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

}
