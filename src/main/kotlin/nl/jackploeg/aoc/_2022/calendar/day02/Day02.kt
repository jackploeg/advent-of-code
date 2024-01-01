package nl.jackploeg.aoc._2022.calendar.day02

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile

class Day02 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(fileName: String): Int {
        val moves = readStrategy(fileName)
        return moves.map { calculateScore1(it) }
            .reduce { sum, score -> sum + score}
    }

    fun partTwo(fileName: String): Int {
        val moves = readStrategy(fileName)
        return moves.map { calculateScore2(it) }
            .reduce { sum, score -> sum + score}
    }

    fun readStrategy(fileName: String): List<Pair<Char, Char>> {
        val moves: MutableList<Pair<Char, Char>> = mutableListOf()
        val pairs = readStringFile(fileName)
        for (pair in pairs) {
            moves.add(Pair(pair[0], pair[2]))
        }
        return moves
    }

    fun calculateScore1(move: Pair<Char, Char>): Int {
        val (a, b) = move
        var score = when (a) {
            'A' ->
                when (b) {
                    'X' -> 3
                    'Y' -> 6
                    'Z' -> 0
                    else -> -1
                }
            'B' ->
                when (b) {
                    'X' -> 0
                    'Y' -> 3
                    'Z' -> 6
                    else -> -1
                }
            'C' ->
                when (b) {
                    'X' -> 6
                    'Y' -> 0
                    'Z' -> 3
                    else -> -1
                }
            else -> -1
        }

        score += when (b) {
            'X' -> 1
            'Y' -> 2
            'Z' -> 3
            else -> -1
        }

        return score
    }

    fun calculateScore2(move: Pair<Char, Char>): Int {
        val (a, b) = move
        // a = rock, b = paper, c = scissors
        // x = lose, y = draw, z = win
        // 1 = rock, 2 = paper, 3 = scissors
        val myMove = when (a) {
            'A' ->
                when (b) {
                    'X' -> 3
                    'Y' -> 1
                    'Z' -> 2
                    else -> -1
                }
            'B' ->
                when (b) {
                    'X' -> 1
                    'Y' -> 2
                    'Z' -> 3
                    else -> -1
                }
            'C' ->
                when (b) {
                    'X' -> 2
                    'Y' -> 3
                    'Z' -> 1
                    else -> -1
                }
            else -> -1
        }

        val score = myMove + when (b) {
            'X' -> 0
            'Y' -> 3
            'Z' -> 6
            else -> -1
        }

        return score
    }
}
