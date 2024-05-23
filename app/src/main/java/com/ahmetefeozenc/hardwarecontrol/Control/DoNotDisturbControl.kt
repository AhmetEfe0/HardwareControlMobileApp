package com.ahmetefeozenc.hardwarecontrol.Control

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat
import com.ahmetefeozenc.hardwarecontrol.TechnologyDataSetUpdate
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class DoNotDisturbControl(private val context: Context, private val binding: ActivityMainBinding) {
    private val technologyDataSetUpdate = TechnologyDataSetUpdate(binding)

    fun startListening() {
        updateStatus()
    }

    fun stopListening() {
        // Herhangi bir receiver kaydetmiyoruz, bu nedenle burada bir işlem yapmamıza gerek yok
    }

    private fun updateStatus() {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val isDoNotDisturbEnabled = notificationManager.currentInterruptionFilter != NotificationManager.INTERRUPTION_FILTER_ALL
        if (isDoNotDisturbEnabled) {
            technologyDataSetUpdate.updateStatus("DoNotDisturb", "Açık")
        } else {
            technologyDataSetUpdate.updateStatus("DoNotDisturb", "Kapalı")
        }
    }

    fun openDoNotDisturbSettings() {
        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
        context.startActivity(intent)
    }

    fun toggleDoNotDisturb() {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED) {
            val isDoNotDisturbEnabled = notificationManager.currentInterruptionFilter != NotificationManager.INTERRUPTION_FILTER_ALL
            if (isDoNotDisturbEnabled) {
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
            } else {
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
            }
            updateStatus()
        } else {
            openDoNotDisturbSettings()
        }
    }
}
