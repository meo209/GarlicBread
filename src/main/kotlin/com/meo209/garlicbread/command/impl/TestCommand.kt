package com.meo209.garlicbread.command.impl

import com.mojang.brigadier.CommandDispatcher
import com.meo209.garlicbread.command.ICommand
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.text.Text

class TestCommand : ICommand {

    override fun register(
        commandDispatcher: CommandDispatcher<FabricClientCommandSource>,
        commandRegistryAccess: CommandRegistryAccess
    ) {
        commandDispatcher.register(
            literal("test")
            .executes { ctx ->
                ctx.source.sendFeedback(Text.literal("TEST"))



                1
            })
    }


}