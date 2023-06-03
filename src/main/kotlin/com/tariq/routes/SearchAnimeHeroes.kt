package com.tariq.routes

import com.tariq.repository.AnimeHeroesRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.searchAnimeHeroes() {
    val animeHeroRepository: AnimeHeroesRepository by inject()

    get("/anime/heroes/search") {
        val name = call.request.queryParameters["name"]

        val response = animeHeroRepository.searchHeroes(heroName = name)

        call.respond(
            message = response,
            status = HttpStatusCode.OK
        )

    }
}