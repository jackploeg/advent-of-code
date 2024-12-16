package nl.jackploeg.aoc._2024.calendar.day15

import nl.jackploeg.aoc.grid.*
import java.io.File
import javax.inject.Inject

class Day15 @Inject constructor() {
    fun partOne(filename: String): Int {
        val grid = CharacterGrid.fromFile(filename)
        var robot: Cell = grid.filter { it.value == '@' }.firstNotNullOf { it.key }
        val moves = File(filename + "-moves").readLines().joinToString("")
        moves.map {
            val move = when (it) {
                '>' -> Direction.EAST
                '<' -> Direction.WEST
                '^' -> Direction.NORTH
                'v' -> Direction.SOUTH
                else -> null
            }
            move?.let { move ->
                if (moveRobotOrBox(grid, robot, move)) {
                    when (move) {
                        Direction.EAST -> robot = robot.east()
                        Direction.WEST -> robot = robot.west()
                        Direction.NORTH -> robot = robot.north()
                        Direction.SOUTH -> robot = robot.south()
                    }
                }
            }
        }
        return scoreBoxGps(grid)
    }

    fun partTwo(filename: String): Long {
        val grid = CharacterGrid.fromFile(filename)
        val expandedGrid = expand(grid)
        var robot: Cell = expandedGrid.filter { it.value == '@' }.firstNotNullOf { it.key }
        val moves = File(filename + "-moves").readLines().joinToString("")
        moves.map { m ->
            val move = when (m) {
                '>' -> Direction.EAST
                '<' -> Direction.WEST
                '^' -> Direction.NORTH
                'v' -> Direction.SOUTH
                else -> null
            }
            move?.let { move ->
                if (moveRobotOrBox(expandedGrid, robot, move)) {
                    expandedGrid.setValue(robot, '.')
                    when (move) {
                        Direction.EAST -> robot = robot.east()
                        Direction.WEST -> robot = robot.west()
                        Direction.NORTH -> robot = robot.north()
                        Direction.SOUTH -> robot = robot.south()
                    }
                    expandedGrid.setValue(robot, '@')
                }
            }
        }
        return scoreLargeBoxGps(expandedGrid)
    }

    fun moveRobotOrBox(grid: CharacterGrid, cell: Cell, direction: Direction): Boolean {
        when (direction) {
            Direction.NORTH -> {
                if (grid[cell.north()] == '#') return false
                if (grid[cell.north()] == 'O') {
                    if (moveRobotOrBox(grid, cell.north(), direction)) {
                        moveMe(grid, cell, direction)
                        return true
                    } else {
                        return false
                    }
                } else if (grid[cell.north()] == '[') {
                    val box = LargeBox(cell, cell.east())
                    val boxStack = box.getAllOnTop(grid)
                    if (boxStack.topRow().all { it.canMoveNorth(grid) } &&
                        boxStack.none { it.isBelowWall(grid) }) {
                        boxStack.sortedBy { it.left.row }.forEach {
                            moveMe(grid, it, direction)
                        }
                        return true
                    } else {
                        return false
                    }
                } else if (grid[cell.north()] == ']') {
                    val box = LargeBox(cell.west(), cell)
                    val boxStack = box.getAllOnTop(grid)
                    if (boxStack.topRow().all { it.canMoveNorth(grid) } &&
                        boxStack.none { it.isBelowWall(grid) }) {
                        boxStack.sortedBy { it.left.row }.forEach {
                            moveMe(grid, it, direction)
                        }
                        return true
                    } else {
                        return false
                    }
                } else {
                    moveMe(grid, cell, direction)
                    return true
                }
            }

            Direction.SOUTH -> {
                if (grid[cell.south()] == '#') return false
                if (grid[cell.south()] == 'O') {
                    if (moveRobotOrBox(grid, cell.south(), direction)) {
                        moveMe(grid, cell, direction)
                        return true
                    } else {
                        return false
                    }
                } else if (grid[cell.south()] == '[') {
                    val box = LargeBox(cell, cell.east())
                    val boxStack = box.getAllBelow(grid)
                    if (boxStack.bottomRow().all { it.canMoveSouth(grid) } &&
                        boxStack.none { it.isOnWall(grid) }) {
                        boxStack.sortedBy { -it.left.row }.forEach {
                            moveMe(grid, it, direction)
                        }
                        return true
                    } else {
                        return false
                    }
                } else if (grid[cell.south()] == ']') {
                    val box = LargeBox(cell.west(), cell)
                    val boxStack = box.getAllBelow(grid)
                    if (boxStack.bottomRow().all { it.canMoveSouth(grid) } &&
                        boxStack.none { it.isOnWall(grid) }) {
                        boxStack.sortedBy { -it.left.row }.forEach {
                            moveMe(grid, it, direction)
                        }
                        return true
                    } else {
                        return false
                    }
                } else {
                    moveMe(grid, cell, direction)
                    return true
                }
            }

            Direction.EAST -> {
                if (grid[cell.east()] == '#') return false
                if (grid[cell.east()] == 'O' || grid[cell.east()] == '[' || grid[cell.east()] == ']') {
                    if (moveRobotOrBox(grid, cell.east(), direction)) {
                        moveMe(grid, cell, direction)
                        return true
                    } else {
                        return false
                    }
                } else {
                    moveMe(grid, cell, direction)
                    return true
                }
            }

            Direction.WEST -> {
                if (grid[cell.west()] == '#') return false
                if (grid[cell.west()] == 'O' || grid[cell.west()] == '[' || grid[cell.west()] == ']') {
                    if (moveRobotOrBox(grid, cell.west(), direction)) {
                        moveMe(grid, cell, direction)
                        return true
                    } else {
                        return false
                    }
                } else {
                    moveMe(grid, cell, direction)
                    return true
                }
            }
        }
    }

