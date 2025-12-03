package nl.jackploeg.aoc._2025.calendar.day03

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import javax.inject.Inject
import kotlin.math.pow

class Day03 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Long =
        generatorFactory.forFile(filename).readAs(::day03) { input ->
            var sumOfJoltage = 0L
            input.forEach { bank ->
                var max1 = 0L
                var max1Pos = 0
                var max2 = 0L
                bank.forEachIndexed { i, v ->
                    if (i < (bank.size - 1) && v > max1) {
                        max1 = v
                        max1Pos = i
                    }
                }
                bank.forEachIndexed({ i, j ->
                    if (i > max1Pos && j > max2) {
                        max2 = j
                    }
                })

                sumOfJoltage += max1 * 10 + max2
            }
            sumOfJoltage
        }

    fun partTwo(filename: String): Long =
        generatorFactory.forFile(filename).readAs(::day03) { input ->
            var sumOfJoltage = 0L
            input.forEach { bank ->
                val maxes = longArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                val maxesPos = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                (0..11).forEach { maxtry ->
                    bank.forEachIndexed { i, v ->
                        if (i < (bank.size - (11-maxtry)) && (maxtry==0 || i > maxesPos[maxtry-1]) && v > maxes[maxtry]) {
                            maxes[maxtry] = v
                            maxesPos[maxtry] = i
                        }
                    }
                }
                var thisMax = 0L
                maxes.forEachIndexed { i, v -> thisMax += v *(10L `**` (11-i)) }
                sumOfJoltage += thisMax
            }
            sumOfJoltage
        }

    private fun day03(line: String): LongArray =
        line.toCharArray().map { it.digitToInt().toLong() }.toLongArray()

    infix fun Long.`**`(exponent: Int): Long = toDouble().pow(exponent).toLong()

}
