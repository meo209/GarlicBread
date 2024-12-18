package com.meo209.garlicbread.module.impl

import com.meo209.garlicbread.module.Config
import com.meo209.garlicbread.module.Module
import org.lwjgl.glfw.GLFW

class CrashModule : Module(
    name = "Crash",
    description = "Crashes servers",
    key = GLFW.GLFW_KEY_V
) {

    override val config: Config
        get() = Config.createFor(this)
            .boolean("testBoolean")

}