package nl.jackploeg.aoc._2025.calendar.day06

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.extractLongs
import javax.inject.Inject

class Day06 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Long {
        val problems: List<Pair<List<Char>, List<Long>>> = readProblemsToLongs(filename)
        return problems.sumOf { problem ->
            if (problem.first.first() == '*') {
                problem.second.reduce(Long::times)
            } else {
                problem.second.reduce(Long::plus)
            }
        }
    }

    fun partTwo(filename: String): Long {
        val problems: Pair<List<Char>, List<List<String>>> = readProblemsToStrings(filename)
        return problems.second.mapIndexed { index, numbers ->
            val conversed = (0..numbers.maxBy { it.length }.length - 1)
                .map { i ->
                    numbers.fold("") { acc, v -> acc + v[i] }.trim().toLong()
                }
            if (problems.first[index] == '*') {
                conversed.reduce(Long::times)
            } else {
                conversed.reduce(Long::plus)
            }
        }.sum()
    }


    fun readProblemsToLongs(filename: String): List<Pair<List<Char>, List<Long>>> {
        val problems = mutableListOf<Pair<MutableList<Char>, MutableList<Long>>>()
        generatorFactory.forFile(filename).read { input ->
            input.forEachIndexed { index, line ->
                if (line.indexOf('*') > -1) {
                    val operators = line.replace("\\s+".toRegex(), " ").split(" ")
                    operators.forEachIndexed { i, char ->
                        problems[i].first.add(char.first())
                    }
                } else {
                    line.extractLongs().forEachIndexed { i, value ->
                        if (problems.size <= i) {
                            problems.add(Pair(mutableListOf(), mutableListOf(value)))
                        } else {
                            problems[i].second.add(value)
                        }
                    }
                }
            }
        }
        return problems
    }

    fun readProblemsToStrings(filename: String): Pair<List<Char>, List<List<String>>> {
        val starts = mutableListOf<Int>()
        val operators = mutableListOf<Char>()
        val numbers = mutableListOf<MutableList<String>>()
        var longestLine = 0
        // read operators and start positions of numbers
        generatorFactory.forFile(filename).read { input ->
            input.forEachIndexed { index, line ->
                if (line.length > longestLine) {
                    longestLine = line.length
                }
                if (line.indexOf('*') > -1) {
                    var pos = 0
                    while (pos < line.length - 1) {
                        starts.add(pos)
                        operators.add(line[pos])
                        pos++
                        while (line[pos] == ' ' && pos < line.length - 1) {
                            pos++
                        }
                    }
                    if (line[pos] != ' ') {
                        starts.add(pos)
                        operators.add(line[pos])
                    }
                }
            }
        }
        // read numbers
        generatorFactory.forFile(filename).read { input ->
            input.forEachIndexed { index, line ->
                if (line.indexOf('*') == -1) {
                    val paddedLine = line.padEnd(longestLine)
                    (0..starts.size - 1).forEach { i ->
                        val number = if (i == starts.size - 1) {
                            paddedLine.substring(starts[i])
                        } else {
                            paddedLine.substring(starts[i], starts[i + 1] - 1)
                        }
                        if (numbers.size <= i) {
                            numbers.add(mutableListOf(number))
                        } else {
                            numbers[i].add(number)
                        }
                    }
                }
            }
        }
        return Pair(operators, numbers)
    }
}
