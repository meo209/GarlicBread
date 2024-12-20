package com.meo209.garlicbread.features.module

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException
import java.lang.reflect.Field

class SettingsSerializer : JsonSerializer<Module.Settings>() {

    @Throws(IOException::class)
    override fun serialize(
        src: Module.Settings,
        jsonGenerator: JsonGenerator,
        serializerProvider: SerializerProvider
    ) {
        jsonGenerator.writeStartObject(src)

        // Add the "type" field
        jsonGenerator.writeStringField("type", src.javaClass.simpleName)

        src.javaClass.declaredFields.forEach { field: Field ->
            field.isAccessible = true
            jsonGenerator.writeObjectField(field.name, field.get(src))
        }

        jsonGenerator.writeEndObject()
    }
}