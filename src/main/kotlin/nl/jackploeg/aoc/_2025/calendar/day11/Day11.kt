package nl.jackploeg.aoc._2025.calendar.day11

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.pathing.countPaths
import javax.inject.Inject

class Day11 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Long =
        countPaths( generatorFactory.forFile(filename).readAs(::day11) { input ->
            input.toMap()
        }, listOf("you", "out"), 30)

    fun partTwo(filename: String): Long {
        val nodes = generatorFactory.forFile(filename).readAs(::day11) { input ->
            input.toMap()
        }
        val pathCount1 = countPaths(nodes, listOf("svr", "fft", "dac", "out"), 50)
        val pathCount2 = countPaths(nodes, listOf("svr", "dac", "fft", "out"), 50)
        return pathCount1 + pathCount2
    }

    private fun day11(line: String): Pair<String, List<String>> {
        val first = line.split(": ")
        val second = first[1].split(" ")
        return first[0] to second
    }
}
