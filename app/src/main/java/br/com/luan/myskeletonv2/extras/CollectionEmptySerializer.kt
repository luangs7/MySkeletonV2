package br.com.luan.myskeletonv2.extras

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer

import java.lang.reflect.Type

/**
 * Created by Luan on 18/12/17.
 */

class CollectionEmptySerializer : JsonSerializer<List<*>> {
    override fun serialize(src: List<*>?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement? {
        if (src == null || src.isEmpty())
        // exclusion is made here
            return null

        val array = JsonArray()

        for (child in src) {
            val element = context.serialize(child)
            array.add(element)
        }

        return array
    }
}

