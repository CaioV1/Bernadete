package com.digitalindividual.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.digitalindividual.bernadete.R
import com.digitalindividual.dao.NotificacaoDAO
import com.digitalindividual.dao.PecaDAO
import com.digitalindividual.model.Peca
import org.jetbrains.anko.find

class ClosetAdapter : PagerAdapter {

    private var context: Context
    private var layoutInflater: LayoutInflater? = null
//    private var images: IntArray = intArrayOf(R.drawable.bota_1, R.drawable.bota_2, R.drawable.bota_3, R.drawable.bota_4)
    private var pecaDAO = PecaDAO()
    private var listaPecas = ArrayList<Peca>()

    constructor(context: Context, listaPecas: ArrayList<Peca>){

        this.context = context
        this.listaPecas = listaPecas

    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {

        return view == `object`

    }

    override fun getCount(): Int {

        return listaPecas.size

    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {

        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var view: View = layoutInflater!!.inflate(R.layout.slide_closet, null)

        var image: ImageView = view.findViewById(R.id.img_closet)
        var txtNome: TextView = view.find(R.id.txt_nome)
        var txtCategoria: TextView = view.find(R.id.txt_categoria_slide)
        val listView = view.find<ListView>(R.id.list_piece_notification)

        val notificacaoDAO = NotificacaoDAO.instance

        val adapter = NotificationAdapter(view.context, notificacaoDAO.obterPorPeca(view.context, listaPecas.get(position).id))

        listView.adapter = adapter

        image.setImageBitmap(listaPecas.get(position).imagem)

        txtNome.setText(listaPecas.get(position).nome)
        txtCategoria.setText(listaPecas.get(position).categoria)

        var vpCloset: ViewPager = container as ViewPager

        vpCloset.addView(view, 0)

        return view

    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {

        var vpCloset: ViewPager = container as ViewPager

        var view: View = `object` as View

        vpCloset.removeView(view)

    }

}