package io.github.lfernando2091.openai.services.edit

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

interface EditService {
    suspend fun create(data: CreateEditRequest): CreateEditResponse
    fun createBlocking(data: CreateEditRequest): ResponseEntity<CreateEditResponse?>
}

@Service
class EditServiceImpl(
    val client: WebClient
): EditService {
    companion object {
        private val log = LoggerFactory.getLogger(EditServiceImpl::class.java)
        const val path = "/edits"
    }

    override suspend fun create(data: CreateEditRequest): CreateEditResponse =
        client
            .post()
            .uri(path)
            .bodyValue(data)
            .retrieve()
            .awaitBody()

    override fun createBlocking(data: CreateEditRequest): ResponseEntity<CreateEditResponse?> =
        client
            .post()
            .uri(path)
            .bodyValue(data)
            .retrieve()
            .toEntity(CreateEditResponse::class.java)
            .toFuture()
            .get()
}