package com.tariq.routes

import com.tariq.models.AnimeApiResponse
import com.tariq.repository.AnimeHeroesRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.lang.NumberFormatException

fun Route.getAllAnimeHeroes() {

    val animeHeroRepository: AnimeHeroesRepository by inject()

    get("/anime/heroes") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..6)

            val response = animeHeroRepository.getAllHeroes(page = page)

            call.respond(
                message = response,
                status = HttpStatusCode.OK
            )
        } catch (e: NumberFormatException) {
            call.respond(
                message = AnimeApiResponse(success = false, message = "Only Numbers allowed."),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException) {
            call.respond(
                message = AnimeApiResponse(success = false, message = "No Heroes Found"),
                status = HttpStatusCode.NotFound
            )
        }
    }
}