    fun moveLargeBox(grid: CharacterGrid, cell: Cell, otherhalf: Cell, direction: Direction): Boolean {
        when (direction) {
            Direction.NORTH -> {
                if (grid[cell.north()] == '#' || grid[otherhalf.north()] == '#') return false
                if (grid[cell.north()] == '[') {
                    if (moveLargeBox(grid, cell.north(), cell.northEast(), direction)) {
                        moveMe(grid, cell, direction)
                        moveMe(grid, cell.east(), direction)
                        return true
                    } else {
                        return false
                    }
                } else if (grid[cell.north()] == ']') {
                    if (moveLargeBox(grid, cell.north(), cell.northWest(), direction)) {
                        moveMe(grid, cell, direction)
                        moveMe(grid, cell.west(), direction)
                        return true
                    } else {
                        return false
                    }
                }
            }

            Direction.SOUTH -> {
                if (grid[cell.south()] == '#' || grid[otherhalf.south()] == '#') return false
                if (grid[cell.south()] == '[') {
                    if (moveLargeBox(grid, cell.south(), cell.southEast(), direction)) {
                        moveMe(grid, cell, direction)
                        moveMe(grid, cell.east(), direction)
                        return true
                    } else {
                        return false
                    }
                } else if (grid[cell.south()] == ']') {
                    if (moveLargeBox(grid, cell.south(), cell.southWest(), direction)) {
                        moveMe(grid, cell, direction)
                        moveMe(grid, cell.west(), direction)
                        return true
                    } else {
                        return false
                    }
                }
            }

            else -> return false
        }
        return false
    }

    fun moveMe(grid: CharacterGrid, cell: Cell, direction: Direction) {
        when (direction) {
            Direction.NORTH -> {
                grid.setValue(cell.north(), grid[cell]!!)
                grid.setValue(cell, '.')
            }

            Direction.SOUTH -> {
                grid.setValue(cell.south(), grid[cell]!!)
                grid.setValue(cell, '.')
            }

            Direction.EAST -> {
                grid.setValue(cell.east(), grid[cell]!!)
                grid.setValue(cell, '.')
            }

            Direction.WEST -> {
                grid.setValue(cell.west(), grid[cell]!!)
                grid.setValue(cell, '.')
            }
        }
    }

