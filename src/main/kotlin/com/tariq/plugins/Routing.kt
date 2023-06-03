package com.tariq.plugins

import com.tariq.routes.getAllAnimeHeroes
import com.tariq.routes.root
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*

fun Application.configureRouting() {
    routing {
//        static("/images") {
//            resources("images")
//        }
        staticResources("/images", "images")
        root()
        getAllAnimeHeroes()
    }
}
