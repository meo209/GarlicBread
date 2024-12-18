package com.meo209.garlicbread.event

import java.util.HashMap
import kotlin.reflect.KClass

object EventBus {

    private val listeners: MutableMap<KClass<out Event>, MutableList<EventListener<*>>> = HashMap()

    fun <T : Event> register(eventClass: KClass<T>, listener: EventListener<T>) {
        val eventListeners = listeners.getOrPut(eventClass) { ArrayList() }
        eventListeners.add(listener as EventListener<*>)
    }

    fun <T : Event> fire(event: T) {
        listeners[event::class]?.forEach { listener ->
            @Suppress("UNCHECKED_CAST")
            (listener as EventListener<T>).handle(event)
        }
    }
}