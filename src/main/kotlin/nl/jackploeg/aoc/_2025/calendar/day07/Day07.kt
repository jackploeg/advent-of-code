package nl.jackploeg.aoc._2025.calendar.day07

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.grid.*
import nl.jackploeg.aoc.utilities.readStringFile
import javax.inject.Inject

class Day07 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {

    fun partOne(filename: String): Int {
        val grid = CharacterGrid.fromFile(filename)
        val start = grid.grid.filter { it.value == 'S' }.keys.first()
        val queue = ArrayDeque<Cell>()
        val splitsHandled = mutableListOf<Cell>()
        queue.add(start)
        while (!queue.isEmpty()) {
            var cell = queue.removeFirst()
            while (cell.row < grid.getHeight() && cell.south() in grid.grid && grid.grid[cell.south()] == '.') {
                cell = cell.south()
            }
            if (cell.south() in grid.grid && grid.grid[cell.south()] == '^' && !splitsHandled.contains(cell.south())) {
                splitsHandled.add(cell.south())
                queue.add(cell.southWest())
                queue.add(cell.southEast())
            }
        }
        return splitsHandled.size
    }

    fun partTwo(filename: String): Long {
        val lines = readStringFile(filename)
        val size = lines.last().length
        var timeLines = List(size) { 1L }

        /*
        Calculate how many paths lead down from every position in a line.
        Start at the bottom where every position has only one path down, then calculate for the
        row above it. Every dot has the same number of paths as the position below it, every ^ has the
        sum of the paths to its southwest and southeast.
         */
        lines.reversed().forEach { line ->
            timeLines = List(size) { i ->
                if (line[i] == '^') timeLines.getOrElse(i - 1) { 0 } + timeLines.getOrElse(i + 1) { 0 }
                else timeLines[i]
            }
            println(timeLines)
        }

        return timeLines[lines.first().indexOf('S')]
    }
}
