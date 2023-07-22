package io.github.lfernando.openai.services.chat

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.annotation.JsonProperty
import io.github.lfernando.openai.UsageData

enum class Role(val value: String) {
    System("system"),
    User("user"),
    Assistant("assistant")
}

data class MessageData(
    @JsonProperty("role")
    val role: Role,
    @JsonProperty("content")
    val content: String
)
/**
 * CreateChatCompletionRequest.
 *
 * @param model            - ID of the model to use.
 *                         See the model endpoint compatibility table for details on which models work with the Chat API.
 * @param messages         - The messages to generate chat completions for, in the chat format.
 * @param temperature      - What sampling temperature to use, between 0 and 2.
 *                         Higher values like 0.8 will make the output more random,
 *                         while lower values like 0.2 will make it more focused and deterministic.
 *                         <p>
 *                         We generally recommend altering this or top_p but not both.
 * @param topP             - An alternative to sampling with temperature,
 *                         called nucleus sampling,
 *                         where the model considers the results of the tokens with top_p probability mass.
 *                         So 0.1 means only the tokens comprising the top 10% probability mass are considered.
 *                         <p>
 *                         We generally recommend altering this or temperature but not both.
 * @param n                - How many chat completion choices to generate for each input message.
 * @param stop             - Up to 4 sequences where the API will stop generating further tokens.
 * @param maxTokens        - The maximum number of tokens to generate in the chat completion.
 *                         <p>
 *                         The total length of input tokens and generated tokens
 *                         is limited by the model's context length.
 * @param presencePenalty  - Number between -2.0 and 2.0.
 *                         Positive values penalize new tokens based on whether they appear in the text so far,
 *                         increasing the model's likelihood to talk about new topics.
 * @param frequencyPenalty - Number between -2.0 and 2.0.
 *                         Positive values penalize new tokens based on their existing frequency in the text so far,
 *                         decreasing the model's likelihood to repeat the same line verbatim.
 * @param logitBias        - Modify the likelihood of specified tokens appearing in the completion.
 *                         <p>
 *                         Accepts a json object that maps tokens (specified by their token ID in the tokenizer)
 *                         to an associated bias value from -100 to 100. Mathematically,
 *                         the bias is added to the logits generated by the model prior to sampling.
 *                         The exact effect will vary per model,
 *                         but values between -1 and 1 should decrease or increase likelihood of selection;
 *                         values like -100 or 100 should result in a ban or exclusive selection of the relevant token.
 * @param user             - A unique identifier representing your end-user,
 *                         which can help OpenAI to monitor and detect abuse
 */
@JsonInclude(Include.NON_NULL)
class CreateChatCompletionRequest(
    @JsonProperty("model")
    val model: String,
    @JsonProperty("messages")
    val messages: List<MessageData>,
    @JsonProperty("temperature")
    val temperature: Double? = null,
    @JsonProperty("top_p")
    val topP: Double? = null,
    @JsonProperty("n")
    val n: Int? = null,
    @JsonProperty("stop")
    val stop: List<String>? = null,
    @JsonProperty("max_tokens")
    val maxTokens: Int? = null,
    @JsonProperty("presence_penalty")
    val presencePenalty: Double? = null,
    @JsonProperty("frequency_penalty")
    val frequencyPenalty: Double? = null,
    @JsonProperty("logit_bias")
    val logitBias: Map<String, Int>? = null,
    @JsonProperty("user")
    val user: String? = null
) {
    init {
        if (model.isBlank()) {
            throw IllegalArgumentException("model value can't be null or blank")
        }
        if (messages.isEmpty()) {
            throw IllegalArgumentException("messages can't be empty")
        }
    }
}

data class ChoiceData(
    @JsonProperty("index")
    val index: Int,
    @JsonProperty("message")
    val message: MessageData,
    @JsonProperty("finish_reason")
    val finishReason: String
)

data class CreateChatCompletionResponse(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("object")
    val `object`: String,
    @JsonProperty("created")
    val created: Long,
    @JsonProperty("model")
    val model: String,
    @JsonProperty("choices")
    val choices: List<ChoiceData>,
    @JsonProperty("usage")
    val usage: UsageData
)