package com.digitalindividual.bernadete

import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.digitalindividual.util.DBHelper
import org.jetbrains.anko.db.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class PresentationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presentation)

        val handler:Handler = Handler()

        val database = DBHelper(applicationContext).readableDatabase

        val SQL = "SELECT id_usuario FROM tbl_usuario"

        val cursor = database.rawQuery(SQL, null)

        if(cursor.count == 0){

            handler.postDelayed(Runnable {

                startActivity<LoginActivity>()

            }, 1000)

        } else {

            handler.postDelayed(Runnable {

                startActivity<MainActivity>()

            }, 1000)

        }

        cursor.close()

        database.close()

        handler.postDelayed(Runnable {

            startActivity<MainActivity>()

        }, 1000)

    }
}
