package com.meo209.garlicbread.features.module.impl

import com.meo209.garlicbread.features.module.Module
import com.meo209.garlicbread.features.module.ModuleCategory

class DebugModule : Module(name = "Debug", ModuleCategory.OTHER) {

    override val settings: DebugSettings
        get() = DebugSettings()

    override fun init() {
        super.init()

        println(settings.debugSetting)
    }

    class DebugSettings : Settings() {
        var debugSetting = true
    }
}