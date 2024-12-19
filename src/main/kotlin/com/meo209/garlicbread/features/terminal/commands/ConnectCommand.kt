package com.meo209.garlicbread.features.terminal.commands

import com.meo209.garlicbread.Garlicbread
import com.meo209.garlicbread.features.terminal.Terminal
import com.meo209.garlicbread.features.terminal.TerminalScreen
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen
import net.minecraft.client.network.ServerAddress
import net.minecraft.client.network.ServerInfo
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand

@Command("connect", "cnt")
class ConnectCommand {

    @Subcommand("<address>")
    fun connect(actor: Terminal.TerminalActor, address: String) {
        actor.reply("Connecting to $address...")

        val split = address.split(":")
        var port = 25565

        if (split.size == 2)
            port = split[1].toInt()

        val addr = ServerAddress(split[0], port)

        ConnectScreen.connect(
            Garlicbread.TERMINAL_SCREEN,
            MinecraftClient.getInstance(),
            addr,
            ServerInfo("Internal", address, ServerInfo.ServerType.OTHER),
            true,
            null
        )
    }

}