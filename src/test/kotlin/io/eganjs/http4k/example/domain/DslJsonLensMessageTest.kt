package io.eganjs.http4k.example.domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.eganjs.http4k.example.DslJsonLens
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasBody
import org.http4k.lens.BiDiBodyLens
import org.junit.jupiter.api.Test

class DslJsonLensMessageTest {

    private val messageDslJsonLens: BiDiBodyLens<Message> = DslJsonLens.of()

    private val deserialisedMessage = Message("test message")
    private val serialisedMessage = """{"msg":"test message"}"""

    @Test
    fun `dslJsonLens successfully serialises message`() {
        val response = Response(OK)

        val actual: Response = messageDslJsonLens(deserialisedMessage, response)

        assertThat(actual, hasBody(equalTo(serialisedMessage)))
    }

    @Test
    fun `dslJsonLens successfully deserialises message`() {
        val request = Request(GET, "/").body(serialisedMessage)

        val actual: Message = messageDslJsonLens(request)

        assertThat(actual, equalTo(deserialisedMessage))
    }
}
