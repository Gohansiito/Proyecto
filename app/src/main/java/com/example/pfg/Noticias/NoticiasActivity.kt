package com.example.pfg.Noticias

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pfg.Cronometro.CronoActivity
import com.example.pfg.Musica.MusicActivity
import com.example.pfg.R
import com.example.pfg.Rutinas.RutinasActivity
import com.google.android.material.navigation.NavigationView


class NoticiasActivity : AppCompatActivity() {

    //Variables para el drawer
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.noticias)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<NoticiasViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        data.add(NoticiasViewModel(R.drawable.logo, "Competicion BARSBONA 3 Septiembre"))


        // This will pass the ArrayList to our Adapter
        val adapter = AdapterNoticias(data)

        // Setting the Adapter with the recyclerview

        recyclerview.adapter = adapter
        adapter.setOnItemClickListener(object : AdapterNoticias.onItemClickListener {
            override fun onItemClick(position: Int) {
                //Aqui se mostrara un Alert en el cual se veran los datos de la noticia
                val dialogBuilder = AlertDialog.Builder(this@NoticiasActivity)

                // set message of alert dialog
                dialogBuilder.setMessage("El proximo dia 3 de Septiembre sera oficialmente la primera competicion organizada por BARSBONA, las batallas seran simplemente 1vs1 los cuales se organizaran entre los propios competidores para evitar disparidad de nivel")
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // negative button text and action
                    .setNegativeButton("Cerrar") { dialog, id ->
                        dialog.cancel()
                    }
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Competicion BARSBONA")
                // show alert dialog
                alert.show()
            }

        })

        //Añadimos el drawer
        drawerLayout = findViewById(R.id.drawerLayout)
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)

        // Muestra la hamburguesa del drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Cuando se abra el drawer su icono cambiara con esto
        actionBarToggle.syncState()

        navView = findViewById(R.id.navView)

        // Un toast que responde cuando un elemento es clicado
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.myProfile -> {
                    Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.musica -> {
                    val newIntent = Intent(this, MusicActivity::class.java)
                    startActivity(newIntent)
                    true
                }
                R.id.novedades -> {
                    val newIntent = Intent(this, NoticiasActivity::class.java)
                    startActivity(newIntent)
                    true
                }
                R.id.cronometro -> {
                    val newIntent = Intent(this, CronoActivity::class.java)
                    startActivity(newIntent)
                    true
                }
                R.id.rutinas -> {
                    val newIntent = Intent(this, RutinasActivity::class.java)
                    startActivity(newIntent)
                    true
                }
                else -> {
                    false
                }
            }
        }

    }
    // Lanza el drawer cuando se clicka la hamburguesa
    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navView)
        return true
    }

    // Cierra el drawer  al retroceder
    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}