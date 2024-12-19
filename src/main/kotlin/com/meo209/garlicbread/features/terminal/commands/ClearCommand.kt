package com.meo209.garlicbread.features.terminal.commands

import com.meo209.garlicbread.Garlicbread
import com.meo209.garlicbread.features.terminal.Terminal
import revxrsal.commands.annotation.Command

class ClearCommand {

    @Command("c", "clear")
    fun clear(actor: Terminal.TerminalActor) {
        Garlicbread.TERMINAL_SCREEN.clear(true)
    }

}