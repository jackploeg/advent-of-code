package nl.jackploeg.aoc._2024.calendar.day20

import nl.jackploeg.aoc.grid.*
import javax.inject.Inject
import kotlin.math.abs

class Day20 @Inject constructor() {
    fun partOne(filename: String, minTimeToSave: Int): Int {
        val grid = CharacterGrid.fromFile(filename)
        val start = grid.entries.first { it.value == 'S' }.key
        val end = grid.entries.first { it.value == 'E' }.key
        grid.setValue(start, '.')
        grid.setValue(end, '.')

        val gridWithPath: MutableMap<Cell, Int> = findPathWithCost(grid, start)
        val cellsToCheat =  grid.filter { it.value == '#' }
            .filter { (cell, _) ->
                grid[cell.north()]=='.' && grid[cell.south()]=='.' && abs(gridWithPath[cell.north()]!! - gridWithPath[cell.south()]!!) > minTimeToSave ||
                grid[cell.west()]=='.' && grid[cell.east()]=='.' && abs(gridWithPath[cell.east()]!! - gridWithPath[cell.west()]!!) > minTimeToSave
            }
        return cellsToCheat.size
    }

    fun partTwo(filename: String, minTimeToSave: Int): Long {
        val grid = CharacterGrid.fromFile(filename)
        val start = grid.entries.first { it.value == 'S' }.key
        val end = grid.entries.first { it.value == 'E' }.key
        grid.setValue(start, '.')
        grid.setValue(end, '.')

        val gridWithPath: MutableMap<Cell, Int> = findPathWithCost(grid, start)

        var cheats = 0L
        gridWithPath.forEach { (source, srcCost) ->
            cheats += gridWithPath.count { (target, tgtCost) ->
                source.manhattanDistance(target) < 21  && tgtCost - srcCost - source.manhattanDistance(target) >= minTimeToSave
            }
        }
        return cheats
    }

    fun findPathWithCost(grid: CharacterGrid, start: Cell): MutableMap<Cell, Int> {
        val gridWithPath: MutableMap<Cell, Int> = mutableMapOf()
        gridWithPath[start] = 0
        val queue: MutableList<Cell> = mutableListOf(start)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val currentCost = gridWithPath[current]!!
            val nextCost = currentCost + 1
            current.neighbours().filter { grid[it] == '.' && !gridWithPath.containsKey(it) }.forEach { neighbour ->
                gridWithPath[neighbour] = nextCost
                queue.add(neighbour)
            }
        }
        return gridWithPath
    }
}
