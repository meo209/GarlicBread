package com.meo209.garlicbread.command.argument

import com.meo209.garlicbread.module.Module
import com.meo209.garlicbread.module.setting.Setting
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.concurrent.CompletableFuture

class SettingArgumentType : ArgumentType<Setting<*>> {

    override fun parse(reader: StringReader): Setting<*> {
        return 
    }

    override fun <S : Any> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        val module = context.getArgument("module", Module::class.java)
        // Suggest all keys (setting names) from the settings map
        module.config.settings.keys.forEach { settingName ->
            builder.suggest(settingName)
        }
        return builder.buildFuture()
    }

    companion object {
        fun setting(): SettingArgumentType {
            return SettingArgumentType()
        }

        fun getSetting(context: CommandContext<*>, name: String): Setting<*> {
            return context.getArgument(name, Setting::class.java)
        }
    }
}