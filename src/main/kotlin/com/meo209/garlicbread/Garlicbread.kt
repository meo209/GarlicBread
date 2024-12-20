package com.meo209.garlicbread

import com.github.meo209.keventbus.EventBus
import com.meo209.garlicbread.events.TickEvent
import com.meo209.garlicbread.features.module.ModuleSystem
import com.meo209.garlicbread.features.terminal.TerminalScreen
import com.meo209.garlicbread.utils.PerformanceMonitor
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.MinecraftClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Garlicbread : ClientModInitializer {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger("GarlicBread")

        val TERMINAL_SCREEN by lazy { TerminalScreen() }

        fun lateInit() {
            logger.info("Garlic Bread [Client]")

            FileManager.init()
            ModuleSystem.init()

            ClientTickEvents.END_CLIENT_TICK.register { _ ->
                PerformanceMonitor.updateFpsHistory()
            }

            DiscordRPC.init()
        }
    }

    object Version {
        const val STRING = "0.0.1+1.21.4"

        const val DEVELOPMENT = true
    }

    override fun onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register {
            EventBus.global().post(TickEvent())
        }
    }
}

val client = MinecraftClient.getInstance()