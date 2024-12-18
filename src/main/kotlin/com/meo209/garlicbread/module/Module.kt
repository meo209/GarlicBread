package com.meo209.garlicbread.module

import com.meo209.garlicbread.event.EventBus
import com.meo209.garlicbread.event.impl.*

abstract class Module(val name: String, val description: String, var key: Int = -1, var enabled: Boolean = false) {

    abstract val config: Config

    fun toggle() {
        enabled = !enabled
        if (enabled)
            EventBus.fire(ModuleEnableEvent(this))
        else
            EventBus.fire(ModuleDisableEvent(this))
    }

}