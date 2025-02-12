package com.example.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(),SensorEventListener {

    var sensor: Sensor?=null
    var sensorManager: SensorManager?=null
    lateinit var compassImage:ImageView
    lateinit var rotationTV:TextView

    var currentDegree = 0f//to keep track of rotation of compass(degree)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)

        compassImage = findViewById(R.id.imageView)
        rotationTV = findViewById(R.id.textView)
    }


    override fun onSensorChanged(event: SensorEvent?) {
        //most imp function
        var degree = Math.round(event!!.values[0])
        rotationTV.text = degree.toString()+"degree"
        var rotationAnimation = RotateAnimation(currentDegree,(-degree).toFloat(),Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
        rotationAnimation.duration = 210
        rotationAnimation.fillAfter = true
        compassImage.startAnimation(rotationAnimation)
        currentDegree = (-degree).toFloat()
        rotationTV.text = "Heading: $degree degrees"

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
    //Register a listerner for the sensor
    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME)

    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }
}
