package com.ahmetefeozenc.hardwarecontrol

import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class TechnologyDataSetUpdate(private val binding: ActivityMainBinding) {
    fun updateStatus(name: String, status: String) {
        when (name) {
            "Wifi" -> binding.wifidurumtext.text = status
            "Bluetooth" -> binding.bluetoothdurumtext.text = status
            "NFC" -> binding.nfcdurumtxt.text = status
            "3G" -> binding.threegdurumtext.text = status
            "Speaker" -> binding.speakerdurumtext.text = status
            "Camera" -> binding.cameradurumtext.text = status
            "Flash" -> binding.flashdurumtext.text = status
            //"Vibration" -> binding.titresimdurumtext.text = status
            //"DoNotDisturb" -> binding.rahatsizetmedurumtext.text = status
            "Microphone" -> binding.mikrofondurumtext.text = status
            "SIM" -> binding.simcarddurumtext.text = status
            "Charge" -> binding.chargedurumtext.text = status
            "Headphone" -> binding.kulaklikdurumtext.text = status
            "Proximity" -> binding.proximitydurumtext.text = status
        }
    }
}
