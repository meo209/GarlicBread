package com.meo209.garlicbread.event

interface EventListener<T: Event> {

    fun handle(event: T)

}