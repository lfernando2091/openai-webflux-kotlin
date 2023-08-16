package io.github.lfernando2091.openai.services.chat

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

interface ChatService {
    suspend fun create(data: CreateChatCompletionRequest): CreateChatCompletionResponse
}

@Service
class ChatServiceImpl(
    val client: WebClient
): ChatService {
    companion object {
        private val log = LoggerFactory.getLogger(ChatServiceImpl::class.java)
        const val path = "/chat/completions"
    }

    override suspend fun create(data: CreateChatCompletionRequest): CreateChatCompletionResponse =
        client
            .post()
            .uri(path)
            .bodyValue(data)
            .retrieve()
            .awaitBody()
}