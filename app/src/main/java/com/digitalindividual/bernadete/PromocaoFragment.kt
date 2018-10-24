package com.digitalindividual.bernadete

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.digitalindividual.adapters.PromocaoAdapter
import com.digitalindividual.model.Promocao
import com.digitalindividual.util.HttpConnection
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import org.json.JSONArray


class PromocaoFragment : Fragment() {

    private var adapter: PromocaoAdapter? = null
    private lateinit var listView: ListView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_promocao, container, false)

        val tabLayout: TabLayout = view.findViewById(R.id.tab)
        listView = view.findViewById<ListView>(R.id.list)

        val tabMasc = tabLayout.newTab().setText("Masculino")
        val tabFem = tabLayout.newTab().setText("Feminino")
        val tabInf = tabLayout.newTab().setText("Infantil")

        tabLayout.addTab(tabMasc, 0)
        tabLayout.addTab(tabFem, 1)
        tabLayout.addTab(tabInf, 2)

        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {

                when(tab?.position){

                    0-> obterPromocoes("Masculino")
                    1-> obterPromocoes("Feminino")
                    2-> obterPromocoes("Infantil")

                }

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) { Log.d("Ola", "Ola") }

        })


        return view

    }

    override fun onResume() {

        super.onResume()

        obterPromocoes("Masculino")

    }

    fun obterPromocoes(categoria: String){

        doAsync{

            val URL: String = resources.getString(R.string.ip_api)

            val valores = HashMap<String, String>()

            valores.put("categoria", categoria)

            var JSON = HttpConnection.post(URL, valores)

            uiThread {

                var jsonArray = JSONArray(JSON)

                var listaPromocoes = ArrayList<Promocao>()

                for(i in 0..jsonArray.length() - 1){

                    var item = jsonArray.getJSONObject(i)

                    var promocao = Promocao(item.get("nome").toString(),
                            item.get("preco_final").toString().toDouble(),
                            item.get("preco_descontado").toString().toDouble(),
                            item.get("caminho_imagem").toString())

                    Log.d("promocacao", promocao.nome)

                    listaPromocoes.add(promocao)

                }

                adapter = PromocaoAdapter(context, listaPromocoes)

                listView.adapter = adapter

            }

        }

    }

    companion object {
        fun newInstance(): PromocaoFragment = PromocaoFragment()
    }

}
