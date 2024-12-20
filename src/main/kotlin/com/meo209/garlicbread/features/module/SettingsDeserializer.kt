package com.meo209.garlicbread.features.module

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import java.lang.reflect.Constructor
import java.lang.reflect.Field

class SettingsDeserializer : JsonDeserializer<Module.Settings>() {

    override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Module.Settings {
        val node: JsonNode = parser.readValueAsTree()
        val type = node.get("type").asText()!!

        val clazz = ModuleSystem.findModuleByName(type.removeSuffix("Settings")).settingInstance::class.java

        // Find the constructor that takes a JsonNode as a parameter
        val constructor: Constructor<out Module.Settings> = clazz.getDeclaredConstructor()
            ?: throw IllegalStateException("No default constructor found for class ${clazz.name}")

        val instance = constructor.newInstance()

        // Iterate over all fields in the class and set their values from the JSON node
        clazz.declaredFields.forEach { field: Field ->
            field.isAccessible = true
            val fieldName = field.name
            val fieldValueNode = node.get(fieldName)

            if (fieldValueNode != null) {
                val fieldValue = when (field.type) {
                    String::class.java -> fieldValueNode.asText()
                    Int::class.java -> fieldValueNode.asInt()
                    Boolean::class.java -> fieldValueNode.asBoolean()
                    // Add more types as needed
                    else -> throw IllegalArgumentException("Unsupported field type: ${field.type}")
                }
                field.set(instance, fieldValue)
            }
        }

        return instance
    }
}