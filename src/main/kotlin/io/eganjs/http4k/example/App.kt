package io.eganjs.http4k.example

import com.dslplatform.json.DslJson
import com.dslplatform.json.runtime.Settings
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets

object App {
    private val dslJson = DslJson<Any>(Settings.withRuntime<Any>().includeServiceLoader())

    operator fun invoke() =
        routes(
            "/ping" bind GET to {
                val msg = Message("pong")

                val buffer = ByteArrayOutputStream()
                dslJson.serialize(msg, buffer)
                val json = buffer.toString(StandardCharsets.UTF_8)

                Response(OK).body(json)
            }
        )
}
