package net.slingspot.picks.server.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import net.slingspot.picks.model.entities.identity.User
import net.slingspot.picks.server.data.DataAccess
import java.io.FileInputStream

class FirebaseRepository : DataAccess {
    private val auth: FirebaseAuth
    private val store: Firestore

    init {
        FirebaseApp.initializeApp(
            FirebaseOptions.Builder().setCredentials(
                // TODO need to package the json appropriately.
                GoogleCredentials.fromStream(FileInputStream(FIREBASE_JSON))
            ).build()
        )

        auth = FirebaseAuth.getInstance()
        store = FirestoreClient.getFirestore()
    }

    override fun users() = store.collection("users").get()
        .get().documents
        .mapNotNull { it.toObject(User::class.java) }

    companion object {
        private const val FIREBASE_JSON = "picks-firebase-admin.json"
    }
}