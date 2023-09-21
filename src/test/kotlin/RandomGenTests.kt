import com.random_gen_lib.RandomGen
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals
import java.lang.IllegalArgumentException
import kotlin.math.roundToInt

class RandomGenTests {

    @Test
    fun `should generate next number according to probabilities`() {
        //Given
        val numbers = listOf(-1, 0, 1, 2, 3)
        val probabilities = listOf(0.01F, 0.3F, 0.58F, 0.1F, 0.01F).toFloatArray()
        val random = RandomGen(numbers.toIntArray(), probabilities)
        val result: MutableMap<Int, Int> = mutableMapOf()

        val repeats = 100_000

        //When
        for (i in 1..repeats) {
            val num = random.nextNum()

            if (result.containsKey(num)) {
                result[num] = result[num]!! + 1
            } else {
                result[num] = 1
            }
        }

        //Then
        for (i in numbers.indices) {
            val actual = getProbability(numbers[i], result[numbers[i]] ?: 0, repeats, probabilities[i])

            assertEquals(
                probabilities[i],
                actual
            )
        }
    }

    @Test
    fun `should throw error if randomNumbers is not distinct`() {
        //Given
        val numbers = listOf(1, 1, 0, 2, 3)
        val probabilities = listOf(0.01F, 0.3F, 0.58F, 0.1F, 0.01F).toFloatArray()

        val expected = IllegalArgumentException("The randomNumbers should have unique elements")

        //When
        val actual = assertThrows<IllegalArgumentException> {
            RandomGen(numbers.toIntArray(), probabilities)
        }

        //Then
        assertEquals(expected.message, actual.message)
    }

    @Test
    fun `should throw error if randomNumbers size is not equal to probabilities size`() {
        //Given
        val numbers = listOf(1, -1, 0, 2)
        val probabilities = listOf(0.01F, 0.3F, 0.58F, 0.1F, 0.01F).toFloatArray()

        val expected = IllegalArgumentException("The randomNumbers size must equal to probabilities size")

        //When
        val actual = assertThrows<IllegalArgumentException> {
            RandomGen(numbers.toIntArray(), probabilities)
        }

        //Then
        assertEquals(expected.message, actual.message)
    }

    @Test
    fun `should throw error if probabilities sum is not 1`() {
        //Given
        val numbers = listOf(1, -1, 0, 2, 3)
        val probabilities = listOf(0.01F, 0.5F, 0.58F, 0.1F, 0.01F).toFloatArray()

        val expected = IllegalArgumentException("The probabilities sum must equal 1")

        //When
        val actual = assertThrows<IllegalArgumentException> {
            RandomGen(numbers.toIntArray(), probabilities)
        }

        //Then
        assertEquals(expected.message, actual.message)
    }

    private fun getProbability(number: Int, amount: Int, repeats: Int, expected: Float): Float {
        val probability = amount.toFloat() / repeats.toFloat()

        println("Probability for $number is ${round(probability)} and is expected ${round(expected)}")

        return round(probability)
    }

    private fun round(number: Float) = ((number * 100.0).roundToInt() / 100.0).toFloat()
}