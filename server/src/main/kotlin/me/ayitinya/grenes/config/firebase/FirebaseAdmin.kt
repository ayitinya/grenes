package me.ayitinya.grenes.config.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.InputStream

object FirebaseAdmin {
    private val serviceAccount: InputStream? =
        this::class.java.classLoader?.getResourceAsStream("grenes-1759f-firebase-adminsdk-xz2uv-c31b7f9b75.json")

    private val options: FirebaseOptions = FirebaseOptions.builder().setCredentials(
        GoogleCredentials.fromStream(
            serviceAccount
        )
    ).build()

    fun init(): FirebaseApp = FirebaseApp.initializeApp(options)
}