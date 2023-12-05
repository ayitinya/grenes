package me.ayitinya.grenes.di

import me.ayitinya.grenes.data.Db
import me.ayitinya.grenes.data.location.DefaultLocationDao
import me.ayitinya.grenes.data.location.LocationDao
import me.ayitinya.grenes.data.media.DefaultMediaDao
import me.ayitinya.grenes.data.media.MediaDao
import me.ayitinya.grenes.data.users.DefaultUserDao
import me.ayitinya.grenes.data.users.UserDao
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dbModule = module {
    single(createdAtStart = true) {
        Db(
            driverClassName = "org.h2.Driver", jdbcURL = "jdbc:h2:file:./build/db"
        )
    }

    single(qualifier = named("test"), createdAtStart = true) {
        Db(
            driverClassName = "org.h2.Driver", jdbcURL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
        )
    }

    single<UserDao> { DefaultUserDao() }

    single<LocationDao> { DefaultLocationDao() }

    single<MediaDao> { DefaultMediaDao() }
}