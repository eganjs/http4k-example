package io.eganjs.http4k.example

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.auto
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test

class AppTest {

    private val app = App()

    private val messageLens = Body.auto<Message>().toLens()

    @Test
    fun `ping returns pong`() {
        Request(GET, "/ping")

        val response = app(Request(GET, "/ping"))

        assertThat(response, hasStatus(OK))
        assertThat(
            response, hasBody(
                messageLens,
                equalTo(Message("pong"))
            )
        )
    }
}
