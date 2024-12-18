package com.meo209.garlicbread.command.impl

import com.mojang.brigadier.CommandDispatcher
import com.meo209.garlicbread.command.ICommand
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.text.Text

class ServerCommand : ICommand {

    override fun register(
        commandDispatcher: CommandDispatcher<FabricClientCommandSource>,
        commandRegistryAccess: CommandRegistryAccess
    ) {
        commandDispatcher.register(
            literal("server")
                .then(
                    literal("ip")
                        .executes { ctx ->
                            val server = MinecraftClient.getInstance().currentServerEntry
                            if (server != null) {
                                ctx.source.sendFeedback(Text.literal("Server: ${server.address}"))
                            } else {
                                ctx.source.sendError(Text.literal("Not connected to a server."))
                            }
                            1
                        }
                )
                .then(
                    literal("players")
                        .executes { ctx ->
                            val networkHandler = MinecraftClient.getInstance().networkHandler
                            if (networkHandler != null) {
                                val playerList = networkHandler.playerList
                                val currentPlayers = playerList.size

                                ctx.source.sendFeedback(Text.literal("Current: $currentPlayers"))
                            } else {
                                ctx.source.sendError(Text.literal("Not connected to a server."))
                            }
                            1
                        }
                )
        )
    }
}