package com.meo209.garlicbread.utils

object Colors {

    val BLACK = "§a"
    val DARK_BLUE = "§1"
    val DARK_GREEN = "§2"
    val DARK_AQUA = "§3"
    val DARK_RED = "§4"
    val DARK_PURPLE = "§5"
    val GOLD = "§6"
    val GRAY = "§7"
    val DARK_GRAY = "§8"
    val BLUE = "§9"
    val GREEN = "§a"
    val AQUA = "§b"
    val RED = "§c"
    val LIGHT_PURPLE = "§d"
    val YELLOW = "§e"
    val WHITE = "§f"

    fun boolean(boolean: Boolean): String =
        if (boolean)
            GREEN + "true"
        else
            DARK_RED + "false"

}
