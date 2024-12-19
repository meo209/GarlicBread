package com.meo209.garlicbread.features.terminal.commands

import com.meo209.garlicbread.Garlicbread
import com.meo209.garlicbread.features.terminal.Terminal
import com.meo209.garlicbread.utils.PerformanceMonitor
import revxrsal.commands.annotation.Command
import kotlin.math.roundToInt

class HistoryCommand {

    @Command("history")
    fun history(actor: Terminal.TerminalActor) {
        actor.reply(Garlicbread.TERMINAL_SCREEN.history)
    }

}