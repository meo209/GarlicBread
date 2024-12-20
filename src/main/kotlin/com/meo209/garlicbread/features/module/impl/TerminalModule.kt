package com.meo209.garlicbread.features.module.impl

import com.github.meo209.keventbus.EventBus
import com.meo209.garlicbread.Garlicbread
import com.meo209.garlicbread.events.KeyPressEvent
import com.meo209.garlicbread.features.module.Module
import net.minecraft.client.MinecraftClient
import org.lwjgl.glfw.GLFW

class TerminalModule : Module(name = "Terminal") {

    override val settingInstance: Settings
        get() = TerminalSettings()

    override fun init() {
        super.init()

        EventBus.global().handler(KeyPressEvent::class) { keyPressEvent ->
            if (keyPressEvent.key == GLFW.GLFW_KEY_RIGHT_SHIFT) {
                MinecraftClient.getInstance().setScreen(Garlicbread.TERMINAL_SCREEN)
            }
        }
    }

    class TerminalSettings : Settings()

}