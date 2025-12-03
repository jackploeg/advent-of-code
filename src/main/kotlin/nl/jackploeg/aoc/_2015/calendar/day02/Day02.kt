package nl.jackploeg.aoc._2015.calendar.day02

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import javax.inject.Inject

class Day02 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Int =
        generatorFactory.forFile(filename).readAs(::day02) { input ->
            var total = 0
            input.forEach {
                val (l, w, h) = it
                val sides = listOf(l * w, w * h, h * l)
                total += sides.sum() * 2 + sides.min()
            }
            total
        }


    fun partTwo(filename: String): Int =
        generatorFactory.forFile(filename).readAs(::day02) { input ->
            var total = 0
            input.forEach {
                val (l, w, h) = it
                val sides = listOf(l + w, w + h, h + l)
                val bow = l * w * h
                total += sides.min() * 2 + bow
            }
            total
        }

    private fun day02(line: String): Triple<Int, Int, Int> =
        line.split('x').let { Triple(it[0].toInt(), it[1].toInt(), it[2].toInt()) }

}
