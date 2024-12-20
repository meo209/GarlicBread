package com.meo209.garlicbread.features.module.impl

import com.meo209.garlicbread.features.module.Module

class DebugModule : Module(name = "Debug") {

    override val settingInstance: DebugSettings = DebugSettings()

    override fun init() {
        super.init()

        println(settingInstance.debugSetting)
    }

    class DebugSettings : Settings() {
        var debugSetting = true
    }
}