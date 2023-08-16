package io.github.lfernando2091.openai.utils

import io.github.lfernando2091.openai.services.embedding.EmbeddingData
import java.math.RoundingMode
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.text.DecimalFormat

/**
 * Compute the similarity of two embeddings using [cosine similarity](https://en.wikipedia.org/wiki/Cosine_similarity).
 */
fun EmbeddingData.similarity(other: List<Double>): Double = Cosine.similarity(embedding, other)

/**
 * Calculate the distance between two embeddings, corresponding to 1.0 - similarity.
 */
fun EmbeddingData.distance(other: List<Double>): Double = Cosine.distance(embedding, other)

object Utils {
    val FLOAT_FORMAT = DecimalFormat("#.########")
    fun toFloatByteArray(list: List<Double>, order: ByteOrder = ByteOrder.LITTLE_ENDIAN): ByteArray {
        FLOAT_FORMAT.roundingMode = RoundingMode.HALF_UP
        val result = ByteArray(list.size * 4)
        for ((index, value) in list.withIndex()) {
            val floatValue = FLOAT_FORMAT.format(value).toFloat()
            ByteBuffer.wrap(result, index * 4, 4)
                .order(order)
                .putFloat(floatValue)
        }
        return result
    }
    fun toFloatOriginalByteArray(list: List<Double>, order: ByteOrder = ByteOrder.LITTLE_ENDIAN): ByteArray {
        val result = ByteArray(list.size * 4)
        for ((index, value) in list.withIndex()) {
            val floatValue = value.toFloat()
            ByteBuffer.wrap(result, index * 4, 4)
                .order(order)
                .putFloat(floatValue)
        }
        return result
    }
    fun toByteArray(list: List<Double>, order: ByteOrder = ByteOrder.LITTLE_ENDIAN): ByteArray {
        val result = ByteArray(list.size * 8)
        for ((index, value) in list.withIndex()) {
            ByteBuffer.wrap(result, index * 8, 8)
                .order(order)
                .putDouble(value)
        }
        return result
    }
    fun toFloatNormalized(list: List<Double>): List<Float> {
        FLOAT_FORMAT.roundingMode = RoundingMode.HALF_UP
        return list.map { FLOAT_FORMAT.format(it).toFloat() }
    }
}