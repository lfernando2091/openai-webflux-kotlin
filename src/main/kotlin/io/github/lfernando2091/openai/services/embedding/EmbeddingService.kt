package io.github.lfernando2091.openai.services.embedding

import io.github.lfernando2091.openai.services.chat.ChatServiceImpl
import io.github.lfernando2091.openai.services.completion.CreateCompletionResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

interface EmbeddingService {
    suspend fun create(data: CreateEmbeddingsRequest): CreateEmbeddingsResponse
    fun createBlocking(data: CreateEmbeddingsRequest): ResponseEntity<CreateEmbeddingsResponse?>
}

@Service
class EmbeddingServiceImpl(
    val client: WebClient
): EmbeddingService {
    companion object {
        private val log = LoggerFactory.getLogger(EmbeddingServiceImpl::class.java)
        const val path = "/embeddings"
    }
    override suspend fun create(data: CreateEmbeddingsRequest): CreateEmbeddingsResponse =
        client
            .post()
            .uri(path)
            .bodyValue(data)
            .retrieve()
            .awaitBody()

    override fun createBlocking(data: CreateEmbeddingsRequest): ResponseEntity<CreateEmbeddingsResponse?> =
        client
            .post()
            .uri(path)
            .bodyValue(data)
            .retrieve()
            .toEntity(CreateEmbeddingsResponse::class.java)
            .toFuture()
            .get()
}