package com.meo209.garlicbread.module.setting

import com.google.gson.annotations.Expose

open class Setting<T>(@Expose open var value: T, open val default: T)