package com.meo209.garlicbread.module

import com.meo209.garlicbread.module.impl.CrashModule

object ModuleRegistry {

    val modules = mutableListOf<Module>()

    fun init() {
        register(CrashModule())
    }

    fun stop() {
        modules.forEach { module: Module ->
            module.config.saveConfig()
        }
    }

    private fun register(module: Module) {
        modules.add(module.apply {
            config.loadConfig()
        })
    }

    fun handleKey(key: Int) {
        modules.forEach { module: Module ->
            if (module.key == key)
                module.toggle()
        }
    }

}