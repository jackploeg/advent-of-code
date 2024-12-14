package nl.jackploeg.aoc._2024.calendar.day14

import nl.jackploeg.aoc.grid.Cell
import nl.jackploeg.aoc.grid.col
import nl.jackploeg.aoc.grid.row
import nl.jackploeg.aoc.grid.surroundings
import java.io.File
import javax.inject.Inject

class Day14 @Inject constructor() {
    fun partOne(filename: String, gridWidth: Int, gridHeight: Int): Int {
        val input = File(filename).readLines()
        val robots = input.map { Robot.fromLine(it) }
        val robotsAfterMoves = robots.map { Robot(it.positionAfterMoves(gridWidth, gridHeight, 100L), it.speed) }
        val quadrants = robotsPerQuadrant(robotsAfterMoves, gridWidth, gridHeight)
        return quadrants.keys.mapNotNull { quadrants[it]?.size }.reduce { acc, i -> acc * i }
    }

    fun partTwo(filename: String, gridWidth: Int, gridHeight: Int): Int {
        val input = File(filename).readLines()
        val robots = input.map { Robot.fromLine(it) }
        (0..gridWidth * gridHeight).forEach { second ->
            val robotsAfterMoves =
                robots.map { Robot(it.positionAfterMoves(gridWidth, gridHeight, second.toLong()), it.speed) }
            // 3x3 block of robots indicates that there's a cluster of robots, could be a Christmas tree?
            if (robotsAfterMoves.any { robot-> robot.position.surroundings().all{neighbour -> robotsAfterMoves.any { it.position == neighbour }} }) {
                println(second)
                (0..gridHeight - 1).forEach { row ->
                    (0..gridWidth).forEach { col ->
                        if (robotsAfterMoves.any { it.position.row == row && it.position.col == col }) {
                            print('#')
                        } else {
                            print(' ')
                        }
                    }
                    println()
                }
                return second
            }
        }
        return -1
    }

    fun robotsPerQuadrant(robots: List<Robot>, gridWidth: Int, gridHeight: Int): Map<Int, List<Pair<Int, Robot>>> {
        val middleRow = gridHeight / 2
        val middleCol = gridWidth / 2
        val quadrants = robots.mapNotNull { robot ->
            when {
                robot.position.row < middleRow && robot.position.col < middleCol -> 0 to robot
                robot.position.row < middleRow && robot.position.col > middleCol -> 1 to robot
                robot.position.row > middleRow && robot.position.col < middleCol -> 2 to robot
                robot.position.row > middleRow && robot.position.col > middleCol -> 3 to robot
                else -> null
            }
        }.groupBy { it.first }
        return quadrants
    }

    class Robot(val position: Cell, val speed: Cell) {
        fun positionAfterMoves(gridWidth: Int, gridHeight: Int, moves: Long): Cell {
            var newRow = ((position.row + speed.row * moves) % gridHeight).toInt()
            if (newRow < 0) newRow += gridHeight
            var newCol = ((position.col + speed.col * moves) % gridWidth).toInt()
            if (newCol < 0) newCol += gridWidth
            return Cell(newRow, newCol)
        }

        companion object {
            fun fromLine(line: String): Robot {
                val (p, v) = line.split(' ')
                val (_, pval) = p.split('=')
                val (px, py) = pval.split(',')

                val (_, vval) = v.split('=')
                val (vx, vy) = vval.split(',')
                return Robot(Cell(py.toInt(), px.toInt()), Cell(vy.toInt(), vx.toInt()))
            }
        }
    }
}
