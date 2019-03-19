package io.eganjs.http4k.example.domain

import java.time.Duration
import java.time.Instant

const val iterations = 1_000_000

fun benchmark(fn: () -> Unit) {
    repeat(Math.sqrt(iterations.toDouble()).toInt()) { fn() }

    val start = Instant.now()
    repeat(iterations) { fn() }
    val end = Instant.now()

    val difference = Duration.between(start, end)
    val averageRuntime = difference.dividedBy(iterations.toLong())

    println(averageRuntime)
}

fun main() {
    val dslJson = DslJsonLensMessageTest()

    println("\ndsl-json serialisation")
    benchmark { dslJson.`dslJsonLens successfully serialises message`() }

    println("\ndsl-json derialisation")
    benchmark { dslJson.`dslJsonLens successfully deserialises message`() }

    val lens = LensMessageTest()

    println("\nlens serialisation")
    benchmark { lens.`lens successfully serialises message`() }

    println("\nlens derialisation")
    benchmark { lens.`lens successfully deserialises message`() }
}
