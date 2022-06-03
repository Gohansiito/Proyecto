package com.example.pfg.Musica

import android.content.ComponentName
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.pfg.Cronometro.CronoActivity
import com.example.pfg.Noticias.NoticiasActivity
import com.example.pfg.R
import com.example.pfg.Rutinas.RutinasActivity
import com.example.pfg.databinding.MusicaBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.lang.Exception
import java.util.concurrent.TimeUnit


class MusicActivity:AppCompatActivity() {
    private lateinit var binding: MusicaBinding

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    private lateinit var runnable: Runnable

    private  val artistas= listOf("Scatman Jhon","Julian Alfred Pankratz","The Rock","Eminem","Pascu y rodri","Imagine Dragons")
    private val nombres= listOf("Scatmans World","Toss a coin to your Witcher","Face Off","Godzilla","Afrodita","My enemy")
    private val imagenes= listOf(
        R.drawable.caratula_papo,
        R.drawable.caratula_witcher,
        R.drawable.caratula_faceoff,
        R.drawable.caratula_godzilla,
        R.drawable.caratula_afrodita,
        R.drawable.caratula_enemy
    )
    private val musicas= listOf(
        R.raw.papo,
        R.raw.witcher,
        R.raw.face_off,
        R.raw.godzilla,
        R.raw.afrodita,
        R.raw.enemy
    )
    private var canciones= mutableListOf<Cancion>()
    private var pausado=false
    private lateinit var seekBar:SeekBar
    private var handler=Handler()
    private var minutos:Long=0
    private var segundos:Long=0


    private var mediaPlayer:MediaPlayer?=null
    private var indice:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.musica)
        binding= MusicaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        anadirCanciones()
        bindeado(indice)
        mediaPlayer=MediaPlayer.create(this,canciones[indice].getCancion())
        findViewById<SeekBar>(R.id.sb_barra_duracion).max=mediaPlayer!!.duration
        findViewById<TextView>(R.id.tv_tiempo_total).text=tiempos(mediaPlayer!!.duration)


        findViewById<FloatingActionButton>(R.id.siguiente).setOnClickListener{
            siguiente()
        }
        findViewById<FloatingActionButton>(R.id.atras).setOnClickListener {
            retroceder()
        }
        findViewById<FloatingActionButton>(R.id.play_pausa).setOnClickListener{
            cambiarIcono()
        }
        findViewById<SeekBar>(R.id.sb_barra_duracion).setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progreso: Int, delusuario: Boolean) {
                if (delusuario)
                    mediaPlayer!!.seekTo(progreso)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })



        runnable= Runnable {
            findViewById<SeekBar>(R.id.sb_barra_duracion).progress=mediaPlayer!!.currentPosition
            findViewById<TextView>(R.id.tv_tiempo_actual).text=tiempos(mediaPlayer!!.currentPosition)
            mediaPlayer!!.setOnCompletionListener {
                siguiente()
            }
            handler.postDelayed(runnable,1000)

        }
        handler.postDelayed(runnable,1000)




        //El drawer
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
                    val componetName = ComponentName(
                        "com.google.mlkit.vision.demo",  //This is the package name of another application
                        "android.intent.action.MAIN"
                    ) //This parameter is the full pathname of the Activity to start
                    try {
                        val intent = Intent()
                        intent.component = componetName
                        startActivity(intent)
                    } catch (e: Exception) {

                    }
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
    private fun bindeado(indice:Int){
        binding.ivCaratula.setImageResource(canciones[indice].getImagen())
        binding.tvTitulo.text=canciones[indice].getNombre()
    }

    override fun onStart() {
        super.onStart()
        mediaPlayer?.start()

    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    private fun anadirCanciones(){

        for ((numCancion, x:String) in nombres.withIndex()){
            canciones.add(Cancion(imagenes[numCancion],artistas[numCancion],x,musicas[numCancion]))
        }
    }

    private fun siguiente(){
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer=null
        indice++
        if (indice==canciones.size){
            indice=0
            bindeado(indice)
        }else{
            bindeado(indice)
        }
        mediaPlayer=MediaPlayer.create(this,canciones[indice].getCancion())
        findViewById<SeekBar>(R.id.sb_barra_duracion).max=mediaPlayer!!.duration
        findViewById<TextView>(R.id.tv_tiempo_total).text=tiempos(mediaPlayer!!.duration)

        pausado=false
        cambiarIcono()

    }
    private fun retroceder(){
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer=null
        indice--
        if (indice<0) {
            indice = canciones.size-1
            bindeado(indice)
        }
        else{
            bindeado(indice)
        }
        mediaPlayer=MediaPlayer.create(this,canciones[indice].getCancion())
        findViewById<SeekBar>(R.id.sb_barra_duracion).max=mediaPlayer!!.duration
        findViewById<TextView>(R.id.tv_tiempo_total).text=tiempos(mediaPlayer!!.duration)

        pausado=false
        cambiarIcono()

    }
    private fun cambiarIcono(){

        pausado = if (!pausado){
            mediaPlayer?.start()
            binding.playPausa.setImageResource(R.drawable.ic_baseline_pause_24)
            true

        }else {
            mediaPlayer?.pause()
            binding.playPausa.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            false

        }
    }

    private fun tiempos(mili:Int):String
    {
        minutos=TimeUnit.MILLISECONDS.toMinutes(mili.toLong())
        val mins:String =if(minutos<10)
            "0$minutos"
        else
            "$minutos"
        segundos=TimeUnit.MILLISECONDS.toSeconds((mili-minutos*60*1000))
        val segs:String=if (segundos<10)
            "0$segundos"
        else
            "$segundos"
        return "$mins:$segs"
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
