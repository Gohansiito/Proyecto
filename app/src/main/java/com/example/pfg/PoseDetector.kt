package com.example.pfg

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import java.io.IOException

class PoseDetector:AppCompatActivity() {
    val options = PoseDetectorOptions.Builder()
        .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
        .build()
    val poseDetector = PoseDetection.getClient(options)

    fun a() {
        val image: InputImage
        try {
            image = InputImage.fromFilePath(this, Uri.parse("C:\\Users\\ivang\\Downloads\\a.jpeg"))
            var result:Task<Pose>
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

}