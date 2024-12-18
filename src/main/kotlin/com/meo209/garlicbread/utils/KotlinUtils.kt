package com.meo209.garlicbread.utils

object KotlinUtils {

    fun isNumeric(str: String?): Boolean {
        if (str.isNullOrEmpty()) {
            return false
        }
        for (c in str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false
            }
        }
        return true
    }

}