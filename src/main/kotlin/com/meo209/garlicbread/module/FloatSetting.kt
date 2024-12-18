package com.meo209.garlicbread.module

data class FloatSetting(override val name: String, override var value: Float, @Transient val range: ClosedRange<Float>): Setting<Float>(name, value)
