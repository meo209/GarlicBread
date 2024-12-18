package com.meo209.garlicbread.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import com.meo209.garlicbread.FileManager
import com.meo209.garlicbread.module.setting.Setting
import com.meo209.garlicbread.module.setting.impl.BooleanSetting
import com.meo209.garlicbread.module.setting.impl.IntSetting
import java.io.File

class Config private constructor(private val module: Module) {

    @Expose
    val settings = mutableMapOf<String, Setting<*>>()

    private val file = File(FileManager.MODULES_DIRECTORY, "${module.name}.json")

    init {
        if (!file.exists())
            file.createNewFile()
    }

    fun boolean(name: String, default: Boolean = false): Config =
        this.apply {
            settings[name] = BooleanSetting(default)
        }

    fun int(name: String, default: Int = 0): Config =
        this.apply {
            settings[name] = IntSetting(default)
        }

    companion object {
        fun createFor(module: Module): Config {
            return Config(module).apply {
                boolean("enabled", module.enabled)
                int("key", module.key)
            }
        }
    }

    private val gson
        get() =
            GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting().create()

    fun saveConfig() {
        file.writeText(gson.toJson(this))
    }

    fun loadConfig() {
        if (file.exists() && file.readText().isNotEmpty()) {
            val loadedConfig = gson.fromJson(file.readText(), Config::class.java)
            this.settings.clear()
            this.settings.putAll(loadedConfig.settings)
        }
    }

}