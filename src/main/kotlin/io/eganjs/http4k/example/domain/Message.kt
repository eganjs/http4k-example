package io.eganjs.http4k.example.domain

import com.dslplatform.json.CompiledJson

@CompiledJson
data class Message(
    val msg: String
)
