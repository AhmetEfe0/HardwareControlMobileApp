package com.ahmetefeozenc.hardwarecontrol.Control

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.provider.Settings
import com.ahmetefeozenc.hardwarecontrol.Interface.IHardwareController
import com.ahmetefeozenc.hardwarecontrol.TechnologyDataSetUpdate
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class SpeakerControl(private val context: Context, private val binding: ActivityMainBinding):
    IHardwareController {
    private val technologyDataSetUpdate = TechnologyDataSetUpdate(binding)
    override fun openHardware() {
        val intent = Intent(Settings.ACTION_SOUND_SETTINGS)
        context.startActivity(intent)
    }

    override fun closeHardware() {
        TODO("Not yet implemented")
    }

    override fun edittextHardware() {
        if (isSpeakerOn()) {
            technologyDataSetUpdate.updateStatus("Speaker", "Açık")
        } else {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val ringerMode = audioManager.ringerMode
            val status = when (ringerMode) {
                AudioManager.RINGER_MODE_NORMAL -> "Hoparlör"
                AudioManager.RINGER_MODE_VIBRATE -> "Titreşim"
                AudioManager.RINGER_MODE_SILENT -> "Sessiz"
                else -> "Kapalı"
            }
            technologyDataSetUpdate.updateStatus("Speaker", status)
        }
    }

    override fun startListening() {
        edittextHardware()
    }

    override fun stopListening() {
        // Hoparlör kontrolü için sürekli dinleme gerekmeyebilir. Bu metod boş bırakılabilir veya gerekli dinleyici eklenebilir.
    }

    private fun isSpeakerOn(): Boolean {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return audioManager.isSpeakerphoneOn
    }

}
