package com.example.pfg.Cronometro

import android.content.*
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.pfg.Musica.MusicActivity
import com.example.pfg.Noticias.NoticiasActivity
import com.example.pfg.R
import com.example.pfg.Rutinas.RutinasActivity
import com.example.pfg.databinding.CronometroBinding
import com.google.android.material.navigation.NavigationView
import java.lang.Exception
import kotlin.math.roundToInt

class CronoActivity: AppCompatActivity() {
    private lateinit var binding: CronometroBinding
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cronometro)
        binding = CronometroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startStopButton.setOnClickListener { startStopTimer() }
        binding.resetButton.setOnClickListener { resetTimer() }

        serviceIntent = Intent(applicationContext, ServicioCrono::class.java)
        registerReceiver(updateTime, IntentFilter(ServicioCrono.TIMER_UPDATED))


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

    private fun resetTimer()
    {
        stopTimer()
        time = 0.0
        binding.timeTV.text = convertirTiempo(time)
    }

    private fun startStopTimer()
    {
        if(timerStarted)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer()
    {
        serviceIntent.putExtra(ServicioCrono.TIME_EXTRA, time)
        startService(serviceIntent)
        binding.startStopButton.text = "Stop"
        binding.startStopButton.icon = getDrawable(R.drawable.ic_baseline_pause_24)
        timerStarted = true
    }

    private fun stopTimer()
    {
        stopService(serviceIntent)
        binding.startStopButton.text = "Start"
        binding.startStopButton.icon = getDrawable(R.drawable.ic_baseline_play_arrow_24)
        timerStarted = false
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context, intent: Intent)
        {
            time = intent.getDoubleExtra(ServicioCrono.TIME_EXTRA, 0.0)
            binding.timeTV.text = convertirTiempo(time)
        }
    }

    private fun convertirTiempo(time: Double): String
    {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)
}
