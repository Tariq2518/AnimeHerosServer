package com.tariq

import com.tariq.models.AnimeApiResponse
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import com.tariq.plugins.*
import com.tariq.repository.AnimeHeroesRepository
import com.tariq.repository.AnimeHeroesRepositoryImp
import io.ktor.client.call.*
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent.inject

class ApplicationTest {

    private val animeHeroesRepository: AnimeHeroesRepository by inject(AnimeHeroesRepository::class.java)

    @Test
    fun `root end points, correct information`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(
                expected = HttpStatusCode.OK,
                actual = status
            )
            assertEquals(
                expected = "API for the Anime Heroes",
                actual = bodyAsText()
            )
        }
    }
    @Test
    fun `anime heroes endpoint, correct information`() = testApplication {
        environment {
            developmentMode = false
        }
        application {
            configureRouting()
        }

        client.get("/anime/heroes").apply {
            assertEquals(
                expected = HttpStatusCode.OK,
                actual = status
            )

            val expectedOutput = AnimeApiResponse(
                success = true,
                message = "Here are Heroes",
                previousPage = null,
                nextPage = 2,
                animeHeroes = animeHeroesRepository.page1
            )

            val actualOutput = Json.decodeFromString<AnimeApiResponse>(bodyAsText())

            assertEquals(
                expected = expectedOutput,
                actual = actualOutput
            )

        }
    }

    @Test
    fun `anime heroes endpoint page not found, correct information`() = testApplication {

        application {
            configureRouting()
        }

        client.get("/anime/heroes?page=7").apply {
            assertEquals(
                expected = HttpStatusCode.NotFound,
                actual = status
            )

            val expectedOutput = AnimeApiResponse(
                success = false,
                message = "No Heroes Found"
            )

            val actualOutput = Json.decodeFromString<AnimeApiResponse>(bodyAsText())

            assertEquals(
                expected = expectedOutput,
                actual = actualOutput
            )

        }
    }

    @Test
    fun `anime heroes endpoint invalid page, correct information`() = testApplication {

        application {
            configureRouting()
        }

        client.get("/anime/heroes?page=f").apply {
            assertEquals(
                expected = HttpStatusCode.BadRequest,
                actual = status
            )

            val expectedOutput = AnimeApiResponse(
                success = false,
                message = "Only Numbers allowed."
            )

            val actualOutput = Json.decodeFromString<AnimeApiResponse>(bodyAsText())

            assertEquals(
                expected = expectedOutput,
                actual = actualOutput
            )

        }
    }
    @Test
    fun `anime heroes endpoint all pages, correct information`() = testApplication {
        environment {
            developmentMode = false
        }
        application {
            configureRouting()
        }

        val pages = 1..6
        pages.forEach{
            client.get("/anime/heroes?page=$it").apply {
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = status
                )

                val expectedOutput = AnimeApiResponse(
                    success = true,
                    message = "Here are Heroes",
                    previousPage = getCurrentPage(page = it)["previousPage"],
                    nextPage = getCurrentPage(page = it)["nextPage"],
                    animeHeroes = listOf(animeHeroesRepository.page1, animeHeroesRepository.page2, animeHeroesRepository.page3,
                        animeHeroesRepository.page4, animeHeroesRepository.page5, animeHeroesRepository.page6)[it -1]
                )

                val actualOutput = Json.decodeFromString<AnimeApiResponse>(bodyAsText())

                assertEquals(
                    expected = expectedOutput,
                    actual = actualOutput
                )

            }
        }
    }

    private fun getCurrentPage(page: Int): Map<String, Int?> {
        var previousPage: Int? = page
        var nextPage: Int? = page

        if (page in 1..5) {
            nextPage = nextPage?.plus(1)
        }
        if (page in 2..6) {
            previousPage = previousPage?.minus(1)
        }
        if (page == 1) {
            previousPage = null
        }
        if (page == 6) {
            nextPage = null
        }

        return mapOf("previousPage" to previousPage, "nextPage" to nextPage)

    }

    @Test
    fun `anime search heroes endpoint, correct information`() = testApplication {

        application {
            configureRouting()
        }

        client.get("/anime/heroes/search?name=vegeta").apply {
            assertEquals(
                expected = HttpStatusCode.OK,
                actual = status
            )

            val actualOutput = Json.decodeFromString<AnimeApiResponse>(bodyAsText()).animeHeroes.size

            assertEquals(
                expected = 1,
                actual = actualOutput
            )

        }
    }

}
