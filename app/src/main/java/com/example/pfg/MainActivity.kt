package com.example.pfg

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import java.io.IOException

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()

    }
    /*
    private fun pose() {
        val options = PoseDetectorOptions.Builder()
            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
            .build()
        val poseDetector = PoseDetection.getClient(options)

            val image: InputImage
            try {
                image =
                    InputImage.fromFilePath(this, Uri.parse("C:\\Users\\ivang\\Downloads\\a.jpeg"))
                var result: Task<Pose>
                result = poseDetector.process(image)
                    .addOnSuccessListener { results ->
                        // Task completed successfully
                        // ...
                        val allPoseLandmarks = results.getAllPoseLandmarks()
                        if (allPoseLandmarks.isEmpty())
                            Toast.makeText(this, "AAAAAAAAAAAAAAAAAAAA", Toast.LENGTH_SHORT).show()


                    }
                    .addOnFailureListener { e ->
                        // Task failed with an exception
                        // ...
                    }
            } catch (e: IOException) {
                e.printStackTrace()
            }

    }

     */
    private fun setup(){
        title = "Autentificacion"

        var registro = findViewById<Button>(R.id.registroGoogle)
        var email = findViewById<EditText>(R.id.email)
        var contra = findViewById<EditText>(R.id.contra)

        registro.setOnClickListener {
            if (email.text.isNotEmpty() && contra.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(),
                    contra.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, "La cuenta se ha creado", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "La cuenta no se ha podido crear", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        var login = findViewById<Button>(R.id.loginGoogle)
        login.setOnClickListener {
            if (email.text.isNotEmpty() && contra.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(),
                    contra.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        val newIntent = Intent(this,Login::class.java)
                        startActivity(newIntent)
                    }
                }
            }
        }

    }
}