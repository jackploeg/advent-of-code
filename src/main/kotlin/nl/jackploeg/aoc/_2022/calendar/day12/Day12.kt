package nl.jackploeg.aoc._2022.calendar.day12

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile

class Day12 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String) = shortestPath(filename)

    fun partTwo(filename: String) = shortestPath(filename, true)

    fun shortestPath(fileName: String, stopAtA: Boolean = false): Int {
        val input = readStringFile(fileName)

        val grid: ArrayList<ArrayList<Cell>> = ArrayList()
        var start = Cell(-1, -1, '@', -1)
        var end = Cell(-1, -1, '@', -1)

        var gridWidth = 0

        fun checkCell(y: Int, x: Int, compareCell: Cell, deque: ArrayDeque<Cell>) {
            if (x in 0 until gridWidth && y in 0 until grid.size) {
                val cell = grid[y][x]
                if (cell.distanceToEnd == -1
                    && (cell.level >= compareCell.level - 1)
                ) {
                    cell.distanceToEnd = compareCell.distanceToEnd + 1
                    deque.add(cell)
                }
            }
        }


        // init grid
        for ((row, line) in input.withIndex()) {
            if (gridWidth == 0)
                gridWidth = line.length
            val cells = line.toCharArray()
            val lineCells: ArrayList<Cell> = ArrayList()
            for ((index, char) in cells.withIndex()) {
                when (char) {
                    'S' -> {
                        val cell = Cell(index, row, 'a', -1)
                        start = cell
                        lineCells.add(cell)
                    }

                    'E' -> {
                        val cell = Cell(index, row, 'z', -1)
                        end = cell
                        lineCells.add(cell)
                    }

                    else -> {
                        val cell = Cell(index, row, char, -1)
                        lineCells.add(cell)
                    }
                }
            }
            grid.add(lineCells)
        }

        // calc distanceToEnd to end
        val visitedCells: ArrayDeque<Cell> = ArrayDeque()
        visitedCells.add(end)
        end.distanceToEnd = 0
        var pathLength = -1

        while (!visitedCells.isEmpty()) {
            val cell = visitedCells.removeFirst()
            if (cell == start || (cell.level == 'a' && stopAtA)) {
                pathLength = cell.distanceToEnd
                break
            }
            checkCell(cell.y - 1, cell.x, cell, visitedCells)
            checkCell(cell.y + 1, cell.x, cell, visitedCells)
            checkCell(cell.y, cell.x - 1, cell, visitedCells)
            checkCell(cell.y, cell.x + 1, cell, visitedCells)
        }

        return pathLength
    }


    data class Cell(val x: Int, val y: Int, val level: Char, var distanceToEnd: Int)

}
