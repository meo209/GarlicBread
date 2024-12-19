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

@Command("gamma")
class GammaCommand {

    @Subcommand("<value>")
    fun connect(actor: Terminal.TerminalActor, value: Double) {
        MinecraftClient.getInstance().options.gamma.value = value
        actor.reply("Adjusted gamma to $value.")
    }

}