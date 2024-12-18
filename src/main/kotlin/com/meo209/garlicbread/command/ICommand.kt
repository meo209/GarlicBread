package com.meo209.garlicbread.command

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.command.CommandRegistryAccess
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback

interface ICommand {

    fun register(commandDispatcher: CommandDispatcher<FabricClientCommandSource>, commandRegistryAccess: CommandRegistryAccess)

}