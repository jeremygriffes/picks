package net.slingspot.picks.server.firebase

import com.google.cloud.firestore.CollectionReference

fun <T> CollectionReference.eachDocument(map: Map<String, Any>.() -> T) = listDocuments()
    .mapNotNull { docRef ->
        docRef.get().get().data?.let { map(it) }
    }

fun Map<String, Any>.string(key: String): String? = get(key) as? String

fun Map<String, Any>.int(key: String): Int? = get(key) as? Int
