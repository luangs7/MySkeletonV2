package br.com.luan.myskeletonv2.data.dao

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by luan on 19/10/2016.
 */
class JsonListHelper<T>(wrapped: Class<T>) : ParameterizedType {

    private val wrapped: Class<*>

    init {
        this.wrapped = wrapped
    }

    override fun getActualTypeArguments(): Array<Type> {
        return arrayOf(wrapped)
    }

    override fun getRawType(): Type {
        return List::class.java
    }

    override fun getOwnerType(): Type? {
        return null
    }

}
