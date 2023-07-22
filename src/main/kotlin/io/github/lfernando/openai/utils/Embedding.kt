package io.github.lfernando.openai.utils

import io.github.lfernando.openai.services.embedding.EmbeddingData
import java.math.RoundingMode
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.text.DecimalFormat

val FLOAT_FORMAT = DecimalFormat("#.########")
/**
 * Compute the similarity of two embeddings using [cosine similarity](https://en.wikipedia.org/wiki/Cosine_similarity).
 */
fun EmbeddingData.similarity(other: List<Double>): Double = Cosine.similarity(embedding, other)

/**
 * Calculate the distance between two embeddings, corresponding to 1.0 - similarity.
 */
fun EmbeddingData.distance(other: List<Double>): Double = Cosine.distance(embedding, other)
fun EmbeddingData.toByteArray(): ByteArray {
    val result = ByteArray(embedding.size * 8)
    for ((index, value) in embedding.withIndex()) {
        ByteBuffer.wrap(result, index * 8, 8)
            .order(ByteOrder.LITTLE_ENDIAN)
            .putDouble(value)
    }
    return result
}
fun EmbeddingData.toFloatByteArray(): ByteArray {
    FLOAT_FORMAT.roundingMode = RoundingMode.HALF_UP
    val result = ByteArray(embedding.size * 4)
    for ((index, value) in embedding.withIndex()) {
        val floatValue = FLOAT_FORMAT.format(value).toFloat()
        ByteBuffer.wrap(result, index * 4, 4)
            .order(ByteOrder.LITTLE_ENDIAN)
            .putFloat(floatValue)
    }
    return result
}
fun EmbeddingData.toFloatNormalized(): List<Float> {
    FLOAT_FORMAT.roundingMode = RoundingMode.HALF_UP
    return embedding.map { FLOAT_FORMAT.format(it).toFloat() }
}