package com.meo209.garlicbread

import net.fabricmc.loader.api.FabricLoader
import java.io.File

object FileManager {

    val ROOT_DIRECTORY = File(FabricLoader.getInstance().configDir.toFile(), "garlicbread")
    val MODULE_DIRECTORY = File(ROOT_DIRECTORY, "modules")

    val HISTORY_FILE = File(ROOT_DIRECTORY, "terminal_history.txt")

    fun init() {
        if (!ROOT_DIRECTORY.exists()) ROOT_DIRECTORY.mkdirs()
        if (!MODULE_DIRECTORY.exists()) MODULE_DIRECTORY.mkdirs()

        if (!HISTORY_FILE.exists()) HISTORY_FILE.createNewFile()
    }

}