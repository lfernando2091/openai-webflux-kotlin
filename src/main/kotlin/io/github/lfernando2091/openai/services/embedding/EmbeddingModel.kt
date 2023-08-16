package io.github.lfernando2091.openai.services.embedding

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonInclude.Include
import io.github.lfernando2091.openai.UsageData

enum class EmbeddingModel(val value: String) {
    TEXTEMBEDDING_ADA_002("text-embedding-ada-002"),
}
/**
 * CreateEmbeddingsRequest.
 *
 * @param model - ID of the model to use. You can use the List models API to see all of your available models,
 *              or see our Model overview for descriptions of them.
 * @param input - Input text to get embeddings for,
 *              encoded as a string or array of tokens.
 *              To get embeddings for multiple inputs in a single request,
 *              pass an array of strings or array of token arrays.
 *              Each input must not exceed 8192 tokens in length.
 * @param user  - A unique identifier representing your end-user,
 *              which can help OpenAI to monitor and detect abuse
 */
@JsonInclude(Include.NON_NULL)
data class CreateEmbeddingsRequest(
    @JsonProperty("model")
    val model: String,
    @JsonProperty("input")
    val input: List<String>,
    @JsonProperty("user")
    val user: String? = null
) {
    init {
        if (model.isBlank()) {
            throw IllegalArgumentException("model value can't be null or blank")
        }
        if (input.isEmpty()) {
            throw IllegalArgumentException("input can't be null or empty")
        }
    }
}

data class EmbeddingData(
    @JsonProperty("object")
    val `object`: String,
    @JsonProperty("embedding")
    val embedding: List<Double>,
    @JsonProperty("index")
    val index: Int
)

data class CreateEmbeddingsResponse(
    @JsonProperty("object")
    val `object`: String,
    @JsonProperty("data")
    val data: List<EmbeddingData>,
    @JsonProperty("model")
    val model: String,
    @JsonProperty("usage")
    val usage: UsageData
)