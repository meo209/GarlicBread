package com.meo209.garlicbread.command

import com.mojang.brigadier.CommandDispatcher
import com.meo209.garlicbread.command.impl.*
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.command.CommandRegistryAccess

object CommandRegistry {

    fun init() {
        ClientCommandRegistrationCallback.EVENT.register { commandDispatcher: CommandDispatcher<FabricClientCommandSource>, commandRegistryAccess: CommandRegistryAccess ->

            fun registerCommand(command: ICommand) =
                command.register(commandDispatcher, commandRegistryAccess)

            registerCommand(ModuleCommand())

        }
    }

}