package com.meo209.garlicbread.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.meo209.garlicbread.FileManager
import com.meo209.garlicbread.event.EventBus
import com.meo209.garlicbread.event.EventListener
import com.meo209.garlicbread.event.impl.*
import java.io.File

abstract class Module(val name: String, val description: String, var key: Int = -1) {

    val settings = mutableSetOf<Setting<*>>()
    private val file = File(FileManager.MODULES_DIRECTORY, "${name}.json")

    fun float(name: String, value: Float, range: ClosedRange<Float> = 0f..1f): FloatSetting {
        return FloatSetting(name, value, range).apply { settings += this }
    }

    private val gson: Gson
        get() = GsonBuilder().setPrettyPrinting().create()

    init {
        if (!file.exists())
            file.createNewFile()

        EventBus.register(ShutdownEvent::class, object : EventListener<ShutdownEvent> {
            override fun handle(event: ShutdownEvent) {
                file.writeText(gson.toJson(settings))
            }
        })
    }

    fun load() {
        val loadedSettings: MutableList<FloatSetting> = gson.fromJson(
            file.readText(),
            TypeToken.getParameterized(MutableList::class.java, FloatSetting::class.java).type
        )

        println(settings)
        println(loadedSettings)

        settings.forEach { setting ->
            if (setting is FloatSetting) {
                val loadedSetting = loadedSettings.find { it.javaClass == setting.javaClass }
                if (loadedSetting != null) {
                    setting.value = loadedSetting.value
                }
            }
        }
    }

    fun toggle() {
        enabled = !enabled
        if (enabled)
            EventBus.fire(ModuleEnableEvent(this))
        else
            EventBus.fire(ModuleDisableEvent(this))
    }

}