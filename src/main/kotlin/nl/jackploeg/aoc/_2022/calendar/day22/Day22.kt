package nl.jackploeg.aoc._2022.calendar.day22

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile

typealias State = Pair<Int, Pair<Int, Int>>

class Day22 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String) = followPath(filename)

    fun partTwo(filename: String, sideLength: Int) = arpitsSolutionForPart2(filename, sideLength)

    fun followPath(fileName: String): Int {

        val input = readStringFile(fileName)

        val grid: MutableList<String> = mutableListOf()
        input.takeWhile { it.isNotEmpty() && " .#".contains(it[0]) }.map { grid.add(it) }

        val gridwidth = grid.map { it.length }.max()
        grid.withIndex().forEach { (index, it) -> grid[index] += " ".repeat(gridwidth - it.length) }

        var pathDescription: String
        input.first { it.isNotEmpty() && "0123456789RL".contains(it[0]) }.also { pathDescription = it }

        var x = 0
        while (grid[0][x] == ' ') x++
        var y = 0
        var facing: Facing = Facing.RIGHT
        var instructionPointer = 0

        while (instructionPointer < pathDescription.length) {
            val instruction = readInstruction(pathDescription, instructionPointer)
            instructionPointer += instruction.third
            when (instruction.first) {
                Instruction.TURN_LEFT -> {
                    facing = when (facing) {
                        Facing.RIGHT -> Facing.UP
                        Facing.UP -> Facing.LEFT
                        Facing.LEFT -> Facing.DOWN
                        Facing.DOWN -> Facing.RIGHT
                    }
                }

                Instruction.TURN_RIGHT -> {
                    facing = when (facing) {
                        Facing.RIGHT -> Facing.DOWN
                        Facing.DOWN -> Facing.LEFT
                        Facing.LEFT -> Facing.UP
                        Facing.UP -> Facing.RIGHT
                    }
                }

                Instruction.MOVE -> {
                    when (facing) {
                        Facing.RIGHT -> {
                            run breaking@{
                                repeat((1..instruction.second).count()) {
                                    if (x < gridwidth - 1 && grid[y][x + 1] == '.')
                                        x++
                                    else if (x == gridwidth - 1 || grid[y][x + 1] == ' ') {
                                        var newX = x - 1
                                        while (newX > -1 && grid[y][newX] != ' ')
                                            newX--
                                        if (grid[y][newX + 1] == '#')
                                            return@breaking
                                        else
                                            x = newX + 1
                                    }
                                }
                            }
                        }

                        Facing.DOWN -> {
                            run breaking@{
                                repeat((1..instruction.second).count()) {
                                    if (y < grid.size - 1 && grid[y + 1][x] == '.')
                                        y++
                                    else if (y == grid.size - 1 || grid[y + 1][x] == ' ') {
                                        var newY = y - 1
                                        while (newY > -1 && grid[newY][x] != ' ')
                                            newY--
                                        if (grid[newY + 1][x] == '#')
                                            return@breaking
                                        else
                                            y = newY + 1
                                    }
                                }
                            }
                        }

                        Facing.LEFT -> {
                            run breaking@{
                                repeat((1..instruction.second).count()) {
                                    if (x > 0 && grid[y][x - 1] == '.')
                                        x--
                                    else if (x == 0 || grid[y][x - 1] == ' ') {
                                        var newX = x + 1
                                        while (newX < gridwidth - 1 && grid[y][newX] != ' ')
                                            newX++
                                        if (grid[y][newX - 1] == '#')
                                            return@breaking
                                        else
                                            x = newX - 1
                                    }
                                }
                            }
                        }

                        Facing.UP -> {
                            run breaking@{
                                repeat((1..instruction.second).count()) {
                                    if (y > 0 && grid[y - 1][x] == '.')
                                        y--
                                    else if (y == 0 || grid[y - 1][x] == ' ') {
                                        var newY = y + 1
                                        while (newY < grid.size - 1 && grid[newY][x] != ' ')
                                            newY++
                                        if (grid[newY - 1][x] == '#')
                                            return@breaking
                                        else
                                            y = newY - 1
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

//        println("x = $x, y = $y, facing = $facing")
        return (1000 * (y + 1)) + (4 * (x + 1)) + facing.score
    }

    fun readInstruction(pathDescription: String, pos: Int): Triple<Instruction, Int, Int> {
        if (pathDescription[pos] == 'R')
            return Triple(Instruction.TURN_RIGHT, 0, 1)
        if (pathDescription[pos] == 'L')
            return Triple(Instruction.TURN_LEFT, 0, 1)

        var endpos = pos
        while (endpos < pathDescription.length && "0123456789".contains(pathDescription[endpos]))
            endpos++
        val steps = pathDescription.substring(pos, endpos).toInt()
        return Triple(Instruction.MOVE, steps, endpos - pos)
    }

    enum class Facing(val score: Int) { RIGHT(0), DOWN(1), LEFT(2), UP(3) }

    enum class Instruction { MOVE, TURN_LEFT, TURN_RIGHT }

    /*************************************************************************************/
    fun arpitsSolutionForPart2(fileName: String, sideLength: Int): Int {
        val input = readStringFile(fileName)
        return (part2(input, sideLength))
    }

    fun Pair<Int, Int>.move(direction: Int) = when (direction.mod(4)) {
        0 -> first + 1 to second
        1 -> first to second + 1
        2 -> first - 1 to second
        3 -> first to second - 1
        else -> throw AssertionError()
    }

    operator fun List<String>.get(pos: Pair<Int, Int>): Char = getOrNull(pos.second)?.getOrNull(pos.first) ?: ' '


    fun getPerimeter(board: List<String>): List<State> {
        val initialPos = board[0].indexOf('.') to 0
        return buildList {
            var pos = initialPos
            var dir = 0
            do {
                add(dir to pos)
                val forward = pos.move(dir)
                if (board[forward] == ' ') {
                    dir = (dir + 1).mod(4)
                } else {
                    val left = forward.move(dir - 1)
                    if (board[left] == ' ') {
                        pos = forward
                    } else {
                        pos = left
                        dir = (dir - 1).mod(4)
                    }
                }
//                println(pos)
            } while (pos != initialPos || dir != 0)
        }
    }

    fun solve(input: List<String>, adjacencyMap: Map<State, State>): Int {
        val moves = input.last().split(Regex("(?<=[RL])|(?=[RL])"))
        val board = input.dropLast(2)
        var pos = board[0].indexOf('.') to 0
        var dir = 0
        for (move in moves) {
//            println(move)
            when (move) {
                "L" -> dir = (dir - 1).mod(4)
                "R" -> dir = (dir + 1).mod(4)
                else -> repeat(move.toInt()) {
                    val (newDir, newPos) = adjacencyMap[dir to pos] ?: (dir to pos.move(dir))
                    if (board[newPos] == '#') return@repeat
                    dir = newDir
                    pos = newPos
                }
            }
        }
        return 1000 * (pos.second + 1) + 4 * (pos.first + 1) + dir
    }

    fun part2(input: List<String>, sideLength: Int): Int {
        val adjacencyMap = mutableMapOf<State, State>()
        val perimeter = getPerimeter(board = input.dropLast(2))
        val edges = perimeter.chunked(sideLength).map { it[0].first to it }.toMutableList()
        while (edges.isNotEmpty()) {
            var i = 0
            while (i < edges.lastIndex) {
                val a = edges[i]
                val b = edges[i + 1]
                if ((a.first - b.first).mod(4) == 1) {
                    edges.subList(i, i + 2).clear()
                    for (j in i..edges.lastIndex) {
                        val edge = edges[j]
                        edges[j] = (edge.first - 1).mod(4) to edge.second
                    }
                    for (j in 0 until sideLength) {
                        val (dir1, pos1) = a.second[j]
                        val (dir2, pos2) = b.second[sideLength - j - 1]
                        adjacencyMap[(dir1 - 1).mod(4) to pos1] = (dir2 + 1).mod(4) to pos2
                        adjacencyMap[(dir2 - 1).mod(4) to pos2] = (dir1 + 1).mod(4) to pos1
                    }
                } else {
                    i++
                }
            }
        }
        return solve(input, adjacencyMap)
    }
}
