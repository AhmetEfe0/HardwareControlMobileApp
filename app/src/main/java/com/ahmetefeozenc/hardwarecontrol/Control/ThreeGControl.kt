package com.ahmetefeozenc.hardwarecontrol.Control

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.provider.Settings
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import com.ahmetefeozenc.hardwarecontrol.Interface.IHardwareController
import com.ahmetefeozenc.hardwarecontrol.TechnologyDataSetUpdate
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class ThreeGControl(private val context: Context, private val binding: ActivityMainBinding):
    IHardwareController {
    private val technologyDataSetUpdate = TechnologyDataSetUpdate(binding)
    private var telephonyManager: TelephonyManager? = null
    private var phoneStateListener: PhoneStateListener? = null
    override fun openHardware() {
        val intent = Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)
        context.startActivity(intent)
    }

    override fun closeHardware() {
        TODO("Not yet implemented")
    }

    override fun edittextHardware() {
        val is3GEnabled = is3GEnabled()
        val is45GEnabled = is45GEnabled()

        if (is45GEnabled) {
            technologyDataSetUpdate.updateStatus("3G", "Kullanımda (4.5G)")
        } else if (is3GEnabled) {
            technologyDataSetUpdate.updateStatus("3G", "Kullanımda")
        } else {
            technologyDataSetUpdate.updateStatus("3G", "Kullanımda Değil")
        }
    }

    override fun startListening() {
        telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        phoneStateListener = object : PhoneStateListener() {
            override fun onDataConnectionStateChanged(state: Int, networkType: Int) {
                super.onDataConnectionStateChanged(state, networkType)
                edittextHardware()
            }

            override fun onServiceStateChanged(serviceState: android.telephony.ServiceState?) {
                super.onServiceStateChanged(serviceState)
                edittextHardware()
            }

            override fun onSignalStrengthsChanged(signalStrength: android.telephony.SignalStrength?) {
                super.onSignalStrengthsChanged(signalStrength)
                edittextHardware()
            }
        }
        telephonyManager?.listen(phoneStateListener,
            PhoneStateListener.LISTEN_DATA_CONNECTION_STATE or
                    PhoneStateListener.LISTEN_SERVICE_STATE or
                    PhoneStateListener.LISTEN_SIGNAL_STRENGTHS)

        // Başlangıç durumu kontrol et
        edittextHardware()
    }

    override fun stopListening() {
        telephonyManager?.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE)
    }


    private fun is3GEnabled(): Boolean {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return false
        }

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo?.isConnected == true && networkInfo.type == ConnectivityManager.TYPE_MOBILE &&
                (telephonyManager?.networkType == TelephonyManager.NETWORK_TYPE_UMTS ||
                        telephonyManager?.networkType == TelephonyManager.NETWORK_TYPE_HSPA ||
                        telephonyManager?.networkType == TelephonyManager.NETWORK_TYPE_HSDPA ||
                        telephonyManager?.networkType == TelephonyManager.NETWORK_TYPE_HSUPA)
    }

    private fun is45GEnabled(): Boolean {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return false
        }

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo?.isConnected == true && networkInfo.type == ConnectivityManager.TYPE_MOBILE &&
                (telephonyManager?.networkType == TelephonyManager.NETWORK_TYPE_LTE ||
                        telephonyManager?.networkType == TelephonyManager.NETWORK_TYPE_NR)
    }
}
