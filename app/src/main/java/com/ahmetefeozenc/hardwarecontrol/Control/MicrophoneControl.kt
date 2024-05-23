package com.ahmetefeozenc.hardwarecontrol.Control

import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import androidx.core.content.ContextCompat
import com.ahmetefeozenc.hardwarecontrol.TechnologyDataSetUpdate
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding
import android.media.AudioRecord
import com.ahmetefeozenc.hardwarecontrol.Interface.IHardwareController

class MicrophoneControl(private val context: Context, private val binding: ActivityMainBinding):
    IHardwareController {
    private val technologyDataSetUpdate = TechnologyDataSetUpdate(binding)
    override fun openHardware() {
        TODO("Not yet implemented")
    }

    override fun closeHardware() {
        TODO("Not yet implemented")
    }

    override fun edittextHardware() {
        val isMicrophoneInUse = isMicrophoneInUse()
        if (isMicrophoneInUse) {
            technologyDataSetUpdate.updateStatus("Microphone", "Kullanımda")
        } else {
            technologyDataSetUpdate.updateStatus("Microphone", "Kullanılmıyor")
        }
    }

    override fun startListening() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            edittextHardware()
        } else {
            technologyDataSetUpdate.updateStatus("Microphone", "İzin Gerekli")
        }
    }

    override fun stopListening() {
        // Mikrofon kontrolü için sürekli dinleme gerekmeyebilir. Bu metod boş bırakılabilir.
    }

    private fun isMicrophoneInUse(): Boolean {
        val minBufferSize = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)
        return minBufferSize != AudioRecord.ERROR_BAD_VALUE && minBufferSize != AudioRecord.ERROR
    }
}