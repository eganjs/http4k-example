package io.eganjs.http4k.example

import com.dslplatform.json.DslJson
import com.dslplatform.json.runtime.Settings
import org.http4k.core.ContentType
import org.http4k.lens.BiDiBodyLens
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets

object DslJsonLens {
    private const val initialBufferSize = 1024

    private val dslJson = DslJson<Any>(Settings.withRuntime<Any>().includeServiceLoader())

    inline fun <reified T> of(): BiDiBodyLens<T> = of(T::class.java)

    fun <T> of(clazz: Class<T>): BiDiBodyLens<T> {
        val threadLocalBuffer: ThreadLocal<ByteArrayOutputStream> = ThreadLocal.withInitial {
            ByteArrayOutputStream(initialBufferSize)
        }

        return BiDiBodyLens(
            emptyList(),
            ContentType.APPLICATION_JSON,
            { httpMessage ->
                dslJson.deserialize(clazz, httpMessage.body.stream)
            },
            { entity, httpMessage ->
                val buffer = threadLocalBuffer.get()
                httpMessage.body(
                    buffer.run {
                        reset()
                        dslJson.serialize(entity, buffer)
                        toString(StandardCharsets.UTF_8)
                    }
                )
            }
        )
    }
}
