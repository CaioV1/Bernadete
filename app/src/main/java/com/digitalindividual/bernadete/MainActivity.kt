package com.digitalindividual.bernadete

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var drawer: DrawerLayout

    

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                toolbar.title = "Guarda Roupa"

                val principal = MainFragment.newInstance()
                openFragment(principal)

                return@OnNavigationItemSelectedListener true

            }
            R.id.navigation_dashboard -> {

                toolbar.title = "Look do Dia"

                val look = LookFragment.newInstance()
                openFragment(look)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {

                toolbar.title = "Promoções"

                val promocao = PromocaoFragment.newInstance()
                openFragment(promocao)

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment){

        val transacao = supportFragmentManager.beginTransaction()
        transacao.replace(R.id.container, fragment)
        transacao.addToBackStack(null)
        transacao.commit()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<Toolbar>(R.id.my_toolbar)

        drawer = findViewById<DrawerLayout>(R.id.activity_main)
        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)

        drawer.addDrawerListener(toggle)

        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bottomNavegation = findViewById<BottomNavigationView>(R.id.navigation)

        toolbar.title = "Guarda Roupa"

        val principal = MainFragment.newInstance()
        openFragment(principal)

        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.look)

        bottomNavegation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(toggle.onOptionsItemSelected(item)){

            return true

        }

        return super.onOptionsItemSelected(item)
    }
}
