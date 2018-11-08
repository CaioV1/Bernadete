package com.digitalindividual.bernadete

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.digitalindividual.util.DBHelper
import com.digitalindividual.util.HttpConnection
import org.jetbrains.anko.*
import org.jetbrains.anko.db.insert
import org.json.JSONObject
import org.jetbrains.anko.db.*

class LoginActivity : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var txtSenha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtEmail = find<EditText>(R.id.txt_email)
        txtSenha = find<EditText>(R.id.txt_senha)

    }

    fun login(view:View){

        doAsync {

            val values = HashMap<String, String>()

            values.put("email", txtEmail.text.toString())
            values.put("senha", txtSenha.text.toString())

            val JSON = HttpConnection.post(resources.getString(R.string.ip_api_login), values)

            uiThread {

                if(JSON != ""){

                    var objectJSON = JSONObject(JSON)

                    if(objectJSON.getBoolean("autenticacao")){

                        val database = DBHelper.getInstance(applicationContext)

                        val email = objectJSON.getString("email")
                        val nome = objectJSON.getString("nome")

                        database.use {

                            insert("tbl_usuario", "email" to email, "nome" to nome)

                        }

                        database.close()

                        toast("Autenticado com sucesso.")

                        startActivity<MainActivity>()

                    } else {

                        longToast("E-mail ou senha incorreto. Tente novamente ou cadastre-se.")

                    }

                }


            }

        }

    }

}
