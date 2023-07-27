package io.lfernando.openai.services.edit

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import io.lfernando.openai.UsageData

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreateEditRequest(
    @JsonProperty("model")
    val model: String,
    @JsonProperty("instruction")
    val instruction: String,
    @JsonProperty("input")
    val input: String? = null,
    @JsonProperty("n")
    val n: String? = null,
    @JsonProperty("temperature")
    val temperature: Double? = null,
    @JsonProperty("top_p")
    val topP: String? = null
) {
    init {
        if (instruction.isBlank()) {
            throw IllegalArgumentException("model value can't be null or blank")
        }
        if (model.isBlank()) {
            throw IllegalArgumentException("model value can't be null or blank")
        }
    }
}

data class CreateEditResponse(
    @JsonProperty("object")
    val `object`: String,
    @JsonProperty("created")
    val created: Long,
    @JsonProperty("choices")
    val choices: List<ChoiceData>,
    @JsonProperty("usage")
    val usage: UsageData
)

data class ChoiceData(
    @JsonProperty("text")
    val text: String,
    @JsonProperty("index")
    val index: Int
)