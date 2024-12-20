package com.meo209.garlicbread.features.module

import com.meo209.garlicbread.features.module.impl.DebugModule

object ModuleSystem {

    private val modules = mutableListOf<Module>()

    fun init() {
        modules.add(DebugModule())

        modules.forEach {
            it.init()
        }
    }

}