package com.meo209.garlicbread.module

open class Setting<T>(
    @Transient open val name: String,
    @Transient open var value: T // Mark as transient to ignore during serialization
) {

    operator fun invoke(): T = value

}