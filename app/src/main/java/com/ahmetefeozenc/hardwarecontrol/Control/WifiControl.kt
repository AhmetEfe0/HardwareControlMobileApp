package com.ahmetefeozenc.hardwarecontrol.Control

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import com.ahmetefeozenc.hardwarecontrol.TechnologyDataSetUpdate
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding
import android.provider.Settings
import com.ahmetefeozenc.hardwarecontrol.Interface.IHardwareController


class WifiControl(private val activity: Activity, private val binding: ActivityMainBinding):
    IHardwareController {
    private lateinit var wifiManager: WifiManager
    private var wifiStateReceiver: BroadcastReceiver? = null

    override fun openHardware() {
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        activity.startActivity(intent)
    }

    override fun closeHardware() {
        TODO("Not yet implemented")
    }

    override fun edittextHardware() {
        val isWifiEnabled = isWifiEnabled()
        val technologyDataSetUpdate = TechnologyDataSetUpdate(binding)
        if (isWifiEnabled) {
            technologyDataSetUpdate.updateStatus("Wifi", "Açık")

        } else {
            technologyDataSetUpdate.updateStatus("Wifi", "Kapalı")
        }
    }

    override fun startListening() {
        wifiManager = activity.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiStateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                edittextHardware()
            }
        }
        activity.registerReceiver(wifiStateReceiver, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))
    }

    override fun stopListening() {
        wifiStateReceiver?.let { activity.unregisterReceiver(it) }
    }

    fun isWifiEnabled(): Boolean {
        return wifiManager.isWifiEnabled
    }
}
