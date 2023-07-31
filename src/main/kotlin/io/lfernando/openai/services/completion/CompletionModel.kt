package io.lfernando.openai.services.completion

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.annotation.JsonInclude
import io.lfernando.openai.UsageData

enum class Models(val value: String) {
    BABBAGE("babbage"),
    DAVINCI("davinci"),
    ADA("ada"),
    TEXTCURIE_001("text-curie-001"),
    TEXTADA_001("text-ada-001"),
    TEXTDAVINCI_003("text-davinci-003"),
    TEXTDAVINCI_002("text-davinci-002"),
    CURIEINSTRUCT_BETA("curie-instruct-beta"),
    DAVINCIINSTRUCT_BETA("davinci-instruct-beta"),
    TEXTBABBAGE_001("text-babbage-001"),
    TEXTDAVINCI_001("text-davinci-001"),
    CURIE("curie")
}

/**
 * CreateCompletionRequest.
 *
 * @param model            - ID of the model to use.
 *                         You can use the List models API to see all of your available models,
 *                         or see our Model overview for descriptions of them.
 * @param prompt           - The prompt(s) to generate completions for,
 *                         encoded as a string, array of strings, array of tokens, or array of token arrays.
 *                         <p>
 *                         Note that {@literal <}|endoftext|{@literal >} is the document separator that the model sees during training,
 *                         so if a prompt is not specified the model will generate as if
 *                         from the beginning of a new document.
 * @param suffix           - The suffix that comes after a completion of inserted text.
 * @param maxTokens        - The maximum number of tokens to generate in the completion.
 *                         <p>
 *                         The token count of your prompt plus max_tokens cannot exceed the model's context length.
 *                         Most models have a context length of 2048 tokens
 *                         (except for the newest models, which support 4096).
 * @param temperature      - What sampling temperature to use, between 0 and 2.
 *                         Higher values like 0.8 will make the output more random,
 *                         while lower values like 0.2 will make it more focused and deterministic.
 *                         <p>
 *                         We generally recommend altering this or top_p but not both.
 * @param topP             - An alternative to sampling with temperature, called nucleus sampling,
 *                         where the model considers the results of the tokens with top_p probability mass.
 *                         So 0.1 means only the tokens comprising the top 10% probability mass are considered.
 *                         <p>
 *                         We generally recommend altering this or temperature but not both.
 * @param n                - How many completions to generate for each prompt.
 *                         <p>
 *                         Note: Because this parameter generates many completions,
 *                         it can quickly consume your token quota.
 *                         Use carefully and ensure that you have reasonable settings for max_tokens and stop.
 * @param logprobs         - Include the log probabilities on the logprobs most likely tokens,
 *                         as well the chosen tokens. For example, if logprobs is 5,
 *                         the API will return a list of the 5 most likely tokens.
 *                         The API will always return the logprob of the sampled token,
 *                         so there may be up to logprobs+1 elements in the response.
 *                         <p>
 *                         The maximum value for logprobs is 5. If you need more than this,
 *                         please contact us through our Help center and describe your use case.
 * @param echo             - Echo back the prompt in addition to the completion
 * @param stop             - Up to 4 sequences where the API will stop generating further tokens.
 *                         The returned text will not contain the stop sequence
 * @param presencePenalty  - Number between -2.0 and 2.0.
 *                         Positive values penalize new tokens based on whether they appear in the text so far,
 *                         increasing the model's likelihood to talk about new topics.
 * @param frequencyPenalty - Number between -2.0 and 2.0.
 *                         Positive values penalize new tokens based on their existing frequency in the text so far,
 *                         decreasing the model's likelihood to repeat the same line verbatim.
 * @param bestOf           - Generates best_of completions server-side and returns the "best"
 *                         (the one with the highest log probability per token). Results cannot be streamed.
 *                         <p>
 *                         When used with n, best_of controls
 *                         the number of candidate completions
 *                         and n specifies how many to return – best_of must be greater than n.
 *                         <p>
 *                         Note: Because this parameter generates many completions,
 *                         it can quickly consume your token quota. Use carefully and ensure
 *                         that you have reasonable settings for max_tokens and stop.
 * @param logitBias        - Modify the likelihood of specified tokens appearing in the completion.
 *                         <p>
 *                         Accepts a json object that maps tokens
 *                         (specified by their token ID in the GPT tokenizer)
 *                         to an associated bias value from -100 to 100.
 *                         Mathematically, the bias is added to the logits generated by the model prior to sampling.
 *                         The exact effect will vary per model,
 *                         but values between -1 and 1 should decrease or increase likelihood of selection;
 *                         values like -100 or 100 should result in a ban or exclusive selection of the relevant token.
 *                         <p>
 *                         As an example,
 *                         you can pass {"50256": -100} to prevent the {@literal <}|endoftext|{@literal >} token from being generated.
 * @param user             - A unique identifier representing your end-user,
 *                         which can help OpenAI to monitor and detect abuse.
 */
@JsonInclude(Include.NON_NULL)
data class CreateCompletionRequest(
    @JsonProperty("model")
    val model: String,
    @JsonProperty("prompt")
    val prompt: List<List<String>>,
    @JsonProperty("suffix")
    val suffix: String? = null,
    @JsonProperty("max_tokens")
    val maxTokens: Int? = null,
    @JsonProperty("temperature")
    val temperature: Double? = null,
    @JsonProperty("top_p")
    val topP: Double? = null,
    @JsonProperty("n")
    val n: Int? = null,
    @JsonProperty("logprobs")
    val logprobs: Int? = null,
    @JsonProperty("echo")
    val echo: Boolean? = null,
    @JsonProperty("stop")
    val stop: List<String>? = null,
    @JsonProperty("presence_penalty")
    val presencePenalty: Double? = null,
    @JsonProperty("frequency_penalty")
    val frequencyPenalty: Double? = null,
    @JsonProperty("best_of")
    val bestOf: Int? = null,
    @JsonProperty("logit_bias")
    val logitBias: Map<String, Int>? = null,
    @JsonProperty("user")
    val user: String? = null
) {
    init {
        if (model.isBlank()) {
            throw IllegalArgumentException("model value can't be null or blank")
        }
    }
}

data class Choice(
    @JsonProperty("text")
    val text: String,
    @JsonProperty("index")
    val index: Int,
    @JsonProperty("logprobs")
    val logprobs: Logprobs,
    @JsonProperty("finish_reason")
    val finishReason: String
)

data class Logprobs(
    @JsonProperty("tokens")
    val tokens: List<String>,
    @JsonProperty("token_logprobs")
    val tokenLogprobs: List<Double>,
    @JsonProperty("top_logprobs")
    val topLogprobs: List<Map<String, Double>>,
    @JsonProperty("text_offset")
    val textOffset: List<Int>
)

data class CreateCompletionResponse(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("object")
    val `object`: String,
    @JsonProperty("created")
    val created: Long,
    @JsonProperty("model")
    val model: String,
    @JsonProperty("choices")
    val choices: List<Choice>,
    @JsonProperty("usage")
    val usage: UsageData
)