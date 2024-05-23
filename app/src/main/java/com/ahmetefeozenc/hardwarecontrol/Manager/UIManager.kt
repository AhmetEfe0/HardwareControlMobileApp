package com.ahmetefeozenc.hardwarecontrol.Manager

import com.ahmetefeozenc.hardwarecontrol.Manager.ControlManager
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class UIManager(private val binding: ActivityMainBinding, private val controlManager: ControlManager) {
    fun initializeUI() {
        binding.bluetoothcard.setOnClickListener {
            if (!controlManager.bluetoothControl.isBluetoothEnabled()) {
                controlManager.bluetoothControl.openHardware()
            } else {
                controlManager.bluetoothControl.closeHardware()
            }
        }

        binding.nfccard.setOnClickListener {
            controlManager.nfcControl.openHardware()
        }

        binding.wificard.setOnClickListener {
            controlManager.wifiControl.openHardware()
        }

        binding.cameracard.setOnClickListener {
            controlManager.cameraControl.openHardware()
        }

        binding.flashcard.setOnClickListener {
            controlManager.flashControl.openHardware()
        }

        binding.speakercard.setOnClickListener {
            controlManager.speakerControl.openHardware()
        }

        binding.threegcard.setOnClickListener {
            controlManager.threeGControl.openHardware()
        }
    }
}
