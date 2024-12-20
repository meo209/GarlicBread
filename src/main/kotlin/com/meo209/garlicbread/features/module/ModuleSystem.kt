package com.meo209.garlicbread.features.module

import com.meo209.garlicbread.features.module.impl.DebugModule
import com.meo209.garlicbread.features.module.impl.TerminalModule

object ModuleSystem {

    private val modules = mutableListOf<Module>()

    fun findModuleByName(name: String): Module =
        modules.first { it.name == name }

    fun init() {
        modules.add(DebugModule())
        modules.add(TerminalModule())

        modules.forEach {
            it.init()
        }
    }

}