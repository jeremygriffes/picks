//package net.slingspot.network
//
//import io.ktor.client.*
//import io.ktor.client.call.*
//import io.ktor.client.engine.*
//import io.ktor.client.plugins.contentnegotiation.*
//import io.ktor.client.request.*
//import io.ktor.http.*
//import io.ktor.serialization.kotlinx.json.*
//import kotlinx.serialization.json.Json
//import net.slingspot.core.Logger
//import javax.inject.Inject
//
//class KtorClient @Inject constructor(
//    engine: HttpClientEngine,
//    private val log: Logger,
//) {
//    val client: HttpClient = HttpClient(engine) {
//        install(ContentNegotiation) {
//            json(
//                Json {
//                    ignoreUnknownKeys = true
//                    isLenient = true
//                }
//            )
//        }
//    }
//
//    fun request(
//        path: String,
//        headers: Map<String, String>
//    ) = HttpRequestBuilder().apply {
//        headers.forEach {
//            header(it.key, it.value)
//        }
//        val securePath = secure(path)
//        log.d(tag) { securePath }
//        url(URLBuilder(securePath).build())
//    }
//
//    private fun secure(path: String) =
//        path.replace("http://", "https://", ignoreCase = true)
//
//    suspend inline fun <reified T : Any> fetch(
//        uri: String,
//        headers: Map<String, String>
//    ) = client.get(request(uri, headers)).body() as? T ?: error("Null response")
//
//    suspend inline fun <reified T : Any> fetch(uri: String) = (fetch(uri, emptyMap()) as T)
//
//    companion object {
//        private val tag = NetworkKtor::javaClass.name
//    }
//}