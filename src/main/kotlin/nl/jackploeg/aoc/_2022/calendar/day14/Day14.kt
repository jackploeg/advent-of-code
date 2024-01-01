package nl.jackploeg.aoc._2022.calendar.day14

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile

class Day14 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(filename: String) =countFallingSand(filename)

  fun partTwo(filename: String) =countFallingSand(filename, true)

    fun countFallingSand(fileName: String, withFloor: Boolean = false): Int {
        val input = readStringFile(fileName)

        val grid = readGrid(input, withFloor)
        var gridStatus = GridStatus.OPEN
        var sandUnits = 0

        while (gridStatus == GridStatus.OPEN) {
            sandUnits++
            gridStatus = dropSandUnit(grid)
        }

        //printGrid(grid)
        return sandUnits - if (gridStatus == GridStatus.FALLTHROUGH) 1 else 0
    }

    fun readGrid(input: List<String>, withFloor: Boolean): ArrayList<ArrayList<Cell>> {

        val grid: ArrayList<ArrayList<Cell>> = ArrayList()
        for (i in 0..200) {
            val row = ArrayList<Cell>()
            for (j in 0..800) {
                row.add(Cell())
            }
            grid.add(row)
        }

        grid[0][500].content = Content.SOURCE

        for (line in input) {
            val walls = line.split(" -> ")
            for (i in 0..walls.size - 2) {
                val corner1 = pairFromWall(walls[i])
                val corner2 = pairFromWall(walls[i + 1])
                val leftx = kotlin.math.min(corner1.first, corner2.first)
                val rightx = kotlin.math.max(corner1.first, corner2.first)
                val topy = kotlin.math.min(corner1.second, corner2.second)
                val bottomy = kotlin.math.max(corner1.second, corner2.second)
                for (x in leftx..rightx)
                    grid[corner1.second][x].content = Content.ROCK
                for (y in topy..bottomy)
                    grid[y][corner1.first].content = Content.ROCK
            }
        }

        if (withFloor) {
            for (y in grid.size - 1 downTo 0) {
                if (!grid[y].none { it.content == Content.ROCK }) {
                    for (x in 0 until grid[y].size) {
                        grid[y + 2][x].content = Content.ROCK
                    }
                    break
                }
            }
        }
        //printGrid(grid)
        return grid
    }

    fun dropSandUnit(grid: ArrayList<ArrayList<Cell>>): GridStatus {

        var x = 500
        var y = 0
        var atRest = false
        var fallThrough = false
        var entryBlocked = false

        while (!atRest && !fallThrough && !entryBlocked) {
            if (grid[y + 1][x].content == Content.AIR) {
                y++
                if (y >= grid.size - 1) {
                    fallThrough = true
                }
            } else if (grid[y + 1][x - 1].content == Content.AIR) {
                y++
                x--
            } else if (grid[y + 1][x + 1].content == Content.AIR) {
                y++
                x++
            } else {
                grid[y][x].content = Content.SAND
                if (y == 0) {
                    entryBlocked = true
                } else {
                    atRest = true
                }
            }
        }
        if (fallThrough)
            return GridStatus.FALLTHROUGH
        if (entryBlocked)
            return GridStatus.BLOCKED
        return GridStatus.OPEN
    }

    fun pairFromWall(wall: String): Pair<Int, Int> {
        val nums = wall.split(",")
        return Pair(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]))
    }

    fun printGrid(grid: ArrayList<ArrayList<Cell>> = ArrayList()) {
        for (row in grid) {
            for (cell in row) {
                when (cell.content) {
                    Content.AIR -> print('.')
                    Content.ROCK -> print('#')
                    Content.SAND -> print('o')
                    Content.SOURCE -> print('+')
                }
            }
            println()
        }
    }

    enum class Content { AIR, ROCK, SAND, SOURCE }
    class Cell(var content: Content = Content.AIR)

    enum class GridStatus { OPEN, FALLTHROUGH, BLOCKED }}
