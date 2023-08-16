package io.github.lfernando2091.openai

import io.github.lfernando2091.openai.exceptions.OpenAIException
import io.github.lfernando2091.openai.services.audio.AudioService
import io.github.lfernando2091.openai.services.audio.AudioServiceImpl
import io.github.lfernando2091.openai.services.chat.ChatService
import io.github.lfernando2091.openai.services.chat.ChatServiceImpl
import io.github.lfernando2091.openai.services.completion.CompletionService
import io.github.lfernando2091.openai.services.completion.CompletionServiceImpl
import io.github.lfernando2091.openai.services.edit.EditService
import io.github.lfernando2091.openai.services.edit.EditServiceImpl
import io.github.lfernando2091.openai.services.embedding.EmbeddingService
import io.github.lfernando2091.openai.services.embedding.EmbeddingServiceImpl
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Configuration
@AutoConfiguration
@EnableConfigurationProperties(ClientAutoConfigurationProperties::class)
open class ClientAutoConfiguration(
    val properties: ClientAutoConfigurationProperties
) {

    private fun errorHandlerExchangeFilterFunction(): ExchangeFilterFunction =
        ExchangeFilterFunction.ofResponseProcessor { clientResponse ->
            if (clientResponse.statusCode().isError) {
                clientResponse
                    .bodyToMono(String::class.java)
                        .flatMap { errorMessage ->
                            Mono.error(
                                OpenAIException(clientResponse.statusCode().value(),
                                errorMessage)
                            )
                        }
            } else {
                Mono.just(clientResponse)
            }
        }

    @Bean
    @Qualifier("OpenAIClient")
    open fun webClient(): WebClient =
        WebClient.builder()
            .baseUrl(properties.baseUrl)
            .defaultHeaders { httpHeaders ->
                httpHeaders.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", properties.apiKey))
                if (properties.organizationId != null) {
                    httpHeaders.add("OpenAI-Organization", properties.organizationId)
                }
            }
            .filter(errorHandlerExchangeFilterFunction())
            .build()

    @Bean
    @ConditionalOnMissingBean
    open fun chatService(
        @Qualifier("OpenAIClient") client: WebClient
    ): ChatService = ChatServiceImpl(client)

    @Bean
    @ConditionalOnMissingBean
    open fun embeddingService(
        @Qualifier("OpenAIClient") client: WebClient
    ): EmbeddingService = EmbeddingServiceImpl(client)

    @Bean
    @ConditionalOnMissingBean
    open fun completionService(
        @Qualifier("OpenAIClient") client: WebClient
    ): CompletionService = CompletionServiceImpl(client)

    @Bean
    @ConditionalOnMissingBean
    open fun audioService(
        @Qualifier("OpenAIClient") client: WebClient
    ): AudioService = AudioServiceImpl(client)

    @Bean
    @ConditionalOnMissingBean
    open fun editService(
        @Qualifier("OpenAIClient") client: WebClient
    ): EditService = EditServiceImpl(client)

}