package net.slingspot.picks.server.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import net.slingspot.picks.model.entities.identity.User
import net.slingspot.picks.server.data.DataAccess

class FirebaseRepository : DataAccess {
    private val auth: FirebaseAuth
    private val store: Firestore

    init {
        val json = requireNotNull(object {}.javaClass.getResourceAsStream(FIREBASE_JSON))

        val credentials = GoogleCredentials.fromStream(json)
        val builder = FirebaseOptions.Builder()
        builder.setCredentials(credentials)
        val options = builder.build()

        FirebaseApp.initializeApp(options)

        auth = FirebaseAuth.getInstance()
        store = FirestoreClient.getFirestore()
    }

    override fun users() = store.collection("users").get()
        .get().documents
        .mapNotNull { it.toObject(User::class.java) }

    companion object {
        private const val FIREBASE_JSON = "/picks-firebase-admin.json"
    }
}