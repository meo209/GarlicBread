package com.meo209.garlicbread.command.bridgadier

import com.meo209.garlicbread.module.Module
import com.meo209.garlicbread.module.ModuleRegistry
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.concurrent.CompletableFuture

class ModuleArgumentType : ArgumentType<Module> {

    override fun parse(reader: StringReader): Module {
        val moduleName = reader.readUnquotedString()
        return ModuleRegistry.modules.find { it.name.equals(moduleName, ignoreCase = true) }
            ?: throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create()
    }

    override fun <S : Any> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        ModuleRegistry.modules.forEach { module ->
            builder.suggest(module.name)
        }
        return builder.buildFuture()
    }

    companion object {
        fun module(): ModuleArgumentType {
            return ModuleArgumentType()
        }

        fun getModule(context: CommandContext<*>, name: String): Module {
            return context.getArgument(name, Module::class.java)
        }
    }
}