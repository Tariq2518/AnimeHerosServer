package com.tariq.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.root() {
    get("/") {
        call.respond(
            message = "API for the Anime Heroes",
            status = HttpStatusCode.OK
        )
    }
}