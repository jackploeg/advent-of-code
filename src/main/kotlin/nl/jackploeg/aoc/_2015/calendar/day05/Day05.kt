package nl.jackploeg.aoc._2015.calendar.day05

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import javax.inject.Inject

class Day05 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Int =
        generatorFactory.forFile(filename).readAs(::isNice1) { input ->
            input.count { it }
        }

    fun partTwo(filename: String): Int =
        generatorFactory.forFile(filename).readAs(::isNice2) { input ->
            input.count { it }
        }

    private fun isNice1(line: String): Boolean {
        var isNice = line.asSequence().count { char -> charArrayOf('a', 'e', 'i', 'o', 'u').contains(char) } >= 3

        if (!isNice) {
            return false
        }
        isNice = false
        var previousChar = '@'
        line.asSequence().forEach { char ->
            if (char == previousChar) {
                isNice = true
            } else {
                previousChar = char
            }
        }
        if (!isNice) {
            return false
        }
        return line.indexOf("ab") == -1 &&
                line.indexOf("cd") == -1 &&
                line.indexOf("pq") == -1 &&
                line.indexOf("xy") == -1
    }

    private fun isNice2(line: String): Boolean {
        val pairs: MutableList<String> = mutableListOf()
        var i = 0
        while (i < line.length -1) {
            pairs.add("${line[i]}${line[i + 1]}")
            if (line[i] == line[i + 1] && (i < line.length -2 && line[i + 1] == line[i + 2])) {
                i+=2
            } else {
                i++
            }
        }
        val pairCount = pairs.groupBy { it }

        if (pairCount.none { it.value.size > 1 }) {
            return false
        }
        i = 0
        while (i < line.length -2) {
            if (line[i] == line[i + 2]) {
                return true
            }
            i++
        }
        return false
    }

}
