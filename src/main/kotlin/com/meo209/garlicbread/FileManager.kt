package com.meo209.garlicbread

import net.fabricmc.loader.api.FabricLoader
import java.io.File

object FileManager {

    val ROOT_DIRECTORY = File(FabricLoader.getInstance().configDir.toFile(), "garlicbread")

    val MODULES_DIRECTORY = File(ROOT_DIRECTORY, "modules")

    fun init() {
        if (!ROOT_DIRECTORY.exists()) ROOT_DIRECTORY.mkdirs()
        if (!MODULES_DIRECTORY.exists()) MODULES_DIRECTORY.mkdirs()
    }

}