    fun moveMe(grid: CharacterGrid, box: LargeBox, direction: Direction) {
        when (direction) {
            Direction.NORTH -> {
                if (grid[box.left] == '@') {
                    grid.setValue(box.left.north(), '@')
                    grid.setValue(box.left, '.')
                } else if (grid[box.right] == '@') {
                    grid.setValue(box.right.north(), '@')
                    grid.setValue(box.right, '.')
                } else {
                    grid.setValue(box.left.north(), grid[box.left]!!)
                    grid.setValue(box.left, '.')
                    grid.setValue(box.right.north(), grid[box.right]!!)
                    grid.setValue(box.right, '.')
                }
            }

            Direction.SOUTH -> {
                if (grid[box.left] == '@') {
                    grid.setValue(box.left.south(), '@')
                    grid.setValue(box.left, '.')
                } else if (grid[box.right] == '@') {
                    grid.setValue(box.right.south(), '@')
                    grid.setValue(box.right, '.')
                } else {
                    grid.setValue(box.left.south(), grid[box.left]!!)
                    grid.setValue(box.left, '.')
                    grid.setValue(box.right.south(), grid[box.right]!!)
                    grid.setValue(box.right, '.')
                }
            }

            else -> {}
        }
    }

    fun expand(grid: CharacterGrid): CharacterGrid {
        val result: MutableMap<Cell, Char> = mutableMapOf()
        grid.map {
            if (it.value == '#') {
                result[Cell(it.key.row, it.key.col * 2)] = it.value
                result[Cell(it.key.row, it.key.col * 2 + 1)] = it.value
            }
            if (it.value == '@') {
                result[Cell(it.key.row, it.key.col * 2)] = it.value
                result[Cell(it.key.row, it.key.col * 2 + 1)] = '.'
            }
            if (it.value == 'O') {
                result[Cell(it.key.row, it.key.col * 2)] = '['
                result[Cell(it.key.row, it.key.col * 2 + 1)] = ']'
            }
            if (it.value == '.') {
                result[Cell(it.key.row, it.key.col * 2)] = '.'
                result[Cell(it.key.row, it.key.col * 2 + 1)] = '.'
            }
        }
        return CharacterGrid(result)
    }

    private fun scoreBoxGps(grid: CharacterGrid) =
        grid.entries.map {
            if (it.value == 'O') {
                it.key.row * 100 + it.key.col
            } else 0
        }.sum()

    private fun scoreLargeBoxGps(grid: CharacterGrid) =
        grid.entries.filter { it.value == '[' }
            .map { it.key.row * 100L + it.key.col }
            .sum()

    data class LargeBox(val left: Cell, val right: Cell) {
        fun canMoveNorth(grid: CharacterGrid): Boolean =
            grid[left.north()] == '.' && grid[right.north()] == '.'

        fun canMoveSouth(grid: CharacterGrid): Boolean =
            grid[left.south()] == '.' && grid[right.south()] == '.'

        fun isBelowWall(grid: CharacterGrid): Boolean =
            grid[left.north()] == '#' || grid[right.north()] == '#'

        fun isOnWall(grid: CharacterGrid): Boolean =
            grid[left.south()] == '#' || grid[right.south()] == '#'

        fun getAllOnTop(grid: CharacterGrid): Set<LargeBox> {
            val boxes = mutableSetOf<LargeBox>()
            //println("${grid[this.left]}, ${grid[this.right]}")
            boxes.add(this)
            if (grid[left.north()] == '[') {
                boxes.addAll(LargeBox(left.north(), right.north()).getAllOnTop(grid))
            } else if (grid[left.north()] == ']') {
                boxes.addAll(LargeBox(left.northWest(), left.north()).getAllOnTop(grid))
            }
            if (grid[right.north()] == '[') {
                boxes.addAll(LargeBox(right.north(), right.northEast()).getAllOnTop(grid))
            }
            return boxes
        }

        fun getAllBelow(grid: CharacterGrid): Set<LargeBox> {
            val boxes = mutableSetOf<LargeBox>()
            boxes.add(this)
            if (grid[left.south()] == '[') {
                boxes.addAll(LargeBox(left.south(), right.south()).getAllBelow(grid))
            } else if (grid[left.south()] == ']') {
                boxes.addAll(LargeBox(left.southWest(), left.south()).getAllBelow(grid))
            }
            if (grid[right.south()] == '[') {
                boxes.addAll(LargeBox(right.south(), right.southEast()).getAllBelow(grid))
            }
            return boxes
        }
    }

    fun Set<LargeBox>.topRow(): List<LargeBox> =
        this.filter { it.left.row == this.map { it.left.row }.min() }

    fun Set<LargeBox>.bottomRow(): List<LargeBox> =
        this.filter { it.left.row == this.map { it.left.row }.max() }
}
