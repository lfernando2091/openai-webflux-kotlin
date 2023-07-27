package io.lfernando.openai

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "openai")
class ClientAutoConfigurationProperties(
    /**
     * OpenAI Api Key
     */
    val apiKey: String,
    val baseUrl: String = "https://api.openai.com/v1",
    val organizationId: String? = null,
)