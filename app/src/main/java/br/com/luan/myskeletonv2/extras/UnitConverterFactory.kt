package br.com.luan.myskeletonv2.extras

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by Luan on 04/12/17.
 */
object UnitConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>,
                                       retrofit: Retrofit): Converter<ResponseBody, *>? {
        return if (type == Unit::class.java) UnitConverter else null
    }

    private object UnitConverter : Converter<ResponseBody, Unit> {
        override fun convert(value: ResponseBody) {
            value.close()
        }
    }
}