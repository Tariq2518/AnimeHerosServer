package com.tariq.plugins

import com.tariq.routes.getAllAnimeHeroes
import com.tariq.routes.root
import com.tariq.routes.searchAnimeHeroes
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*

fun Application.configureRouting() {
    routing {
        staticResources("/images", "images")
        root()
        getAllAnimeHeroes()
        searchAnimeHeroes()
    }
}
