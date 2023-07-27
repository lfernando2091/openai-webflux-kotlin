package io.lfernando.openai

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "openai")
data class ClientAutoConfigurationProperties(
    /**
     * OpenAI Api Key
     */
    var apiKey: String = "",
    var baseUrl: String = "https://api.openai.com/v1",
    var organizationId: String? = null,
)