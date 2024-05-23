package com.ahmetefeozenc.hardwarecontrol.Control

import android.content.Context
import android.os.Vibrator
import android.media.AudioManager
import com.ahmetefeozenc.hardwarecontrol.TechnologyDataSetUpdate
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class VibrationControl(private val context: Context, private val binding: ActivityMainBinding) {
    private val technologyDataSetUpdate = TechnologyDataSetUpdate(binding)
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    fun startListening() {
        updateStatus()
    }

    fun stopListening() {
        // Titreşim kontrolü için sürekli dinleme gerekmeyebilir. Bu metod boş bırakılabilir veya gerekli dinleyici eklenebilir.
    }

    private fun isVibrationAvailable(): Boolean {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        return vibrator.hasVibrator()
    }

    private fun updateStatus() {
        val isVibrationAvailable = isVibrationAvailable()
        val currentMode = audioManager.ringerMode

        val status = when {
            currentMode == AudioManager.RINGER_MODE_VIBRATE -> "Açık"
            isVibrationAvailable -> "Kapalı"
            else -> "Bulunamadı"
        }

        technologyDataSetUpdate.updateStatus("Vibration", status)
    }
}
