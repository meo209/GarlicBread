package com.meo209.garlicbread.utils

import net.minecraft.client.MinecraftClient
import kotlin.math.roundToInt

object PerformanceMonitor {

    private const val FPS_HISTORY_SECONDS = 10

    var bootTime = 0L

    private val fpsHistory = mutableListOf<Int>()
    private var lastFpsUpdateTime = System.currentTimeMillis()

    val fps: Int
        get() =
            MinecraftClient.getInstance().currentFps

    val averageFps: Int
        get() = if (fpsHistory.isEmpty()) fps else fpsHistory.average().roundToInt()

    val memoryUsageMB: Double
        get() {
            val runtime = Runtime.getRuntime()
            val usedMemory = runtime.totalMemory() - runtime.freeMemory()
            return usedMemory / (1024.0 * 1024.0)
        }

    val totalMemoryMB: Double
        get() = Runtime.getRuntime().maxMemory() / (1024.0 * 1024.0)

    val diskUsageGB: Double
        get() {
            val gameDir = MinecraftClient.getInstance().runDirectory
            val usedSpace = gameDir.totalSpace - gameDir.freeSpace
            return usedSpace / (1024.0 * 1024.0 * 1024.0)
        }

    val totalDiskSpaceGB: Double
        get() = MinecraftClient.getInstance().runDirectory.totalSpace / (1024.0 * 1024.0 * 1024.0)

    internal fun updateFpsHistory() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastFpsUpdateTime >= 1000) {
            fpsHistory.add(MinecraftClient.getInstance().currentFps)
            lastFpsUpdateTime = currentTime

            if (fpsHistory.size > FPS_HISTORY_SECONDS) {
                fpsHistory.removeFirst()
            }
        }
    }

}