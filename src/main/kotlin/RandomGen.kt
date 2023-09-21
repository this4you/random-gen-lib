package com.random_gen_lib

import kotlin.random.Random

class RandomGen(
    /** Values that may be returned by nextNum() */
    private val randomNumbers: IntArray,
    /** Probability of the occurence of randomNumbers */
    private val probabilities: FloatArray
) {
    init {
        require(randomNumbers.distinct().size == randomNumbers.size) {
            "The randomNumbers should have unique elements"
        }

        require(randomNumbers.size == probabilities.size) {
            "The randomNumbers size must equal to probabilities size"
        }

        require(probabilities.sum().equals(1.toFloat())) {
            "The probabilities sum must equal 1"
        }
    }

    /**
    Returns one of the randomNumbers. When this method is called
    multiple times over a long period, it should return the
    numbers roughly with the initialized probabilities.
     */
    fun nextNum(): Int {
        val randomNumber = Random.nextFloat()

        var probability = 0.toFloat()
        var result = 0

        for (i in 0..randomNumbers.size) {
            probability += probabilities[i]

            if (probability > randomNumber || i == (randomNumbers.size -1)) {
                result = randomNumbers[i]

                break
            }
        }

        return result
    }
}
