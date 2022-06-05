package com.example.pfg

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import android.content.ComponentName
import com.example.pfg.Cronometro.CronoActivity
import com.example.pfg.Musica.MusicActivity
import com.example.pfg.Noticias.NoticiasActivity
import com.example.pfg.Rutinas.RutinasActivity
import java.lang.Exception


class Login : AppCompatActivity() {

    private val db = FirebaseDatabase.getInstance()
    private lateinit var referencia: DatabaseReference


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    private val GOOGLE_SING_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        //var botonGoogle = findViewById<Button>(R.id.botonGoogle)

        val bundle=intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        //setup(botonGoogle)

        //Guardado de datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()

        referencia = db.getReference("Musica")


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
    /*
    private fun setup(botongoogle:Button){
        title="Inicio"

        botongoogle.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this,googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SING_IN)

        }
    }
*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SING_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful){

                        val newIntent = Intent(this, RutinasActivity::class.java)
                        startActivity(newIntent)
                    }
                }
            }
        }
    }
}