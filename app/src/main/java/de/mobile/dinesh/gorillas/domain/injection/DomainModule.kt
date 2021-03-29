package de.mobile.dinesh.gorillas.domain.injection

import de.mobile.dinesh.gorillas.data.injection.BACKGROUND_DISPATCHER
import de.mobile.dinesh.gorillas.domain.usecase.GetPostByIdUseCase
import de.mobile.dinesh.gorillas.domain.usecase.GetPostsUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetPostsUseCase(get(), get(named(BACKGROUND_DISPATCHER)))
    }

    factory {
        GetPostByIdUseCase(get(), get(named(BACKGROUND_DISPATCHER)))
    }
}