package com.ahmetefeozenc.hardwarecontrol.Control

import android.content.Context
import android.telephony.TelephonyManager
import com.ahmetefeozenc.hardwarecontrol.Interface.IHardwareController
import com.ahmetefeozenc.hardwarecontrol.TechnologyDataSetUpdate
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class SimControl(private val context: Context, private val binding: ActivityMainBinding):
    IHardwareController {
    private val technologyDataSetUpdate = TechnologyDataSetUpdate(binding)
    override fun openHardware() {
        TODO("Not yet implemented")
    }

    override fun closeHardware() {
        TODO("Not yet implemented")
    }

    override fun edittextHardware() {
        val isSimPresent = isSimPresent()
        if (isSimPresent) {
            technologyDataSetUpdate.updateStatus("SIM", "Takılı")
        } else {
            technologyDataSetUpdate.updateStatus("SIM", "Takılı Değil")
        }
    }

    override fun startListening() {
        edittextHardware()
    }

    override fun stopListening() {
        // SIM kart durumu kontrolü için sürekli dinleme gerekmeyebilir. Bu metod boş bırakılabilir.
    }

    private fun isSimPresent(): Boolean {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.simState != TelephonyManager.SIM_STATE_ABSENT
    }

}
