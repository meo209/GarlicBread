package com.meo209.garlicbread

import com.meo209.garlicbread.event.EventBus
import com.meo209.garlicbread.event.impl.ShutdownEvent
import com.meo209.garlicbread.features.terminal.TerminalScreen
import com.meo209.garlicbread.utils.PerformanceMonitor
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.MinecraftClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class Garlicbread : ClientModInitializer {

    private val logger: Logger = LoggerFactory.getLogger("GarlicBread")

    companion object {
        fun stop() {
            EventBus.fire(ShutdownEvent())
        }

        val TERMINAL_SCREEN by lazy { TerminalScreen() }
    }

    object Version {
        const val STRING = "0.0.1+1.21.4"

        const val DEVELOPMENT = true
    }

    override fun onInitializeClient() {
        logger.info("Garlic Bread [Client]")

        FileManager.init()

        ClientTickEvents.END_CLIENT_TICK.register { _ ->
            PerformanceMonitor.updateFpsHistory()
        }

        DiscordRPC.init()
    }
}

val client = MinecraftClient.getInstance()