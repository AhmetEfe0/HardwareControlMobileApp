package com.ahmetefeozenc.hardwarecontrol.Control

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import com.ahmetefeozenc.hardwarecontrol.Interface.IHardwareController
import com.ahmetefeozenc.hardwarecontrol.TechnologyDataSetUpdate
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class HeadphoneControl(private val context: Context, private val binding: ActivityMainBinding):
    IHardwareController {
    private val technologyDataSetUpdate = TechnologyDataSetUpdate(binding)
    private var headphoneStateReceiver: BroadcastReceiver? = null
    override fun openHardware() {
        TODO("Not yet implemented")
    }

    override fun closeHardware() {
        TODO("Not yet implemented")
    }

    override fun edittextHardware() {
        if(isHeadphoneOn()){
            technologyDataSetUpdate.updateStatus("Headphone", "Bağlı")
        }else{
            technologyDataSetUpdate.updateStatus("Headphone", "Bağlı Değil")
        }
    }

    override fun startListening() {
        headphoneStateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == Intent.ACTION_HEADSET_PLUG) {
                    edittextHardware()
                }
            }
        }
        val filter = IntentFilter(Intent.ACTION_HEADSET_PLUG)
        context.registerReceiver(headphoneStateReceiver, filter)
        // Mevcut durumu kontrol et
        edittextHardware()
    }

    override fun stopListening() {
        headphoneStateReceiver?.let { context.unregisterReceiver(it) }
    }

    private fun isHeadphoneOn():Boolean {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return audioManager.isWiredHeadsetOn
    }


}
