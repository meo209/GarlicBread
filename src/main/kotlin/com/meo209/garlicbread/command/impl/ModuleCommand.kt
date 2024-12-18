package com.meo209.garlicbread.command.impl

import com.meo209.garlicbread.command.ICommand
import com.meo209.garlicbread.module.FloatSetting
import com.meo209.garlicbread.module.Module
import com.meo209.garlicbread.module.ModuleRegistry
import com.meo209.garlicbread.module.Setting
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.FloatArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.text.Text

class ModuleCommand : ICommand {

    override fun register(
        commandDispatcher: CommandDispatcher<FabricClientCommandSource>,
        commandRegistryAccess: CommandRegistryAccess
    ) {
        commandDispatcher.register(
            LiteralArgumentBuilder.literal<FabricClientCommandSource>("module")
                .then(
                    LiteralArgumentBuilder.literal<FabricClientCommandSource>("toggle")
                        .then(
                            ClientCommandManager.argument("moduleName", StringArgumentType.word())
                                .executes { context ->
                                    toggleModule(context, StringArgumentType.getString(context, "moduleName"))
                                }
                        )
                )
                .then(
                    LiteralArgumentBuilder.literal<FabricClientCommandSource>("list")
                        .executes { context ->
                            listModules(context)
                        }
                )
                .then(
                    LiteralArgumentBuilder.literal<FabricClientCommandSource>("config")
                        .then(
                            ClientCommandManager.argument("moduleName", StringArgumentType.word())
                                .then(
                                    ClientCommandManager.argument("settingName", StringArgumentType.word())
                                        .then(
                                            ClientCommandManager.argument("value", FloatArgumentType.floatArg())
                                                .executes { context ->
                                                    setConfigValue(
                                                        context,
                                                        StringArgumentType.getString(context, "moduleName"),
                                                        StringArgumentType.getString(context, "settingName"),
                                                        FloatArgumentType.getFloat(context, "value")
                                                    )
                                                }
                                        )
                                )
                        )
                )
        )
    }

    private fun toggleModule(context: CommandContext<FabricClientCommandSource>, moduleName: String): Int {
        val module = ModuleRegistry.modules.find { it.name.equals(moduleName, ignoreCase = true) }
        return if (module != null) {
            module.toggle()
            context.source.sendFeedback(Text.of("Toggled module: ${module.name}"))
            1
        } else {
            context.source.sendError(Text.of("Module not found: $moduleName"))
            0
        }
    }

    private fun listModules(context: CommandContext<FabricClientCommandSource>): Int {
        val moduleList = ModuleRegistry.modules.joinToString(", ") { it.name }
        context.source.sendFeedback(Text.of("Available modules: $moduleList"))
        return 1
    }

    private fun setConfigValue(
        context: CommandContext<FabricClientCommandSource>,
        moduleName: String,
        settingName: String,
        value: Float
    ): Int {
        val module = ModuleRegistry.modules.find { it.name.equals(moduleName, ignoreCase = true) }
        return if (module != null) {
            val setting = module.settings.find { it.name.equals(settingName, ignoreCase = true) }
            if (setting != null) {
                if (setting is Setting<*>) {
                    when (setting) {
                        is FloatSetting -> {
                            if (value in setting.range) {
                                setting.value = value
                                context.source.sendFeedback(Text.of("Set ${module.name}.${setting.name} to $value"))
                                return 1
                            } else {
                                context.source.sendError(Text.of("Value $value is out of range for ${setting.name}"))
                                return 0
                            }
                        }

                        else -> {
                            context.source.sendError(Text.of("Setting ${setting.name} is not a FloatSetting"))
                            return 0
                        }
                    }
                } else {
                    context.source.sendError(Text.of("Setting ${setting.name} is not a valid Setting"))
                    return 0
                }
            } else {
                context.source.sendError(Text.of("Setting not found: $settingName"))
                return 0
            }
        } else {
            context.source.sendError(Text.of("Module not found: $moduleName"))
            return 0
        }
    }
}