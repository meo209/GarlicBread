package com.meo209.garlicbread.features.terminal

import com.meo209.garlicbread.FileManager
import com.meo209.garlicbread.Garlicbread
import com.meo209.garlicbread.utils.Colors
import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.ButtonComponent
import io.wispforest.owo.ui.component.TextAreaComponent
import io.wispforest.owo.ui.component.TextBoxComponent
import io.wispforest.owo.ui.container.FlowLayout
import net.minecraft.util.Identifier
import org.lwjgl.glfw.GLFW

class TerminalScreen : BaseUIModelScreen<FlowLayout>(
    FlowLayout::class.java,
    DataSource.asset(Identifier.of("garlicbread", "terminal_ui_model"))
) {

    val history: String
        get() = FileManager.HISTORY_FILE.readLines()
            .joinToString("\n") { "${Colors.LIGHT_PURPLE} > ${Colors.WHITE}$it" }

    private lateinit var inputField: TextBoxComponent
    private lateinit var historyField: TextAreaComponent

    fun log(message: String, isError: Boolean = false) {
        historyField.text(
            historyField.text + "\n${
                if (isError) "§4$message"
                else message
            }"
        )
    }

    fun clear(input: Boolean) {
        if (input)
            inputField.text("")
        historyField.text("")
    }

    private fun appendHistory(cmd: String) {
        FileManager.HISTORY_FILE.appendText("$cmd\n")
    }

    override fun build(rootComponent: FlowLayout) {
        inputField = rootComponent.childById(TextBoxComponent::class.java, "input")

        historyField = rootComponent.childById(TextAreaComponent::class.java, "history")
        historyField.text(history)

        rootComponent.childById(ButtonComponent::class.java, "run-button").onPress { dispatch() }
    }

    private fun dispatch() {
        if (inputField.text.isEmpty()) return
        appendHistory(inputField.text)
        try {
            log("${Colors.LIGHT_PURPLE} > ${inputField.text}")
            Terminal.dispatch(inputField.text)
        } catch (e: Exception) {
            log(e.toString(), true)
        }
        inputField.text("")
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == GLFW.GLFW_KEY_ENTER && inputField.text.isNotEmpty() && inputField.isFocused) {
            dispatch()
        }
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

}
