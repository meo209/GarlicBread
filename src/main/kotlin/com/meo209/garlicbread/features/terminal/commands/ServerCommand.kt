package com.meo209.garlicbread.features.terminal.commands

import com.meo209.garlicbread.client
import com.meo209.garlicbread.features.terminal.Terminal
import com.meo209.garlicbread.utils.Colors
import com.meo209.garlicbread.utils.PerformanceMonitor
import net.minecraft.client.MinecraftClient
import revxrsal.commands.annotation.Command
import kotlin.math.roundToInt

class ServerCommand {

    @Command("server", "serverinfo")
    fun server(actor: Terminal.TerminalActor) {
        if (client.networkHandler == null) {
            actor.error("Not connected to a server.")
            return
        }

        val info = client.networkHandler!!.serverInfo!!

        actor.reply("Ping: ${info.ping}")
        actor.reply("Version: ${info.version.literalString} (${info.protocolVersion})")
        actor.reply("Status: ${info.status}")
        actor.reply("RPP: ${info.resourcePackPolicy.name}")
        actor.reply("Realm: ${Colors.boolean(info.isRealm)}")
        actor.reply("Players: ${client.networkHandler!!.playerList.size}")
    }

}