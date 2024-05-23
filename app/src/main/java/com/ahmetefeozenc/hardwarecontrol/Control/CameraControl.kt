package com.ahmetefeozenc.hardwarecontrol.Control

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.provider.MediaStore
import com.ahmetefeozenc.hardwarecontrol.Interface.IHardwareController
import com.ahmetefeozenc.hardwarecontrol.TechnologyDataSetUpdate
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class CameraControl(private val activity: Activity, private val binding: ActivityMainBinding):
    IHardwareController {
    private val technologyDataSetUpdate = TechnologyDataSetUpdate(binding)
    private val cameraManager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    override fun openHardware() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(cameraIntent, 1337)
    }

    override fun closeHardware() {
        TODO("Not yet implemented")
    }

    override fun edittextHardware() {
        if (isCameraAvailable()) {
            technologyDataSetUpdate.updateStatus("Camera", "Mevcut")
        } else {
            technologyDataSetUpdate.updateStatus("Camera", "Bulunamadı")
        }
    }

    override fun startListening() {
        isCameraAvailable()
        edittextHardware()
    }

    override fun stopListening() {
        // Kamera kontrolü için sürekli dinleme gerekmeyebilir. Bu metod boş bırakılabilir veya gerekli dinleyici eklenebilir.
    }

    private fun isCameraAvailable():Boolean {
        try {
            val cameraIdList = cameraManager.cameraIdList
            return cameraIdList.isNotEmpty()
        } catch (e: CameraAccessException) {
            e.printStackTrace()
            return false
        }
    }

}
