package com.tariq.di

import com.tariq.repository.AnimeHeroesRepository
import com.tariq.repository.AnimeHeroesRepositoryImp
import org.koin.dsl.module

val koinModule = module {
    single<AnimeHeroesRepository> {
        AnimeHeroesRepositoryImp()
    }
}