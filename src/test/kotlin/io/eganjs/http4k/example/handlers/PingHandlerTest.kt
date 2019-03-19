package io.eganjs.http4k.example.handlers

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.eganjs.http4k.example.domain.Message
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.format.Jackson.auto
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test

class PingHandlerTest {

    private val pingHandler = PingHandler

    private val messageLens = Body.auto<Message>().toLens()

    @Test
    fun `ping returns pong`() {
        Request(Method.GET, "/ping")

        val response = pingHandler(Request(Method.GET, "/ping"))

        assertThat(response, hasStatus(Status.OK))
        assertThat(
            response, hasBody(
                messageLens,
                equalTo(Message("pong"))
            )
        )
    }
}
