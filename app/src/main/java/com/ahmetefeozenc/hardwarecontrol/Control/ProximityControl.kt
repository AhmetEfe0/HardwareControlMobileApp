package com.ahmetefeozenc.hardwarecontrol.Control

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.ahmetefeozenc.hardwarecontrol.TechnologyDataSetUpdate
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class ProximityControl(private val activity: Activity, private val binding: ActivityMainBinding) : SensorEventListener {
    private val technologyDataSetUpdate = TechnologyDataSetUpdate(binding)
    private val sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val proximitySensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

    fun startListening() {
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
            updateStatus("Mevcut")
        } else {
            updateStatus("Bulunamadı")
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    private fun updateStatus(status: String) {
        technologyDataSetUpdate.updateStatus("Proximity", status)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            val distance = event.values[0]
            technologyDataSetUpdate.updateStatus("Proximity", "Mesafe: $distance cm")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Gerektiğinde kullanılabilir.
    }
}
