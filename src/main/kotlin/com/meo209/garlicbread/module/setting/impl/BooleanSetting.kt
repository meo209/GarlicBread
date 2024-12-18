package com.meo209.garlicbread.module.setting.impl

import com.google.gson.annotations.Expose
import com.meo209.garlicbread.module.setting.Setting

data class BooleanSetting(override val default: Boolean, @Expose override var value: Boolean = default) :
    Setting<Boolean>(value, default)
