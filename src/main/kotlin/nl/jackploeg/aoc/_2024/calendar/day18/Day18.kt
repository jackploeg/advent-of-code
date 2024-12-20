package nl.jackploeg.aoc._2024.calendar.day18

import nl.jackploeg.aoc.grid.*
import java.io.File
import java.util.*
import javax.inject.Inject

class Day18 @Inject constructor() {
    fun partOne(filename: String, gridSize: Int, steps: Int): Int {
        val input = File(filename).readLines()
        val bytes = input.map { line ->
            val (x, y) = line.split(",")
            Byte(x.toInt(), y.toInt())
        }
        val grid: CharacterGrid = CharacterGrid.withSize(gridSize)
        (0..steps - 1).forEach { step ->
            grid.setValue(Cell(bytes[step].y, bytes[step].x), '#')
        }
        return shortestPath(grid, Cell(0, 0), Cell(gridSize - 1, gridSize - 1))
    }

    fun partTwo(filename: String, gridSize: Int, steps: Int): Byte {
        val input = File(filename).readLines()
        val bytes = input.map { line ->
            val (x, y) = line.split(",")
            Byte(x.toInt(), y.toInt())
        }
        val grid: CharacterGrid = CharacterGrid.withSize(gridSize)
        (0..steps - 1).forEach { step ->
            grid.setValue(Cell(bytes[step].y, bytes[step].x), '#')
        }
        var step = steps - 1
        while (shortestPath(grid, Cell(0, 0), Cell(gridSize - 1, gridSize - 1))< Int.MAX_VALUE) {
            step++
            println(bytes[step])
            grid.setValue(Cell(bytes[step].y, bytes[step].x), '#')
        }
        return bytes[step]
    }

    private fun shortestPath(grid: CharacterGrid, start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        val visited = mutableSetOf<Cell>()
        val unvisited = PriorityQueue<Path>(compareBy(Path::cost))
        unvisited.add(Path(start, 0))
        var bestCost = Int.MAX_VALUE
        while (unvisited.isNotEmpty()) {
            val (current, cost) = unvisited.remove().also { visited.add(it.cell) }
            if (current == end) {
                bestCost = cost
                break
            }
            unvisited += listOf(
                Path(current.north(), cost + 1),
                Path(current.south(), cost + 1),
                Path(current.east(), cost + 1),
                Path(current.west(), cost + 1),
            ).filter { grid.contains(it.cell) &&
                    it.cell !in visited &&
                    grid[it.cell] != '#' &&
                    !unvisited.any { it.cell == current && it.cost <= cost }
            }
        }
        return bestCost

    }

    //data class CellWithCost(val cell: Cell, val cost: Int)
    data class Path(
        val cell: Cell,
        val cost: Int
    )

    data class Byte(val x: Int, val y: Int)
}
