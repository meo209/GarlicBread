package com.meo209.garlicbread.features.terminal.commands

import com.meo209.garlicbread.features.terminal.Terminal
import com.meo209.garlicbread.utils.PerformanceMonitor
import revxrsal.commands.annotation.Command
import kotlin.math.roundToInt

class StatisticsCommand {

    @Command("statistics", "stats")
    fun statistics(actor: Terminal.TerminalActor) {
        actor.reply("FPS: ${PerformanceMonitor.fps}")
        actor.reply("Avg. FPS (last 10 seconds): ${PerformanceMonitor.averageFps}")
        actor.reply("Memory Usage (MB): ${PerformanceMonitor.memoryUsageMB.roundToInt()}")
        actor.reply("Disk Usage (GB): ${PerformanceMonitor.diskUsageGB.roundToInt()}")
        actor.reply("Total Disk Space (GB): ${PerformanceMonitor.totalDiskSpaceGB.roundToInt()}")
        actor.reply("Game Boot Time (MS): ${PerformanceMonitor.bootTime}")
    }

}