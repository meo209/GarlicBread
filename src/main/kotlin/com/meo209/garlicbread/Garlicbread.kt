package com.meo209.garlicbread

import com.meo209.garlicbread.command.CommandRegistry
import com.meo209.garlicbread.event.Event
import com.meo209.garlicbread.event.EventBus
import com.meo209.garlicbread.event.EventListener
import com.meo209.garlicbread.event.impl.ModuleDisableEvent
import com.meo209.garlicbread.event.impl.ModuleEnableEvent
import com.meo209.garlicbread.event.impl.ShutdownEvent
import com.meo209.garlicbread.event.impl.TickEvent
import com.meo209.garlicbread.module.ModuleRegistry
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.client.input.Input
import net.minecraft.client.input.KeyboardInput
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.option.KeyBinding
import org.lwjgl.glfw.GLFW
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.math.cos
import kotlin.math.sin


class Garlicbread : ClientModInitializer {

    private val logger: Logger = LoggerFactory.getLogger("GarlicBread")

    val flightKey: KeyBinding = KeyBinding("toggleflying", GLFW.GLFW_KEY_F4, KeyBinding.MISC_CATEGORY)

    companion object {
        fun stop() {
            EventBus.fire(ShutdownEvent())
        }
    }

    override fun onInitializeClient() {
        logger.info("Garlic Bread [Client]")

        FileManager.init()

        CommandRegistry.init()

        ModuleRegistry.init()

        EventBus.register(ModuleEnableEvent::class, object : EventListener<ModuleEnableEvent> {
            override fun handle(event: ModuleEnableEvent) {
                logger.info("${event.module.javaClass.simpleName} was enabled.")
            }
        })

        EventBus.register(ModuleDisableEvent::class, object : EventListener<ModuleDisableEvent> {
            override fun handle(event: ModuleDisableEvent) {
                logger.info("${event.module.javaClass.simpleName} was disabled.")
            }
        })

        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
    }

    private var isFlying = false

    private fun onClientTick(client: MinecraftClient) {
        if (client.player == null) return

        val player: ClientPlayerEntity = client.player!!

        // Toggle flying mode with a key (e.g., F8)
        if (flightKey.isPressed) {
            isFlying = !isFlying
        }

        if (isFlying) {
            enableFlying(player)
        }

        EventBus.fire(TickEvent())
    }

    private fun enableFlying(player: ClientPlayerEntity) {
        val input: Input = player.input

        // Simulate flying movement
        val velocity = player.velocity

        if (input is KeyboardInput) {
            val keyboardInput = input

            if (keyboardInput.playerInput.jump) {
                player.velocity = velocity.add(0.0, 0.1, 0.0)
            }

            if (keyboardInput.playerInput.sneak) {
                player.velocity = velocity.subtract(0.0, 0.1, 0.0)
            }

            if (keyboardInput.movementForward != 0f || keyboardInput.movementSideways != 0f) {
                val forward = keyboardInput.movementForward.toDouble()
                val sideways = keyboardInput.movementSideways.toDouble()
                val yaw = Math.toRadians(player.yaw.toDouble())

                val dx = -sin(yaw) * forward + cos(yaw) * sideways
                val dz = cos(yaw) * forward + sin(yaw) * sideways

                player.velocity = velocity.add(dx * 0.1, 0.0, dz * 0.1)
            }
        }
    }
}
