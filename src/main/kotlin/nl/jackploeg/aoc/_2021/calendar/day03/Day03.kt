package nl.jackploeg.aoc._2021.calendar.day03

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory

class Day03 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Int {
        return generatorFactory.forFile(filename).readAs(::day03) { input ->
            val summedBytes = mutableMapOf<Int, Int>()
            var numEntries = 0
            input.forEach { bytes ->
                bytes.keys.forEach { k ->
                    summedBytes[k] = (summedBytes[k] ?: 0) + (bytes[k] ?: 0)
                }
                numEntries++
            }
            val mostBytes = mutableMapOf<Int, Int>()
            val leastBytes = mutableMapOf<Int, Int>()
            summedBytes.forEach{ byte ->
                mostBytes[byte.key] = if (byte.value > numEntries/2) 1 else 0
                leastBytes[byte.key] = if (byte.value > numEntries/2) 0 else 1
            }
            val gammaRate = mostBytes.values.joinToString("").toInt(2)
            val epsilonRate = leastBytes.values.joinToString("").toInt(2)
            gammaRate * epsilonRate
        }
    }

    fun partTwo(filename: String) =
        generatorFactory.forFile(filename).readAs(::day03) { input ->
            -1
        }

    private fun day03(line: String): Map<Int, Int> =
        line.toCharArray().mapIndexed { index, c -> index to c.code - 48 }.toMap()


}
