package io.eganjs.http4k.example.domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.auto
import org.http4k.hamkrest.hasBody
import org.http4k.lens.BiDiBodyLens
import org.junit.jupiter.api.Test


class LensMessageTest {

    private val messageLens: BiDiBodyLens<Message> = Body.auto<Message>().toLens()

    private val deserialisedMessage = Message("test message")
    private val serialisedMessage = """{"msg":"test message"}"""

    @Test
    fun `lens successfully serialises message`() {
        val response = Response(OK)

        val actual = messageLens(deserialisedMessage, response)

        assertThat(actual, hasBody(equalTo(serialisedMessage)))
    }

    @Test
    fun `lens successfully deserialises message`() {
        val request = Request(GET, "/").body(serialisedMessage)

        val actual: Message = messageLens(request)

        assertThat(actual, equalTo(deserialisedMessage))
    }
}
