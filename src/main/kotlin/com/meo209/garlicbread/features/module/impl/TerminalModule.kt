package com.meo209.garlicbread.features.module.impl

import com.github.meo209.keventbus.EventBus
import com.meo209.garlicbread.features.module.Module
import com.meo209.garlicbread.features.module.ModuleCategory
import org.lwjgl.glfw.GLFW

class TerminalModule : Module(name = "Terminal", ModuleCategory.OTHER) {

    override val settings: TerminalSettings
        get() = TerminalSettings()

    override fun registerHandlers(eventBus: EventBus) {
        registerToggleHandler(eventBus, settings.key, onEnable = {
            disable(eventBus)
        })
    }

    class TerminalSettings : Settings() {
        var key = GLFW.GLFW_KEY_RIGHT_SHIFT
    }

}