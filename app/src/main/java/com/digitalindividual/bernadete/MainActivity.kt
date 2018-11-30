package com.digitalindividual.bernadete

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.digitalindividual.util.AlarmReceiver
import org.jetbrains.anko.find
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : AppCompatActivity(){

    lateinit var toolbar: Toolbar

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDrawerLayout()

        val bottomNavegation = findViewById<BottomNavigationView>(R.id.navigation)

        val principal = MainFragment.newInstance()
        openFragment(principal)

        bottomNavegation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    fun setDrawerLayout(){

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

        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        navigationView.bringToFront()

        navigationView.setNavigationItemSelectedListener{

            when(it.itemId){

                R.id.roupa -> longToast("Essa é a tela de peça de roupa")
                R.id.catalogacao -> startActivity<RegisterPiece>()
                R.id.notificacao -> startActivity<NotificationActivity>()
                R.id.item_cadastro -> startActivity<RegisterNotification>()
//                R.id.tag -> toast("Tag")

            }

            true

        }

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

//                createAlarm(this)

                toolbar.title = "Look do Dia"

                val look = LookFragment.newInstance()
                openFragment(look)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {

                toolbar.title = "Promoções"

                val promocao = SaleFragment.newInstance()
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
