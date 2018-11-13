package com.digitalindividual.bernadete

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.digitalindividual.adapters.SaleAdapter
import com.digitalindividual.model.Promocao
import com.digitalindividual.util.HttpConnection
import org.jetbrains.anko.*
import org.json.JSONArray
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.net.ConnectException


class SaleFragment : Fragment() {

    private var adapter: SaleAdapter? = null
    private lateinit var listView: ListView
    private lateinit var txtError: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_promocao, container, false)

        val tabLayout: TabLayout = view.findViewById(R.id.tab)
        listView = view.findViewById<ListView>(R.id.list)
        txtError = view.find<TextView>(R.id.txt_error_api)

        val tabMasc = tabLayout.newTab().setText("Masculino")
        val tabFem = tabLayout.newTab().setText("Feminino")
        val tabInf = tabLayout.newTab().setText("Infantil")

        tabLayout.addTab(tabMasc, 0)
        tabLayout.addTab(tabFem, 1)
        tabLayout.addTab(tabInf, 2)

        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {

                when(tab?.position){

                    0->

                        if(obterPromocoes("Masculino")){

                            listView.visibility = View.GONE
                            txtError.visibility = View.VISIBLE

                        }

                    1->

                        if(obterPromocoes("Feminino")){

                            listView.visibility = View.GONE
                            txtError.visibility = View.VISIBLE

                        }

                    2->

                        if(obterPromocoes("Infantil")){

                            listView.visibility = View.GONE
                            txtError.visibility = View.VISIBLE

                        }

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

    fun obterPromocoes(categoria: String): Boolean {

        var requisicao = false

        doAsync{

            val URL: String = resources.getString(R.string.ip_api_sale)

            val valores = HashMap<String, String>()

            valores.put("categoria", categoria)

            try {

                var JSON = HttpConnection.post(URL, valores)

                if(JSON == ""){

                    requisicao = false

                } else {

                    requisicao = true

                    uiThread {

                        var jsonArray: JSONArray = JSONArray()

                        jsonArray = JSONArray(JSON)

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

                        adapter = SaleAdapter(context, listaPromocoes)

                        listView.adapter = adapter

                    }

                }

            } catch (ec: ConnectException) {

                ec.stackTrace

            } catch (ex: IOException) {

                ex.stackTrace

            } catch (ei: InvocationTargetException){

                ei.stackTrace

            } catch (e: Exception){

                e.stackTrace

            }

        }

        return requisicao

    }

    companion object {
        fun newInstance(): SaleFragment = SaleFragment()
    }

}
