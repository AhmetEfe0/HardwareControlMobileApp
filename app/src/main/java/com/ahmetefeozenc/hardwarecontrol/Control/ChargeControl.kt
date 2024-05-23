package com.ahmetefeozenc.hardwarecontrol.Control

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.ahmetefeozenc.hardwarecontrol.Interface.IHardwareController
import com.ahmetefeozenc.hardwarecontrol.TechnologyDataSetUpdate
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class ChargeControl(private val context: Context, private val binding: ActivityMainBinding):
    IHardwareController {
    private val technologyDataSetUpdate = TechnologyDataSetUpdate(binding)
    private var chargeStateReceiver: BroadcastReceiver? = null
    private var isCharging = false

    override fun openHardware() {
        TODO("Not yet implemented")
    }

    override fun closeHardware() {
        TODO("Not yet implemented")
    }

    override fun edittextHardware() {
        if (isCharging) {
            technologyDataSetUpdate.updateStatus("Charge", "Bağlı")
        } else {
            technologyDataSetUpdate.updateStatus("Charge", "Bağlı Değil")
        }
    }

    override fun startListening() {
        chargeStateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
                edittextHardware()
            }
        }
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(chargeStateReceiver, filter)
    }

    override fun stopListening() {
        chargeStateReceiver?.let { context.unregisterReceiver(it) }
    }
}
