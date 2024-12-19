package com.meo209.garlicbread.features.terminal

import com.meo209.garlicbread.Garlicbread
import com.meo209.garlicbread.features.terminal.commands.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import revxrsal.commands.Lamp
import revxrsal.commands.command.CommandActor
import java.util.*

object Terminal {

    private val lamp = Lamp.builder<TerminalActor>().build()

    init {
        lamp.register(ConnectCommand())
        lamp.register(PingCommand())
        lamp.register(GammaCommand())
        lamp.register(StatisticsCommand())
        lamp.register(PluginsCommand())
        lamp.register(ServerCommand())
        lamp.register(ClearCommand())
        lamp.register(HistoryCommand())
    }

    fun dispatch(input: String) {
        lamp.dispatch(TerminalActor(), input)
    }

    class TerminalActor: CommandActor {

        val logger: Logger = LoggerFactory.getLogger("Terminal")

        override fun name(): String = "Terminal"

        override fun uniqueId(): UUID = UUID.fromString(name())

        override fun sendRawMessage(message: String) {
            logger.info(message)
            Garlicbread.TERMINAL_SCREEN.log(message)
        }

        override fun sendRawError(message: String) {
            logger.error(message)
            Garlicbread.TERMINAL_SCREEN.log(message, true)
        }

        override fun lamp(): Lamp<*> = lamp

    }

}