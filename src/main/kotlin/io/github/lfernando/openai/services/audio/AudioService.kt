package io.github.lfernando.openai.services.audio

import org.slf4j.LoggerFactory
import org.springframework.core.io.PathResource
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

interface AudioService {
    suspend fun createTranscription(data: CreateTranscriptionRequest): CreateTranscriptionResponse
    suspend fun createTranslation(data: CreateTranslationRequest): CreateTranslationResponse
}

@Service
class AudioServiceImpl(
    val client: WebClient
): AudioService {
    companion object {
        private val log = LoggerFactory.getLogger(AudioServiceImpl::class.java)
    }

    override suspend fun createTranscription(data: CreateTranscriptionRequest): CreateTranscriptionResponse {
        val path = "/audio/transcriptions"
        val multipartBodyBuilder = MultipartBodyBuilder()
        multipartBodyBuilder.part("file", PathResource(data.file))
        multipartBodyBuilder.part("model", data.model)
        if (data.prompt != null) {
            multipartBodyBuilder.part("prompt", data.prompt)
        }
        if (data.responseFormat != null) {
            multipartBodyBuilder.part("response_format", data.responseFormat)
        }
        if (data.temperature != null) {
            multipartBodyBuilder.part("temperature", data.temperature)
        }
        if (data.language != null) {
            multipartBodyBuilder.part("language", data.language)
        }
        return client
            .post()
            .uri(path)
            .bodyValue(multipartBodyBuilder.build())
            .retrieve()
            .awaitBody()
    }

    override suspend fun createTranslation(data: CreateTranslationRequest): CreateTranslationResponse {
        val path = "/audio/translations"
        val multipartBodyBuilder = MultipartBodyBuilder()
        multipartBodyBuilder.part("file", PathResource(data.file))
        multipartBodyBuilder.part("model", data.model)
        if (data.prompt != null) {
            multipartBodyBuilder.part("prompt", data.prompt)
        }
        if (data.responseFormat != null) {
            multipartBodyBuilder.part("response_format", data.responseFormat)
        }
        if (data.temperature != null) {
            multipartBodyBuilder.part("temperature", data.temperature)
        }
        return client
            .post()
            .uri(path)
            .bodyValue(multipartBodyBuilder.build())
            .retrieve()
            .awaitBody()
    }

}