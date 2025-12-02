package nl.jackploeg.aoc._2025.calendar.day02

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory

class Day02 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {

    fun partOne(filename: String): Long {
        val input = generatorFactory.forFile(filename).readOne(::day02)
        var total: Long = 0
        input.forEach {
            total += processRange(it)
        }
        return total
    }

    fun partTwo(filename: String): Long {
        val input = generatorFactory.forFile(filename).readOne(::day02)
        var total: Long = 0
        input.forEach {
            total += processRangeMulti(it)
        }
        return total
    }

    private fun day02(line: String): List<Pair<Long, Long>> {
        val ranges = line.split(',')
        val result = mutableListOf<Pair<Long, Long>>()
        ranges.forEach {
            val limits = it.split('-')
            result.add(Pair(limits[0].toLong(), limits[1].toLong()))
        }
        return result
    }

    private fun processRange(range: Pair<Long, Long>): Long {
        var total: Long = 0
        (range.first..range.second).forEach { productId ->
            val strId = productId.toString()
            if (strId.length % 2 == 0) {
                val half = strId.length / 2
                if (strId.substring(0, half) == strId.substring(half)) {
                    total += productId
                }
            }
        }
        return total
    }

    private fun processRangeMulti(range: Pair<Long, Long>): Long {
        var total: Long = 0
        (range.first..range.second).forEach productIdLoop@{ productId ->
            val strId = productId.toString()
            ((strId.length/2) downTo 1).forEach { subLength ->
                if (strId.length % subLength == 0) {
                    val part = strId.substring(0, subLength)
                    val repeated = part.repeat(strId.length/subLength)
                    if (repeated == strId) {
                        total += productId
                        println(productId)
                        return@productIdLoop
                    }
                }
            }
        }
        return total
    }

}
