package io.github.lfernando2091.openai.services.audio

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * CreateTranscriptionRequest.
 *
 * @param file           - The audio file to transcribe,
 *                       in one of these formats: mp3, mp4, mpeg, mpga, m4a, wav, or webm.
 * @param model          - ID of the model to use. Only whisper-1 is currently available.
 * @param prompt         - An optional text to guide the model's style or continue a previous audio segment.
 *                       The prompt should match the audio language.
 * @param responseFormat - The format of the transcript output,
 *                       in one of these options: json, text, srt, verbose_json, or vtt.
 * @param temperature    - The sampling temperature, between 0 and 1.
 *                       Higher values like 0.8 will make the output more random,
 *                       while lower values like 0.2 will make it more focused and deterministic.
 *                       If set to 0, the model will use log probability to automatically increase
 *                       the temperature until certain thresholds are hit.
 * @param language       - The language of the input audio.
 *                       Supplying the input language in ISO-639-1 format will improve accuracy and latency.
 */
data class CreateTranscriptionRequest(
    val file: String,
    val model: String,
    val prompt: String? = null,
    val responseFormat: String? = null,
    val temperature: Double? = null,
    val language: String? = null
) {
    init {
        if (file.isBlank()) {
            throw IllegalArgumentException("file value can't be null or empty")
        }
        if (model.isBlank()) {
            throw IllegalArgumentException("model value can't be null or blank")
        }
    }
}

data class CreateTranscriptionResponse(
    @JsonProperty("text")
    val text: String
)

/**
 * CreateTranslationRequest.
 *
 * @param file           - The audio file to translate,
 *                       in one of these formats: mp3, mp4, mpeg, mpga, m4a, wav, or webm.
 * @param model          - ID of the model to use. Only whisper-1 is currently available.
 * @param prompt         - An optional text to guide the model's style or continue a previous audio segment.
 *                       The prompt should be in English.
 * @param responseFormat - The format of the transcript output,
 *                       in one of these options: json, text, srt, verbose_json, or vtt.
 * @param temperature    - The sampling temperature, between 0 and 1.
 *                       Higher values like 0.8 will make the output more random,
 *                       while lower values like 0.2 will make it more focused and deterministic.
 *                       If set to 0, the model will use log probability
 *                       to automatically increase the temperature until certain thresholds are hit.
 */
data class CreateTranslationRequest(
    val file: String,
    val model: String,
    val prompt: String? = null,
    val responseFormat: String? = null,
    val temperature: Double? = null
) {
    init {
        if (file.isBlank()) {
            throw IllegalArgumentException("file value can't be null or empty")
        }
        if (model.isBlank()) {
            throw IllegalArgumentException("model value can't be null or blank")
        }
    }
}

data class CreateTranslationResponse(
    @JsonProperty("text")
    val text: String
)