package io.eganjs.http4k.example.handlers

import io.eganjs.http4k.example.DslJsonLens
import io.eganjs.http4k.example.domain.Message
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

object PingHandler : HttpHandler {
    private val messageLens = DslJsonLens.of<Message>()

    override fun invoke(p1: Request): Response =
        messageLens(Message("pong"), Response(Status.OK))
}
