package com.ahmetefeozenc.hardwarecontrol

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahmetefeozenc.hardwarecontrol.Manager.ControlManager
import com.ahmetefeozenc.hardwarecontrol.Manager.PermissionManager
import com.ahmetefeozenc.hardwarecontrol.Manager.UIManager
import com.ahmetefeozenc.hardwarecontrol.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var controlManager: ControlManager
    private lateinit var permissionManager: PermissionManager
    private lateinit var uiManager: UIManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        controlManager = ControlManager(this,this, binding)
        permissionManager = PermissionManager(this)
        uiManager = UIManager(binding, controlManager)

        controlManager.initializeControls()
        uiManager.initializeUI()

        permissionManager.checkAndRequestPermissions()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionManager.handlePermissionsResult(requestCode, grantResults)) {
            controlManager.startListening()
        } else {
        }
    }

    override fun onResume() {
        super.onResume()
        controlManager.startListening()
    }

    override fun onPause() {
        super.onPause()
        controlManager.stopListening()
    }
}
