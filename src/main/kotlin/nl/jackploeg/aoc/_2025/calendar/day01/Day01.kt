package nl.jackploeg.aoc._2025.calendar.day01

import javax.inject.Inject
import kotlin.Int
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import kotlin.math.abs

class Day01 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Int {
        var pos = 50
        var onZero = 0
        generatorFactory.forFile(filename).readAs(::day01) { input ->
            input.forEach { i ->
                pos += when (i.first) {
                    'L' -> -i.second
                    else -> i.second
                }
                while (pos >= 100) {
                    pos -= 100
                }
                while (pos < 0) {
                    pos += 100
                }
                if (pos == 0) {
                    onZero++
                }
            }
        }
        return onZero
    }

    fun partTwo(filename: String): Int {
        var pos = 50
        var pastZero = 0
        generatorFactory.forFile(filename).readAs(::day01) { input ->

            var startingFromZero = false

            input.forEach { i ->
                val clicks = when (i.first) {
                    'L' -> -i.second
                    else -> i.second
                }

                pastZero += abs(clicks / 100)
                pos += clicks % 100

                if (pos >= 100) {
                    pastZero++
                    pos -= 100
                }

                if (pos < 0) {
                    if (!startingFromZero) {
                        pastZero++
                    }
                    pos += 100
                }

                if (pos == 0) {
                    if (i.first == 'L') {
                        pastZero++
                    }
                    startingFromZero = true
                } else {
                    startingFromZero = false
                }
            }
        }
        return pastZero
    }

    private fun day01(line: String): Pair<Char, Int> {
        return line[0] to line.drop(1).toInt()
    }
}
