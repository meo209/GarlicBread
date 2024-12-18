package com.meo209.garlicbread.command.impl

import com.meo209.garlicbread.command.ICommand
import com.meo209.garlicbread.command.argument.SettingArgumentType
import com.meo209.garlicbread.command.bridgadier.ModuleArgumentType
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.command.CommandRegistryAccess


class ConfigCommand : ICommand {

    override fun register(
        commandDispatcher: CommandDispatcher<FabricClientCommandSource>,
        commandRegistryAccess: CommandRegistryAccess
    ) {
        commandDispatcher.register(
            literal("config")
                .then(
                    argument("module", ModuleArgumentType.module())
                        .then(
                            argument("setting", SettingArgumentType.setting())
                                .executes { ctx ->
                                    val module = ModuleArgumentType.getModule(ctx, "module")
                                    val setting = SettingArgumentType.getSetting(ctx, "setting")
                                    1
                                }
                        )
                )
        )
    }


}