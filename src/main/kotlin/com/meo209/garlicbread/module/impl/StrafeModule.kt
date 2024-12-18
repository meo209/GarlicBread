package com.meo209.garlicbread.module.impl

import com.meo209.garlicbread.event.EventBus
import com.meo209.garlicbread.event.EventListener
import com.meo209.garlicbread.event.impl.TickEvent
import com.meo209.garlicbread.module.Module
import net.minecraft.client.MinecraftClient
import org.lwjgl.glfw.GLFW
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class StrafeModule : Module(
    name = "Strafe",
    description = "Increases movement speed and removes part of movement physics",
    key = GLFW.GLFW_KEY_V
) {

    val speed = float("speed", 1f)

    init {
        EventBus.register(TickEvent::class, object : EventListener<TickEvent> {
            override fun handle(event: TickEvent) {
                if (this@StrafeModule.enabled) {
                    val client = MinecraftClient.getInstance()
                    if (client.player!!.input.movementForward > 0f) {
                        val yawRadians = Math.toRadians(client.player!!.yaw.toDouble())
                        val x = -sin(yawRadians)
                        val z = cos(yawRadians)

                        val magnitude = sqrt(x * x + z * z)
                        val normalizedX = x / magnitude
                        val normalizedZ = z / magnitude

                        val velocityX = normalizedX * speed()
                        val velocityZ = normalizedZ * speed()

                        MinecraftClient.getInstance()
                            .player!!
                            .setVelocity(
                                velocityX,
                                client.player!!.velocity.y,
                                velocityZ
                            )
                    }
                }
            }
        })
    }

}