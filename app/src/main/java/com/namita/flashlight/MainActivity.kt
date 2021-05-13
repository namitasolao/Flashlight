package com.namita.flashlight

import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible


class MainActivity : AppCompatActivity() {

    lateinit var cameraManager: CameraManager
    lateinit var cameraId: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myButton = findViewById<Switch>(R.id.switch1)

        myButton.setOnClickListener() {
            buttonFormat(myButton)
            buttonClicked(myButton)
            soundEffect()
        }
    }

    private fun buttonClicked(myButton: Switch) {

        val isFlashAvailable = applicationContext.packageManager
            .hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
        if (!isFlashAvailable) {
            flashNotAvail()
        } else {
            cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
            cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, myButton.isChecked)
        }
    }


    private fun flashNotAvail() {
        val alert = AlertDialog.Builder(this)
            .create()
        alert.setTitle("Oops!")
        alert.setMessage("Flash not available in this device...")
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _, _ -> finish() }
        alert.show()
    }


    private fun buttonFormat(myButton: Switch) {
        if (myButton.isChecked) {
            //myButton.setBackgroundColor(R.color.black)
            Toast.makeText(this, "Light is turned on", Toast.LENGTH_SHORT).show()
            findViewById<ImageView>(R.id.lightOn_image).isVisible = false
            findViewById<ImageView>(R.id.lightOff_image).isVisible = true


        } else {
            // myButton.setBackgroundColor(R.color.teal_700)
            Toast.makeText(this, "Light is turned off", Toast.LENGTH_SHORT).show()
            findViewById<ImageView>(R.id.lightOn_image).isVisible = true
            findViewById<ImageView>(R.id.lightOff_image).isVisible = false

        }
    }

    private fun soundEffect() {

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mp = MediaPlayer.create(applicationContext, alarmSound)
        mp.start()

    }
}
