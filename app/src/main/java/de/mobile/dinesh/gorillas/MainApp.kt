package de.mobile.dinesh.gorillas

import android.app.Application
import de.mobile.dinesh.gorillas.data.injection.commonModule
import de.mobile.dinesh.gorillas.data.injection.dataModule
import de.mobile.dinesh.gorillas.domain.injection.domainModule
import de.mobile.dinesh.gorillas.ui.postlist.injection.postListViewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApp)
            modules(
                listOf(
                    commonModule,
                    dataModule,
                    domainModule,
                    postListViewModule
                )
            )
        }
    }
}