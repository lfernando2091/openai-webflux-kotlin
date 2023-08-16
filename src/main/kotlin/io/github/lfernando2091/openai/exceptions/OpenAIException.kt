package io.github.lfernando2091.openai.exceptions

import org.springframework.web.reactive.function.client.WebClientException

class OpenAIException(
    statusCode: Int,
    errorMessage: String
): WebClientException(String.format("""
    OpenAi Service exception occurred.
    HTTP error code: %s
    Detailed error message:
    %s
""".trimIndent(), statusCode, errorMessage))