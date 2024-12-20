package com.meo209.garlicbread.features.terminal.commands

import com.meo209.garlicbread.features.terminal.Terminal
import io.netty.bootstrap.Bootstrap
import io.netty.channel.*
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.timeout.ReadTimeoutHandler
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ServerAddress
import net.minecraft.network.ClientConnection
import net.minecraft.network.NetworkSide
import net.minecraft.network.handler.PacketSizeLogger
import net.minecraft.network.packet.c2s.play.PlayerSessionC2SPacket
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket
import net.minecraft.util.math.BlockPos
import net.minecraft.util.profiler.MultiValueDebugSampleLogImpl
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand

class PlayerCommand {

    @Command("player <name>")
    fun player(actor: Terminal.TerminalActor, name: String) {
        val client = MinecraftClient.getInstance()
        val network = client.networkHandler!!
        val world = client.world!!
        val players = world.players

        val player = players.first { it.name.string == name }
        actor.reply("UUID: ${player.uuidAsString}")
        actor.reply("Position (if available): ${player.blockPos.toCenterPos()}")
        actor.reply("Alive: ${player.isAlive}")

        val networkPlayer = network.playerList.first { it.profile.name == name }
        actor.reply("Latency: ${networkPlayer.latency}")
        actor.reply("Profile: ${networkPlayer.profile.properties}")
        actor.reply("Gamemode: ${networkPlayer.gameMode}")
        actor.reply("Session ID: ${networkPlayer.session?.sessionId}")
        actor.reply("Key Expired: ${networkPlayer.session?.isKeyExpired}")
        actor.reply("Key Data: ${networkPlayer.session?.publicKeyData?.data}")
        actor.reply("Skin url: ${networkPlayer.skinTextures.textureUrl}")
    }

}