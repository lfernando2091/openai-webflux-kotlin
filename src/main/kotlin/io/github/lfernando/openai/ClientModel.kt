package io.github.lfernando.openai

import com.fasterxml.jackson.annotation.JsonProperty

data class UsageData(
    @JsonProperty("prompt_tokens")
    val promptTokens: Int,
    @JsonProperty("completion_tokens")
    val completionTokens: Int,
    @JsonProperty("total_tokens")
    val totalTokens: Int
)