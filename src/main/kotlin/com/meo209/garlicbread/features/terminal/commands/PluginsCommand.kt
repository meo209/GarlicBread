package com.meo209.garlicbread.features.terminal.commands

import com.meo209.garlicbread.client
import com.meo209.garlicbread.features.terminal.Terminal
import com.meo209.garlicbread.utils.PerformanceMonitor
import net.minecraft.client.MinecraftClient
import revxrsal.commands.annotation.Command
import kotlin.math.roundToInt

class PluginsCommand {

    // Skidded from Liquidbounce
    private val knownAntiCheats = arrayOf(
        "nocheatplus",
        "grimac",
        "aac",
        "intave",
        "horizon",
        "vulcan",
        "Vulcan",
        "spartan",
        "kauri",
        "anticheatreloaded",
        "matrix",
        "themis",
        "negativity"
    )

    @Command("plugins", "plugin")
    fun plugins(actor: Terminal.TerminalActor) {
        if (client.networkHandler == null) {
            actor.error("Not connected to a server.")
            return
        }

        client.networkHandler!!.sendCommand("plugins")
        client.networkHandler!!.sendCommand("bukkit:plugins")

        actor.reply("Plugins printed in chat.")
    }

}