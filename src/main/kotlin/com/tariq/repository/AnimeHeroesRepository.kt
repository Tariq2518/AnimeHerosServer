package com.tariq.repository

import com.tariq.models.AnimeApiResponse
import com.tariq.models.AnimeHero

interface AnimeHeroesRepository {

    val animeHeroes : Map<Int, List<AnimeHero>>
    val page1:List<AnimeHero>
    val page2:List<AnimeHero>
    val page3:List<AnimeHero>
    val page4:List<AnimeHero>
    val page5:List<AnimeHero>
    val page6:List<AnimeHero>
    suspend fun getAllHeroes(page:Int =1): AnimeApiResponse

    suspend fun searchHeroes(heroName: String?): AnimeApiResponse
}