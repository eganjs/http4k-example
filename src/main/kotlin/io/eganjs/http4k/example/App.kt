package io.eganjs.http4k.example

import io.eganjs.http4k.example.handlers.PingHandler
import org.http4k.core.Method.GET
import org.http4k.routing.bind
import org.http4k.routing.routes

object App {

    operator fun invoke() =
        routes(
            "/ping" bind GET to PingHandler
        )
}
