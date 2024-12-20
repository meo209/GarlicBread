package com.meo209.garlicbread.features.terminal.commands

import com.meo209.garlicbread.features.terminal.Terminal
import com.meo209.garlicbread.utils.PerformanceMonitor
import net.minecraft.client.MinecraftClient
import net.minecraft.network.packet.c2s.play.PlayerLoadedC2SPacket
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket
import net.minecraft.util.math.BlockPos
import revxrsal.commands.annotation.Command
import kotlin.math.roundToInt

class TestCommand {

    @Command("test")
    fun statistics(actor: Terminal.TerminalActor) {
        val client = MinecraftClient.getInstance()
        val network = client.networkHandler!!
        val world = client.world!!
        val players = world.players

        actor.reply("LOL0")

        repeat(10000) {
            network.sendPacket(PlayerLoadedC2SPacket())
        }

        actor.reply("LOL1")
    }

}