package com.ahmetefeozenc.hardwarecontrol.Control

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.ahmetefeozenc.hardwarecontrol.Interface.IHardwareController
import com.ahmetefeozenc.hardwarecontrol.TechnologyDataSetUpdate
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class BluetoothControl(private val activity: Activity, private val binding: ActivityMainBinding):
    IHardwareController {
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private var bluetoothStateReceiver: BroadcastReceiver? = null
    private val REQUEST_ENABLE_BT = 1

    override fun startListening() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        bluetoothStateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                edittextHardware()
            }
        }
        activity.registerReceiver(bluetoothStateReceiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
        edittextHardware()
    }

    override fun stopListening() {
        bluetoothStateReceiver?.let { activity.unregisterReceiver(it) }
    }

    override fun openHardware() {
        bluetoothAdapter?.let {
            if (!it.isEnabled) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // Check Bluetooth permission here
                    return
                }
                val intentBtEnabled = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                activity.startActivityForResult(intentBtEnabled, REQUEST_ENABLE_BT)
            }
        }
    }

    override fun closeHardware() {
        bluetoothAdapter?.let {
            if (it.isEnabled) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // Check Bluetooth permission here
                    return
                }
                val intentBtEnabled = Intent("android.bluetooth.adapter.action.REQUEST_DISABLE")
                activity.startActivityForResult(intentBtEnabled,REQUEST_ENABLE_BT)
            }
        }
    }

    override fun edittextHardware() {
        val isBluetoothEnabled = isBluetoothEnabled()
        val technologyDataSetUpdate = TechnologyDataSetUpdate(binding)
        if (isBluetoothEnabled) {
            technologyDataSetUpdate.updateStatus("Bluetooth", "Açık")
        } else {
            technologyDataSetUpdate.updateStatus("Bluetooth", "Kapalı")
        }
    }

    fun isBluetoothEnabled(): Boolean {
        return bluetoothAdapter.isEnabled
    }

}
