package nl.jackploeg.aoc._2024.calendar.day10

import nl.jackploeg.aoc.grid.IntGrid
import nl.jackploeg.aoc.grid.neighbours
import javax.inject.Inject

class Day10 @Inject constructor() {
    fun partOne(filename: String): Int {
        val grid = IntGrid().fromFile(filename)
        val zeroCells = grid.grid.filter { it.value == 0 }
        val reachableNinesFromzeroes = zeroCells.map { it.key }
            .map { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 1 } }
            .map { ones ->
                ones.flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 2 } }
                    .flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 3 } }
                    .flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 4 } }
                    .flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 5 } }
                    .flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 6 } }
                    .flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 7 } }
                    .flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 8 } }
                    .flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 9 } }
                    .distinct()
            }

        return reachableNinesFromzeroes.map { it.size }.sum()
    }

    fun partTwo(filename: String): Int {
        val grid = IntGrid().fromFile(filename)
        val zeroCells = grid.grid.filter { it.value == 0 }
        val pathsToNinesFromzeroes = zeroCells.map { it.key }
            .map { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 1 } }
            .map { ones ->
                ones.flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 2 } }
                    .flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 3 } }
                    .flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 4 } }
                    .flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 5 } }
                    .flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 6 } }
                    .flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 7 } }
                    .flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 8 } }
                    .flatMap { cell -> cell.neighbours().filter { neigbour -> grid.grid[neigbour] == 9 } }
            }

        return pathsToNinesFromzeroes.map { it.size }.sum()
    }
}
