package nl.jackploeg.aoc._2024.calendar.day06

import java.io.File
import javax.inject.Inject

class Day06 @Inject constructor() {

    fun partOne(filename: String): Int {
        val input = File(filename).readLines()
        val area: List<List<Char>> = input.map { it.toList() }
        val walk = walk(area)
        val visitedPoints = walk.first.map { Pair(it.x, it.y) }.distinct()
        return visitedPoints.size
    }

    fun partTwo(filename: String): Int {
        val input = File(filename).readLines()
        val area: List<List<Char>> = input.map { it.toList() }
        val firstWalk = walk(area)
        val possibleBlockers: MutableSet<Pair<Int, Int>> = mutableSetOf()
        firstWalk.first.forEach {
            val tryArea = area.map { it.toMutableList() }
            if (tryArea[it.y][it.x] != '^') {
                tryArea[it.y][it.x] = '#'
            }
            val secondWalk = walk(tryArea)
            if (secondWalk.second) {
                possibleBlockers.add(Pair(it.x, it.y))
            }
        }
        return possibleBlockers.size
    }

    fun walk(area: List<List<Char>>): Pair<Set<VisitedCell>, Boolean> {
        val row = area.first { chars -> chars.contains('^') }
        val width = area[0].size
        var y = area.indexOf(row)
        var x = row.indexOf('^')
        var direction = Direction.NORTH
        var outOfArea = false
        var hasLoop = false
        val visitedCells: MutableSet<VisitedCell> = mutableSetOf()
        while (!outOfArea) {
            if (visitedCells.contains(VisitedCell(x, y, direction))) {
                hasLoop = true
                break
            }
            visitedCells.add(VisitedCell(x, y, direction))
            when (direction) {
                Direction.NORTH -> {
                    if (y == 0) {
                        outOfArea = true
                        break
                    } else {
                        if (area[y - 1][x] != '#') {
                            y--
                        } else {
                            direction = direction.turnRight()
                        }
                    }
                }

                Direction.SOUTH -> {
                    if (y == area.size - 1) {
                        outOfArea = true
                        break
                    } else {
                        if (area[y + 1][x] != '#') {
                            y++
                        } else {
                            direction = direction.turnRight()
                        }
                    }
                }

                Direction.EAST -> {
                    if (x == width - 1) {
                        outOfArea = true
                        break
                    } else {
                        if (area[y][x + 1] != '#') {
                            x++
                        } else {
                            direction = direction.turnRight()
                        }
                    }
                }

                Direction.WEST -> {
                    if (x == 0) {
                        outOfArea = true
                        break
                    } else {
                        if (area[y][x - 1] != '#') {
                            x--
                        } else {
                            direction = direction.turnRight()
                        }
                    }
                }
            }
        }
        return Pair(visitedCells, hasLoop)

    }

    data class VisitedCell(val x: Int, val y: Int, val direction: Direction)

    enum class Direction {
        NORTH {
            override fun turnRight() = EAST
        },
        SOUTH {
            override fun turnRight() = WEST
        },
        EAST {
            override fun turnRight() = SOUTH
        },
        WEST {
            override fun turnRight() = NORTH
        };

        abstract fun turnRight(): Direction
    }

}
