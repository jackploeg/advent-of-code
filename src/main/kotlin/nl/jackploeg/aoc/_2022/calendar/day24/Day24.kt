package nl.jackploeg.aoc._2022.calendar.day24

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import java.util.*

data class State(val y: Int, val x: Int, val t: Int, val reachEnd: Boolean, val reachStart: Boolean)

class Day24 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(filename: String, maxTime: Int) =calculatePath(maxTime, filename)

  fun partTwo(filename: String, maxTime: Int) = calculatePath(maxTime, filename, false)

    fun calculatePath(maxTime: Int, fileName: String, stopAfterPartOne: Boolean = true): Int {

        val input = readStringFile(fileName)
        val (rows, columns) = input.size - 2 to input[0].length - 2
        // pre-fill maxTime consecutive grid states
        val blizzardCells = (0..maxTime).associateWith { time ->
            buildSet {
                for (row in 0 until rows) {
                    for (column in 0 until columns) {
                        when (input[row + 1][column + 1]) {
                            '>' -> add(row to (column + time) % columns)
                            'v' -> add((row + time) % rows to column)
                            '<' -> add(row to (column - time).mod(columns))
                            '^' -> add((row - time).mod(rows) to column)
                        }
                    }
                }
            }
        }
        val stateQueue: Queue<State> = LinkedList()
        val seen = mutableSetOf<State>()
        stateQueue.add(State(y = 0, x = 1, t = 0, reachEnd = false, reachStart = false))
        var part1complete = false
        while (stateQueue.isNotEmpty()) {
            val state = stateQueue.poll()
            var (y, x, time, reachedEnd, reachedStart) = state
            if (y !in input.indices || x !in input[0].indices || input[y][x] == '#')
                continue
            if (y == input.lastIndex) {
                reachedEnd = true
                if (reachedStart) {
                    println("Part 2: $time")
                    return time
                } else if (!part1complete) {
                    println("Part 1: $time")
                    if (stopAfterPartOne)
                        return time
                }
                part1complete = true
            }
            if (y == 0 && reachedEnd)
                reachedStart = true
            if (state in seen)
                continue
            seen += state
            listOf(0 to 0, 0 to 1, 0 to -1, 1 to 0, -1 to 0).forEach { (dy, dx) ->
                if (y - 1 + dy to x - 1 + dx !in blizzardCells[time + 1]!!)
                    stateQueue.add(State(y + dy, x + dx, time + 1, reachedEnd, reachedStart))
            }
        }
        return -1
    }

}
