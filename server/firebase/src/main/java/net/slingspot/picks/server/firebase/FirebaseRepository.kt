package net.slingspot.picks.server.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import net.slingspot.picks.model.entities.identity.User
import net.slingspot.picks.server.data.DataAccess
import net.slingspot.picks.server.firebase.model.toUser

class FirebaseRepository : DataAccess {
    private val auth: FirebaseAuth
    private val store: Firestore

    init {
        val json = requireNotNull(object {}.javaClass.getResourceAsStream(FIREBASE_JSON))
        val credentials = GoogleCredentials.fromStream(json)
        val options = FirebaseOptions.builder()
            .setCredentials(credentials)
            .build()

        FirebaseApp.initializeApp(options)

        auth = FirebaseAuth.getInstance()
        store = FirestoreClient.getFirestore()
    }

    override suspend fun users(): List<User> = store
        .collection("users")
        .eachDocument { toUser() }

    companion object {
        private const val FIREBASE_JSON = "/picks-firebase-admin.json"
    }
}