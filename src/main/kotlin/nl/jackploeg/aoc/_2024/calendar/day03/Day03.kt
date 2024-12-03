package nl.jackploeg.aoc._2024.calendar.day03

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import java.io.File
import javax.inject.Inject

class Day03 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Int {
        val multiplications: MutableList<Multiplication> = mutableListOf()
        generatorFactory.forFile(filename).readAs(::day03) { input ->
            input.forEach {
                it.second.map {
                    multiplications.add(Multiplication(it.x, it.y))
                }
            }
        }
        return multiplications.sumOf { it.result() }
    }

    fun partTwo(filename: String): Int {
        val file = File(filename)
        val input = file.readLines().joinToString(separator = "")
        val multiplications: MutableList<Multiplication> = findMultiplications(input)
        return multiplications.sumOf { it.result() }
    }

    private fun findMultiplications(input: String): MutableList<Multiplication> {
        val multiplications: MutableList<Multiplication> = mutableListOf()
        var scanning = true
        var addMultiplications = true
        var pos = 0
        while (scanning) {
            if (addMultiplications) {
                val nextMultiplicationOrStop: Pair<Int?, Int?>? =
                    findMultiplicationOrStop(input.substring(pos, input.length))
                nextMultiplicationOrStop?.let {
                    if (it.second != null) {
                        addMultiplications = false
                        pos += it.second!!
                    } else if (it.first != null) {
                        val s = input.substring(pos + it.first!!, input.indexOf(")", pos + it.first!!))
                        val first = s.substring(4, s.indexOf(','))
                        val second = s.substring(s.indexOf(',') + 1, s.length)
                        multiplications.add(Multiplication(first.toInt(), second.toInt()))
                        pos += it.first!! + 5
                    } else {
                        scanning = false
                    }
                }
                if (nextMultiplicationOrStop == null) {
                    scanning = false
                }
            } else {
                val startIndex = input.indexOf("do()", pos)
                if (startIndex == -1) {
                    scanning = false
                } else {
                    pos = startIndex + 4
                    addMultiplications = true
                }
            }
        }

        return multiplications
    }

    private fun findMultiplicationOrStop(input: String): Pair<Int?, Int?>? {
        var mulIndex: Int? = null
        val mulRegex: Regex = "mul\\(\\d+,\\d+\\)".toRegex()
        val matchResult = mulRegex.find(input)
        matchResult?.let {
            mulIndex = it.range.first
        }
        val stopIndex = input.indexOf("don't()")
        if (stopIndex == -1) {
            if (mulIndex == null) {
                return null
            }
            return Pair(mulIndex, null)
        }
        if (mulIndex == null) {
            return Pair(null, stopIndex)
        }
        if (stopIndex < mulIndex!!) {
            return Pair(null, stopIndex)
        }
        return Pair(mulIndex, null)
    }

    private fun day03(line: String): Pair<Int, List<Multiplication>> {
        val mulRegex: Regex = "mul\\(\\d+,\\d+\\)".toRegex()
        val matches = mulRegex.findAll(line)
        val result: MutableList<Multiplication> = mutableListOf()
        matches.forEach { match ->
            match.groups.forEach { group ->
                val s = group?.value
                s?.let {
                    val first = s.substring(4, s.indexOf(','))
                    val second = s.substring(s.indexOf(',') + 1, s.length - 1)
                    result.add(Multiplication(first.toInt(), second.toInt()))
                }
            }
        }
        return Pair(line.length, result)
    }

    data class Multiplication(val x: Int, val y: Int) {
        fun result() = x * y
    }
}
