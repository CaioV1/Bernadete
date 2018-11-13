package com.digitalindividual.bernadete

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import org.jetbrains.anko.find
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class NotificationActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        setDrawerLayout()

    }

    fun setDrawerLayout(){

        toolbar = findViewById<Toolbar>(R.id.my_toolbar)

        setSupportActionBar(toolbar)

        drawer = findViewById<DrawerLayout>(R.id.notification_activity)

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

                R.id.roupa -> startActivity<MainActivity>()
                R.id.notificacao -> longToast("Essa é a tela de notificações")
                R.id.tag -> toast("Tag")

            }

            true

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

    fun openRegister(view: View){

        startActivity<RegisterNotification>()

    }

}
