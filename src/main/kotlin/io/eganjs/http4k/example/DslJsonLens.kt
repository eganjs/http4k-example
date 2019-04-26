package io.eganjs.http4k.example

import com.dslplatform.json.DslJson
import com.dslplatform.json.runtime.Settings
import org.http4k.core.ContentType
import org.http4k.core.HttpMessage
import org.http4k.lens.BiDiBodyLens
import java.io.ByteArrayOutputStream

object DslJsonLens {

    private const val initialBufferSize = 1024

    private val dslJson = DslJson<Any>(Settings.withRuntime<Any>().includeServiceLoader())

    inline fun <reified T> of(): BiDiBodyLens<T> =
        of(T::class.java)

    fun <T> of(clazz: Class<T>): BiDiBodyLens<T> =
        BiDiBodyLens(
            emptyList(),
            ContentType.APPLICATION_JSON,
            deserialiser(clazz),
            serialiser()
        )

    private fun <T> deserialiser(clazz: Class<T>): (HttpMessage) -> T = { httpMessage ->
        dslJson.deserialize(clazz, httpMessage.body.stream)!!
    }

    private fun <T> serialiser(): (T, HttpMessage) -> HttpMessage = { entity, httpMessage ->
        val buffer = ByteArrayOutputStream(initialBufferSize)
        dslJson.serialize(entity, buffer)
        httpMessage.body(buffer.toString())
    }
}
