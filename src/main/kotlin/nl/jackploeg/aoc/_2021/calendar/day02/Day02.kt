package nl.jackploeg.aoc._2021.calendar.day02

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import javax.inject.Inject

class Day02 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Long {
        var position = Position()
        generatorFactory.forFile(filename).readAs(::day02) { input ->
            input.forEach {
                position = position.travel(it)
            }
        }
        return position.result
    }

    fun partTwo(filename: String): Long {
        var positionWithAim = PositionWithAim()
        generatorFactory.forFile(filename).readAs(::day02) { input ->
            input.forEach {
                positionWithAim = positionWithAim.travel(it)
            }
        }
        return positionWithAim.result
    }

    private fun day02(line: String): Move {
        val (direction, units) = line.split(' ')
        return Move(Direction.valueOf(direction.uppercase()), units.toLong())
    }

    enum class Direction {
        FORWARD, UP, DOWN
    }

    data class Move(val direction: Direction, val units: Long)

    data class Position(val horizontal: Long = 0, val depth: Long = 0) {
        val result: Long
            get() = horizontal * depth

        fun travel(move: Move): Position {
            var x = horizontal
            var y = depth
            when (move.direction) {
                Direction.FORWARD -> x += move.units
                Direction.UP -> y -= move.units
                Direction.DOWN -> y += move.units
            }
            return Position(x, y)
        }
    }

    data class PositionWithAim(val horizontal: Long = 0, val depth: Long = 0, val aim: Long = 0) {
        val result: Long
            get() = horizontal * depth

        fun travel(move: Move): PositionWithAim {
            var x = horizontal
            var y = depth
            var a = aim
            when (move.direction) {
                Direction.FORWARD -> {
                    x += move.units
                    y += move.units * aim
                }

                Direction.UP -> {
                    a -= move.units
                }

                Direction.DOWN -> {
                    a += move.units
                }
            }
            return PositionWithAim(x, y, a)
        }
    }
}
