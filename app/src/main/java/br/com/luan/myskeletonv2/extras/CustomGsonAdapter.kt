package br.com.luan.myskeletonv2.extras

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject


/**
 * Created by Luan on 04/05/17.
 */

class CustomGsonAdapter {

    fun serialize(objects: List<Any>, arrKey: String, objKey: String): String {
        val ja = JsonArray()
        for (`object` in objects) {
            val gson = Gson()
            val je = gson.toJsonTree(`object`)
            val jo = JsonObject()
            jo.add(objKey, je)
            ja.add(jo)
        }

        val objMain = JsonObject()
        objMain.add(arrKey, ja)

        return objMain.toString()

    }


    fun serialize(`object`: Any, objKey: String): String {
        val gson = Gson()
        val je = gson.toJsonTree(`object`)
        val jo = JsonObject()
        jo.add(objKey, je)



        return jo.toString()

    }

    fun serialize(`object`: Any, objKey: String, property: String, value: String): String {
        val gson = Gson()
        val je = gson.toJsonTree(`object`)
        val jo = JsonObject()
        jo.add(objKey, je)
        jo.addProperty(property, value)



        return jo.toString()

    }
}