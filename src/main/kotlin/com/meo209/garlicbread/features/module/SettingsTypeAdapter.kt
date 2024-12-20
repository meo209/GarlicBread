package com.meo209.garlicbread.features.module

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class SettingsTypeAdapter : JsonDeserializer<Module.Settings?> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        type: Type,
        jsonDeserializationContext: JsonDeserializationContext
    ): Module.Settings? {
        val jsonObject: JsonObject = json.asJsonObject
        val typeName: String = jsonObject.get("type").asString

        try {
            val cls: Class<out Module.Settings?> = Class.forName(typeName) as Class<out Module.Settings?>
            return Gson().fromJson(json, cls)
        } catch (e: ClassNotFoundException) {
            throw JsonParseException(e)
        }
    }

}
