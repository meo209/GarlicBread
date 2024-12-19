package com.meo209.garlicbread.features.terminal.commands

import com.meo209.garlicbread.features.terminal.Terminal
import com.meo209.garlicbread.utils.Colors
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand
import java.net.InetAddress
import kotlin.concurrent.thread

@Command("ping")
class PingCommand {

    @Subcommand("<address>")
    fun ping(actor: Terminal.TerminalActor, address: String) {
        ping(actor, address, 5)
    }

    @Subcommand("<address> <timeout>")
    fun ping(actor: Terminal.TerminalActor, address: String, timeout: Int) {
        thread {
            actor.reply("Pinging $address...")

            actor.reply("Is reachable: ${Colors.boolean(InetAddress.getByName(address).isReachable(timeout * 1000))}")
        }
    }

}