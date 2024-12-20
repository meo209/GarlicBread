package com.meo209.garlicbread.features.module

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.github.meo209.keventbus.EventBus
import com.meo209.garlicbread.FileManager
import com.meo209.garlicbread.events.ShutdownEvent
import com.meo209.garlicbread.utils.Initializable
import java.io.File

abstract class Module(val name: String, var enabled: Boolean = false) : Initializable {

    abstract val settingInstance: Settings
    private val settingFile = File(FileManager.MODULE_DIRECTORY, "$name.json")

    private val objectMapper: ObjectMapper = ObjectMapper().apply {
        val module = SimpleModule()
        module.addSerializer(Settings::class.java, SettingsSerializer())
        module.addDeserializer(Settings::class.java, SettingsDeserializer())
        this.registerModule(module)
    }

    override fun init() {
        if (settingFile.exists() && settingFile.readText().isNotEmpty()) {
            val loadedSettings = objectMapper.readValue(settingFile.readText(), Settings::class.java)

            settingInstance::class.java.declaredFields.forEach { field ->
                if (field.name == "INSTANCE") return@forEach // Skip companion object INSTANCE field

                val loadedField = loadedSettings::class.java.declaredFields.first { it.name == field.name }
                field.isAccessible = true
                loadedField.isAccessible = true
                field.set(settingInstance, loadedField.get(loadedSettings))
            }
        }

        EventBus.global().handler(ShutdownEvent::class) { _: ShutdownEvent ->
            println("Saving $name module")
            if (!settingFile.exists()) settingFile.createNewFile()

            settingFile.writeText(objectMapper.writeValueAsString(settingInstance))
        }
    }

    abstract class Settings
}