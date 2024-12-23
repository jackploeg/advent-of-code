package nl.jackploeg.aoc._2024.calendar.day21

import arrow.core.memoize
import nl.jackploeg.aoc.generators.InputGenerator
import java.util.*
import javax.inject.Inject
import kotlin.math.sign

class Day21 @Inject constructor(
    private val generatorFactory: InputGenerator.InputGeneratorFactory,
) {
    fun partOne(filename: String) = generatorFactory.forFile(filename).read { input ->
        solve(input, indirections = 3)
    }

    fun partTwo(filename: String) = generatorFactory.forFile(filename).read { input ->
        solve(input, indirections = 26)
    }

    private fun solve(input: Sequence<String>, indirections: Int): Int {
        return input.sumOf { code ->
            val length = memoizedGetPathLength(NUMERIC_PAD, code, indirections)
            val size = code.take(3).toInt()

            length * size
        }
    }

    private val memoizedGetPathLength = ::getPathLength.memoize()

    private val memoizedPaths = ::paths.memoize()

    private fun getPathLength(keyPad: Map<Char, Key>, code: String, indirections: Int): Int {
        if (indirections == 0) {
            return code.length
        }

        val minLength = "A${code}".toList()
            .windowed(2)
            .sumOf { (start, end) ->
                memoizedPaths(keyPad, start, end).minOf { memoizedGetPathLength(DIRECTION_PAD, "${it}A", indirections - 1) }
            }

        return minLength
    }

    private fun paths(keyPad: Map<Char, Key>, start: Char, end: Char): List<String> {
        val paths = mutableListOf<String>()

        val partialPaths = LinkedList<Pair<Key, String>>().apply {
            add(keyPad[start]!! to "")
        }

        pathGeneration@ while (partialPaths.isNotEmpty()) {
            val (position, path) = partialPaths.poll()
            val target = keyPad[end]!!

            if (position == target) {
                paths.add(path)
                continue@pathGeneration
            }

            val (yDelta, xDelta) = target - position

            if (xDelta != 0) {
                val newPoint = position.first to position.second + xDelta.sign
                if (newPoint in keyPad.values) {
                    val dir = if (xDelta > 0) {
                        ">"
                    } else {
                        "<"
                    }
                    partialPaths.add(newPoint to "$path$dir")
                }
            }

            if (yDelta != 0) {
                val newPoint = position.first + yDelta.sign to position.second
                if (newPoint in keyPad.values) {
                    val dir = if (yDelta > 0) {
                        "v"
                    } else {
                        "^"
                    }
                    partialPaths.add(newPoint to "$path$dir")
                }
            }
        }

        return paths
    }

    private operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) =
        this.first - other.first to this.second - other.second

    companion object {
        val NUMERIC_PAD = mapOf(
            '7' to (0 to 0), '8' to (0 to 1), '9' to (0 to 2),
            '4' to (1 to 0), '5' to (1 to 1), '6' to (1 to 2),
            '1' to (2 to 0), '2' to (2 to 1), '3' to (2 to 2),
            '0' to (3 to 1), 'A' to (3 to 2),
        )

        val DIRECTION_PAD = mapOf(
            '^' to (0 to 1), 'A' to (0 to 2),
            '<' to (1 to 0), 'v' to (1 to 1), '>' to (1 to 2)
        )
    }
}

typealias Key = Pair<Int, Int>

