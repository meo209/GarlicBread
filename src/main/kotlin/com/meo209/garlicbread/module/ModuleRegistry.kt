package com.meo209.garlicbread.module

import com.meo209.garlicbread.module.impl.StrafeModule

object ModuleRegistry {

    val modules = mutableListOf<Module>()

    fun init() {
        register(StrafeModule())
    }

    private fun register(module: Module) {
        modules.add(module)
        module.load()
    }

    fun handleKey(key: Int) {
        modules.forEach { module: Module ->
            if (module.key == key)
                module.toggle()
        }
    }

}