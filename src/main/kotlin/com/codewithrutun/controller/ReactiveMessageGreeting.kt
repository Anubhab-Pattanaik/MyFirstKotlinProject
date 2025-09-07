package com.codewithrutun.controller

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

// test

@Path("/hello")
class ReactiveMessageGreeting {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    suspend fun hello(): String = "Hello to my new Kotlin Programming World!"

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun helloJson(): Map<String, String> =
        mapOf(
            "message" to "Hello to my new Kotlin Programming World!",
            "language" to "Kotlin",
            "framework" to "Quarkus",
        )
}
