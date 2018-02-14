package br.com.luan.myskeletonv2.data.dao

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONArray
import java.text.Normalizer

/**
 * Created by luan on 19/10/2016.
 */
@SuppressWarnings("unused")
abstract class AbstractLocalDb<E> protected constructor(protected var context: Context) {
    private val gson: Gson
    private val spName = "SharedPreferences"

    init {
        this.gson = Gson()
    }

    fun save(`object`: E, type: Class<E>, name: String) {
        val settings = context.getSharedPreferences(spName, 0)
        val editor = settings.edit()

        val objectJson = gson.toJson(`object`, type)

        editor.putString(name, objectJson)
        editor.commit()
    }

    fun save(mObject: E) {
        val settings = context.getSharedPreferences(spName, 0)
        val editor = settings.edit()

        val objectJson = gson.toJson(mObject, (mObject as Any)::class.java)

        editor.putString( (mObject as Any)::class.simpleName, objectJson)
        editor.commit()
    }

    operator fun get(type: Class<E>, name: String): E? {
        val settings = context.getSharedPreferences(spName, 0)

        val model: E
        val objectJson = settings.getString(name, null)
        if (objectJson != null) {
            model = gson.fromJson(objectJson, type)
        } else {
            return null
        }

        return model

    }

    fun getDefault(type: Class<E>): E? {
        return get(type, type.simpleName)
    }

    fun clearObject(name: String) {
        val settings = context.getSharedPreferences(spName, 0)
        val editor = settings.edit()
        editor.remove(name)
        editor.commit()
    }

    fun clearObject(type: Class<E>) {
        val settings = context.getSharedPreferences(spName, 0)
        val editor = settings.edit()
        editor.remove(type.simpleName)
        editor.commit()
    }

    @Throws(Exception::class)
    private fun getList(type: Class<E>, json: JSONArray): List<E>? {
        val gsonB = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

        return gsonB.fromJson<List<E>>(removeAcentos(json.toString()), JsonListHelper(type))
    }

    fun saveList(objectList: List<E>, name: String) {

        val json = Gson().toJson(objectList)
        println(json)

        val settings = context.getSharedPreferences(spName, 0)
        val editor = settings.edit()

        editor.putString(name, json)
        editor.commit()
    }

    fun getList(type: Class<E>, name: String): List<E>? {
        val settings = context.getSharedPreferences(spName, 0)

        val listJson = settings.getString(name, null)
        if (listJson != null) {
            try {
                return getList(type, JSONArray(listJson))
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return null
    }

    fun getDefaultList(type: Class<E>): List<E>? {
        return getList(type, type.simpleName)
    }

    fun removeAcentos(str: String): String {
        var mStr = str

        mStr = Normalizer.normalize(mStr, Normalizer.Form.NFD)
        mStr = mStr.replace("[^\\p{ASCII}]".toRegex(), "")
        return mStr

    }
}
