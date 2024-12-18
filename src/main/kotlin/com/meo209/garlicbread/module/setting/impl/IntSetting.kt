package com.meo209.garlicbread.module.setting.impl

import com.google.gson.annotations.Expose
import com.meo209.garlicbread.module.setting.Setting

data class IntSetting(override val default: Int, @Expose override var value: Int = default) :
    Setting<Int>(value, default)
