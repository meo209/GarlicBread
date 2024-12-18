package com.meo209.garlicbread.event.impl

import com.meo209.garlicbread.event.Event
import com.meo209.garlicbread.module.Module

class ModuleEnableEvent(val module: Module): Event
class ModuleDisableEvent(val module: Module): Event