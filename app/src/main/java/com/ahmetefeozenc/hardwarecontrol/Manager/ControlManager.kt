package com.ahmetefeozenc.hardwarecontrol.Manager

import android.app.Activity
import android.content.Context
import com.ahmetefeozenc.hardwarecontrol.Control.*
import com.ahmetefeozenc.hardwarecontrol.Interface.IHardwareController
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class ControlManager(private val context: Context,private val activity: Activity, private val binding: ActivityMainBinding) {
    val bluetoothControl: BluetoothControl = BluetoothControl(activity, binding)
    val wifiControl: WifiControl = WifiControl(activity, binding)
    val nfcControl: NfcControl = NfcControl(context, binding)
    val threeGControl: ThreeGControl = ThreeGControl(context, binding)
    val speakerControl: SpeakerControl = SpeakerControl(context, binding)
    val cameraControl: CameraControl = CameraControl(activity, binding)
    val flashControl: FlashControl = FlashControl(context, binding)
    val vibrationControl: VibrationControl = VibrationControl(context, binding)
    val doNotDisturbControl: DoNotDisturbControl = DoNotDisturbControl(context, binding)
    val microphoneControl: MicrophoneControl = MicrophoneControl(context, binding)
    val simControl: SimControl = SimControl(context, binding)
    val chargeControl: ChargeControl = ChargeControl(context, binding)
    val headphoneControl: HeadphoneControl = HeadphoneControl(context, binding)
    val proximityControl: ProximityControl = ProximityControl(activity, binding)



    fun initializeControls() {
        // Initialize any controls if necessary
    }

    fun startListening() {
        bluetoothControl.startListening()
        wifiControl.startListening()
        nfcControl.startListening()
        threeGControl.startListening()
        speakerControl.startListening()
        cameraControl.startListening()
        flashControl.startListening()
        vibrationControl.startListening()
        doNotDisturbControl.startListening()
        microphoneControl.startListening()
        simControl.startListening()
        chargeControl.startListening()
        headphoneControl.startListening()
        proximityControl.startListening()
    }

    fun stopListening() {
        bluetoothControl.stopListening()
        wifiControl.stopListening()
        nfcControl.stopListening()
        threeGControl.stopListening()
        speakerControl.stopListening()
        cameraControl.stopListening()
        flashControl.stopListening()
        vibrationControl.stopListening()
        doNotDisturbControl.stopListening()
        microphoneControl.stopListening()
        simControl.stopListening()
        chargeControl.stopListening()
        headphoneControl.stopListening()
        proximityControl.stopListening()
    }

    fun handlePermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        // Handle permission results here if needed
    }
}
