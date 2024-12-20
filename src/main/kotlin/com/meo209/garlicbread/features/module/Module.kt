package com.meo209.garlicbread.features.module

import com.github.meo209.keventbus.EventBus
import com.google.gson.GsonBuilder
import com.meo209.garlicbread.FileManager
import com.meo209.garlicbread.events.KeyPressEvent
import com.meo209.garlicbread.events.ModuleDisableEvent
import com.meo209.garlicbread.events.ModuleEnableEvent
import com.meo209.garlicbread.events.ShutdownEvent
import com.meo209.garlicbread.utils.Initializable
import java.io.File

abstract class Module(val name: String, val category: ModuleCategory) : Initializable {

    abstract val settings: Settings
    private val settingFile = File(FileManager.MODULE_DIRECTORY, "$name.json")

    private val gson = GsonBuilder()
        .registerTypeAdapter(Settings::class.java, SettingsTypeAdapter())
        .create()

    override fun init() {
        if (settingFile.exists() && settingFile.readText().isNotEmpty()) {
            val loadedSettings = gson.fromJson(settingFile.readText(), Settings::class.java)

            settings::class.java.declaredFields.forEach { field ->
                if (field.name == "INSTANCE") return@forEach // Skip companion object INSTANCE field

                val loadedField = loadedSettings::class.java.declaredFields.first { it.name == field.name }
                field.isAccessible = true
                loadedField.isAccessible = true
                field.set(settings, loadedField.get(loadedSettings))
            }
        }

        EventBus.global().handler(ShutdownEvent::class) { _: ShutdownEvent ->
            println("Saving $name module")
            if (!settingFile.exists()) settingFile.createNewFile()

            settingFile.writeText(gson.toJson(settings))
        }

        registerHandlers(EventBus.global())
    }

    open fun registerHandlers(eventBus: EventBus) {}

    fun enable(eventBus: EventBus) {
        settings.enabled = true
        eventBus.post(ModuleEnableEvent(this))
    }

    fun disable(eventBus: EventBus) {
        settings.enabled = false
        eventBus.post(ModuleEnableEvent(this))
    }

    fun registerToggleHandler(eventBus: EventBus, key: Int, onEnable: () -> Unit = {}, onDisable: () -> Unit = {}) {
        eventBus.handler(KeyPressEvent::class) { keyPressEvent ->
            if (keyPressEvent.key == key) {
                settings.enabled = !settings.enabled

                if (settings.enabled) {
                    onEnable()
                    eventBus.post(ModuleEnableEvent(this))
                } else {
                    onDisable()
                    eventBus.post(ModuleDisableEvent(this))
                }
            }
        }
    }

    abstract class Settings {
        /**
         * Used for polymorphic serialization
         */
        public val type = javaClass.name

        /**
         * Is enabled
         */
        var enabled: Boolean = false
    }
}