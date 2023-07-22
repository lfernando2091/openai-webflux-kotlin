package io.github.lfernando.openai.services.completion

import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

interface CompletionService {
    suspend fun create(data: CreateCompletionRequest): CreateCompletionResponse
}

class CompletionServiceImpl(
    val client: WebClient
): CompletionService {
    companion object {
        private val log = LoggerFactory.getLogger(CompletionServiceImpl::class.java)
        const val path = "/completions"
    }
    override suspend fun create(data: CreateCompletionRequest): CreateCompletionResponse =
        client
            .post()
            .uri(path)
            .bodyValue(data)
            .retrieve()
            .awaitBody()
}