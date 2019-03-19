package io.eganjs.http4k.example

import com.dslplatform.json.CompiledJson

@CompiledJson
data class Message(
    val msg: String
)
