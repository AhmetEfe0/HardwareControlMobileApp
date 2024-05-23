package com.ahmetefeozenc.hardwarecontrol.Control

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import com.ahmetefeozenc.hardwarecontrol.TechnologyDataSetUpdate
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding
import android.provider.Settings
import com.ahmetefeozenc.hardwarecontrol.Interface.IHardwareController

class NfcControl(private val context: Context, private val binding: ActivityMainBinding):
    IHardwareController {
    private var nfcAdapter: NfcAdapter? = null
    private var nfcStateReceiver: BroadcastReceiver? = null
    val technologyDataSetUpdate = TechnologyDataSetUpdate(binding)
    override fun openHardware() {
        val intent = Intent(Settings.ACTION_NFC_SETTINGS)
        context.startActivity(intent)
    }

    override fun closeHardware() {
        TODO("Not yet implemented")
    }

    override fun edittextHardware() {
        val isNfcEnabled = isNfcEnabled()
        if (isNfcEnabled) {
            technologyDataSetUpdate.updateStatus("NFC", "Açık")
        } else {
            technologyDataSetUpdate.updateStatus("NFC", "Kapalı")
        }
    }

    override fun startListening() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        if (nfcAdapter == null) {
            technologyDataSetUpdate.updateStatus("NFC", "Bulunamadı")
            return
        }

        // NFC özelliği bulunuyor, dinleyiciyi başlat
        nfcStateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                edittextHardware()
            }
        }
        val filter = IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED)
        context.registerReceiver(nfcStateReceiver, filter)
        edittextHardware()
    }

    override fun stopListening() {
        nfcStateReceiver?.let { context.unregisterReceiver(it) }
    }

    private fun isNfcEnabled(): Boolean {
        return nfcAdapter?.isEnabled ?: false
    }


}
