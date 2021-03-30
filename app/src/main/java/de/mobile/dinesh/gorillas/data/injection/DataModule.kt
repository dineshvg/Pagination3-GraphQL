package de.mobile.dinesh.gorillas.data.injection

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import de.mobile.dinesh.gorillas.data.PostsRepositoryImpl
import de.mobile.dinesh.gorillas.data.network.client.ApiClient
import de.mobile.dinesh.gorillas.domain.reposiotory.PostsRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val BASE_URL = "https://graphqlzero.almansi.me/api"
private const val BASE_URL_NAME = "BASE_URL"
private const val MAX_ITEMS_PER_PAGE = "MAX_ITEMS_PER_PAGE"
private const val PAGE_ITEMS = 25
const val BACKGROUND_DISPATCHER = "background_dispatcher"

val commonModule = module {
    single(named(BACKGROUND_DISPATCHER)) { Dispatchers.IO }
}

val dataModule = module {

    //base-url
    single(named(BASE_URL_NAME)) { BASE_URL }

    factory(named(MAX_ITEMS_PER_PAGE)) { PAGE_ITEMS }

    //Logger
    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }

    //OkHttpClient
    single { OkHttpClient().newBuilder()
        .addInterceptor(get<HttpLoggingInterceptor>())
        .build() }

    //ApolloClient
    single {
        ApolloClient.builder()
            .serverUrl(get<String>(named(BASE_URL_NAME)))
            .normalizedCache(LruNormalizedCacheFactory(EvictionPolicy.builder().maxSizeBytes(10 * 1024 * 1024).build()))
            .okHttpClient(get())
            .build()
    }

    factory { ApiClient(get(), get(named(MAX_ITEMS_PER_PAGE)), get(named(BACKGROUND_DISPATCHER))) }

    factory<PostsRepository> {
        PostsRepositoryImpl(get())
    }
}