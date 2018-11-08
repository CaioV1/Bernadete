package com.digitalindividual.bernadete

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<Toolbar>(R.id.my_toolbar)

        setSupportActionBar(toolbar)

        drawer = findViewById<DrawerLayout>(R.id.activity_main)
        toggle = object : ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close){

            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
                invalidateOptionsMenu()
            }

        }

        toggle.isDrawerIndicatorEnabled = true
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val bottomNavegation = findViewById<BottomNavigationView>(R.id.navigation)

        val principal = MainFragment.newInstance()
        openFragment(principal)

        bottomNavegation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

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

    override fun onBackPressed() {

        super.onBackPressed()

        if(supportFragmentManager.backStackEntryCount == 0){

            finishAffinity()

        } else {

            fragmentManager.popBackStack()

        }

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        toggle.syncState()

    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        toggle.onConfigurationChanged(newConfig)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(toggle.onOptionsItemSelected(item)){

            return true

        }

        return super.onOptionsItemSelected(item)
    }
}
