package com.example.pfg.Rutinas

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
import com.example.pfg.Noticias.NoticiasActivity
import com.example.pfg.R
import com.google.android.material.navigation.NavigationView


class RutinasActivity : AppCompatActivity() {

    //Variables para el drawer
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rutinas)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<RutinasViewModel>()

        for (i in 1..3) {
            data.add(RutinasViewModel(R.drawable.ic_baseline_fitness_center_24, "Rutina " + i))
        }

        val adapter = AdapterRutinas(data)


        recyclerview.adapter = adapter
        adapter.setOnItemClickListener(object : AdapterRutinas.onItemClickListener {
            override fun onItemClick(position: Int) {
                //Aqui se mostrara un Alert en el cual se veran los datos de la rutina
                val dialogBuilder = AlertDialog.Builder(this@RutinasActivity)

                var rondas = 2*(position+1) -1
                var series = 2*(position+1)
                var repes = 4*(position+1)
                var descanso = 60/(position+1)
                dialogBuilder.setMessage("Rondas: $rondas \nSeries: $series \nRepeticiones: $repes \nDescanso: $descanso")
                    .setCancelable(false)
                    .setPositiveButton("Ir a cronometro") { dialog, i ->
                        abrirCrono()
                    }
                    .setNegativeButton("Cerrar") { dialog, id ->
                        dialog.cancel()
                    }
                val alert = dialogBuilder.create()
                alert.setTitle("Dominadas nivel: $position")
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
    private fun abrirCrono(){
        val newIntent = Intent(this, CronoActivity::class.java)
        startActivity(newIntent)
    }

}