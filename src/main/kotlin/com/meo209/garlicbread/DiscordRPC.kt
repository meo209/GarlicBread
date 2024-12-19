package com.meo209.garlicbread

import de.jcm.discordgamesdk.Core
import de.jcm.discordgamesdk.CreateParams
import de.jcm.discordgamesdk.activity.Activity
import de.jcm.discordgamesdk.activity.ActivityButton
import de.jcm.discordgamesdk.activity.ActivityButtonsMode
import java.time.Instant
import kotlin.concurrent.thread

object DiscordRPC {

    private lateinit var activity: Activity
    private lateinit var core: Core

    fun updateDetails(details: String, update: Boolean = true) {
        activity.details = details
        if (update)
            update()
    }

    fun updateState(state: String, update: Boolean = true) {
        activity.state = state
        if (update)
            update()
    }

    private fun update() {
        core.activityManager().updateActivity(activity)
    }

    // Launch a thread to avoid blocking the main thread.
    fun init() = thread {
        CreateParams().use { params ->
            params.clientID = 1319395876942250004L
            params.flags = CreateParams.getDefaultFlags()
            Core(params).use { core ->
                Activity().use { activity ->
                    this.activity = activity
                    this.core = core

                    // We update the activity later
                    updateDetails("GarlicBread", false)
                    updateState("DEV: ${Garlicbread.Version.DEVELOPMENT} [${Garlicbread.Version.STRING}]", false)

                    activity.timestamps().start = Instant.now()

                    activity.assets().largeImage = "icon"

                    activity.activityButtonsMode = ActivityButtonsMode.BUTTONS
                    activity.addButton(ActivityButton().apply {
                        label = "Github"; url = "https://github.com/meo209/GarlicBread"
                    })

                    // Update the activity
                    update()
                }
                while (true) {
                    core.runCallbacks()
                    try {
                        Thread.sleep(16)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

}