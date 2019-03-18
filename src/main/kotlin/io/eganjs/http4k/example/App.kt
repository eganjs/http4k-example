package io.eganjs.http4k.example

import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes

object App {
    operator fun invoke() =
        routes(
            "/ping" bind GET to { Response(OK).body("pong") }
        )
}
