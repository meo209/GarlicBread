package com.meo209.garlicbread.command

import com.meo209.garlicbread.command.impl.ChunkCommand
import com.meo209.garlicbread.command.impl.ConfigCommand
import com.meo209.garlicbread.command.impl.ServerCommand
import com.mojang.brigadier.CommandDispatcher
import com.meo209.garlicbread.command.impl.TestCommand
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.command.CommandRegistryAccess

object CommandRegistry {

    fun init() {
        ClientCommandRegistrationCallback.EVENT.register { commandDispatcher: CommandDispatcher<FabricClientCommandSource>, commandRegistryAccess: CommandRegistryAccess ->

            fun registerCommand(command: ICommand) =
                command.register(commandDispatcher, commandRegistryAccess)

            registerCommand(TestCommand())
            registerCommand(ServerCommand())
            registerCommand(ChunkCommand())
            registerCommand(ConfigCommand())
        }
    }

}