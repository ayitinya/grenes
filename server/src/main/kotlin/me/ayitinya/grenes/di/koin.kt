package me.ayitinya.grenes.di

import me.ayitinya.grenes.config.firebase.FirebaseAdmin
import me.ayitinya.grenes.data.Db
import me.ayitinya.grenes.data.challenges.ChallengeDao
import me.ayitinya.grenes.data.challenges.DefaultChallengeDao
import me.ayitinya.grenes.data.media.DefaultMediaDao
import me.ayitinya.grenes.data.media.MediaDao
import me.ayitinya.grenes.data.users.DefaultUserDao
import me.ayitinya.grenes.data.users.UserDao
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dbModule = module {
    single {
        Db(
            driverClassName = "org.h2.Driver", jdbcURL = "jdbc:h2:file:./build/dbtest;MODE=MYSQL"
        )
    }

//    single {
//        Db(
//            driverClassName = "org.h2.Driver", jdbcURL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
//        )
//    }

    factory(qualifier = named("test")) { params ->
        Db(
            driverClassName = "org.h2.Driver",
            jdbcURL = "jdbc:h2:mem:${params.get<String>()};DB_CLOSE_DELAY=-1"
        )
    }

    single<UserDao> { DefaultUserDao() }

    single(createdAtStart = true) {
        FirebaseAdmin()
    }

    single<MediaDao> { DefaultMediaDao() }

    single<ChallengeDao> { DefaultChallengeDao() }
}

val appModule = module {

}
