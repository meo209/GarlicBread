package com.meo209.garlicbread.command.impl

import com.meo209.garlicbread.command.ICommand
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos


class ChunkCommand : ICommand {

    override fun register(
        commandDispatcher: CommandDispatcher<FabricClientCommandSource>,
        commandRegistryAccess: CommandRegistryAccess
    ) {
        commandDispatcher.register(
            literal("chunk")
            .executes { ctx ->
                val client = MinecraftClient.getInstance()
                val playerPos: BlockPos = client.player!!.blockPos
                val radius = 10

                for (x in 0 until radius) {
                    for (z in 0 until radius) {
                        val chunkPos = ChunkPos(playerPos.add(x * 16, 0, z * 16))
                        client.world!!.getChunk(chunkPos.x, chunkPos.z)
                    }
                }

                ctx.source.sendFeedback(Text.literal("Loaded."))
                1
            })
    }


}