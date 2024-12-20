package com.meo209.garlicbread.events

import com.github.meo209.keventbus.Event
import com.meo209.garlicbread.features.module.Module

class ModuleEnableEvent(val module: Module): Event
class ModuleDisableEvent(val module: Module): Event