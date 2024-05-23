package com.ahmetefeozenc.hardwarecontrol.Control

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import com.ahmetefeozenc.hardwarecontrol.Interface.IHardwareController
import com.ahmetefeozenc.hardwarecontrol.TechnologyDataSetUpdate
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class FlashControl(private val context: Context, private val binding: ActivityMainBinding):
    IHardwareController {
    private val technologyDataSetUpdate = TechnologyDataSetUpdate(binding)

    override fun openHardware() {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
        if (!isFlashAvailable()) {
            try {
                cameraManager.setTorchMode(cameraId, true)
            } catch (e: CameraAccessException) {
            }
        } else {
            try {
                cameraManager.setTorchMode(cameraId, false)
            } catch (e: CameraAccessException) {
            }
        }
    }

    override fun closeHardware() {
        TODO("Not yet implemented")
    }

    override fun edittextHardware() {
        if (isFlashOn()) {
            technologyDataSetUpdate.updateStatus("Flash", "Mevcut")
        } else {
            technologyDataSetUpdate.updateStatus("Flash", "Bulunamadı")
        }
    }

    override fun startListening() {
        if (isFlashAvailable()) {
            edittextHardware()
        } else {
            technologyDataSetUpdate.updateStatus("Flash", "Bulunamadı")
        }
    }

    override fun stopListening() {
        // Flaş kontrolü için sürekli dinleme gerekmeyebilir. Bu metod boş bırakılabilir veya gerekli dinleyici eklenebilir.
    }

    private fun isFlashAvailable(): Boolean {
        val packageManager = context.packageManager
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    private fun isFlashOn(): Boolean {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            for (cameraId in cameraManager.cameraIdList) {
                val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
                val flashAvailable = cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) ?: false
                if (flashAvailable) {
                    // Not: Android API'de doğrudan flaşın açık olup olmadığını öğrenmek için bir yöntem bulunmamaktadır. Flaşın durumunu kontrol etmek için
                    // kamera özelliklerine bakılır. Burada, yalnızca cihazda flaş olup olmadığı kontrol ediliyor.
                    return true
                }
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
        return false
    }


}
