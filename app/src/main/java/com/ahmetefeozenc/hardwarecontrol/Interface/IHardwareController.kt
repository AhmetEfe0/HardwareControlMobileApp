package com.ahmetefeozenc.hardwarecontrol.Interface

interface IHardwareController {
    fun openHardware()
    fun closeHardware()
    fun edittextHardware()
    fun startListening()
    fun stopListening()
}