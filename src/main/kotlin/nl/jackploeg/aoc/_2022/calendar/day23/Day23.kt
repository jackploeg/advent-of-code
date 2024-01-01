package nl.jackploeg.aoc._2022.calendar.day23

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import java.io.Serializable

class Day23 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String) = spreadElves(10, filename)

    fun partTwo(filename: String, steps: Int) = spreadElves(steps, filename)

    var elves = mutableListOf<Elve>()

    // TODO this take a long time for > 10 steps....
    fun spreadElves(steps: Int, fileName: String): Int {
        val input = readStringFile(fileName)

        elves = mutableListOf<Elve>()

        for ((y, line) in input.withIndex()) {
            for ((x, char) in line.toCharArray().withIndex()) {
                if (char == '#')
                    elves.add(Elve(Position(x, y)))
            }
        }

        (0..steps - 1).forEach { spreadStep(it) }

        val minX = elves.map { it.position.x }.min()
        val maxX = elves.map { it.position.x }.max()
        val minY = elves.map { it.position.y }.min()
        val maxY = elves.map { it.position.y }.max()

        return ((maxX - minX + 1) * (maxY - minY + 1)) - elves.size
    }

    private fun printElvesGrid(minY: Int, maxY: Int, minX: Int, maxX: Int) {
        println("--------------------------------")
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                if (elves.contains(Elve(Position(x, y))))
                    print('#')
                else
                    print('.')
            }
            println()
        }
    }

    fun spreadStep(step: Int) {
//        if (step % 1000 == 0) println(step)
        val candidatePositions = mutableMapOf<Position, Int>()
        for (elve in elves) {
            val neigbours = elveNeigbours(elve)
            if (!neigbours.first && !neigbours.second && !neigbours.third && !neigbours.fourth) {
                elve.preferredPosition = null
                continue
            }
            val preferredPosition =
                findPreferredPosition(elve, neigbours, directionPreference[step % directionPreference.size])
            elve.preferredPosition = preferredPosition

            preferredPosition?.let {
                if (candidatePositions.contains(preferredPosition)) {
                    candidatePositions.put(preferredPosition, candidatePositions.getValue(preferredPosition) + 1)
                } else {
                    candidatePositions.put(preferredPosition, 1)
                }
            }
        }
        if (elves.filter { it.preferredPosition != null }.size == 0) println("No elves moved at step ${step + 1}!")
        for (elve in elves) {
            if (elve.preferredPosition != null) {
                if (candidatePositions.getValue(elve.preferredPosition!!) < 2) {
                    elve.position = elve.preferredPosition!!
                }
            }
        }
        val newElves = mutableListOf<Elve>()
        newElves.addAll(elves)
        elves = newElves
    }

    fun findPreferredPosition(
        elve: Elve,
        neigbours: Quadruple<Boolean, Boolean, Boolean, Boolean>,
        direction: Direction
    ): Position? {
        return when (direction) {
            Direction.NORTH -> if (!neigbours.first) Position(elve.position.x, elve.position.y - 1)
            else if (!neigbours.second) Position(elve.position.x, elve.position.y + 1)
            else if (!neigbours.third) Position(elve.position.x - 1, elve.position.y)
            else if (!neigbours.fourth) Position(elve.position.x + 1, elve.position.y)
            else null

            Direction.SOUTH -> if (!neigbours.second) Position(elve.position.x, elve.position.y + 1)
            else if (!neigbours.third) Position(elve.position.x - 1, elve.position.y)
            else if (!neigbours.fourth) Position(elve.position.x + 1, elve.position.y)
            else if (!neigbours.first) Position(elve.position.x, elve.position.y - 1)
            else null

            Direction.WEST -> if (!neigbours.third) Position(elve.position.x - 1, elve.position.y)
            else if (!neigbours.fourth) Position(elve.position.x + 1, elve.position.y)
            else if (!neigbours.first) Position(elve.position.x, elve.position.y - 1)
            else if (!neigbours.second) Position(elve.position.x, elve.position.y + 1)
            else null

            Direction.EAST -> if (!neigbours.fourth) Position(elve.position.x + 1, elve.position.y)
            else if (!neigbours.first) Position(elve.position.x, elve.position.y - 1)
            else if (!neigbours.second) Position(elve.position.x, elve.position.y + 1)
            else if (!neigbours.third) Position(elve.position.x - 1, elve.position.y)
            else null
        }
    }

    fun elveNeigbours(elve: Elve): Quadruple<Boolean, Boolean, Boolean, Boolean> {
        val surroundingPositions = listOf<Position>(
            Position(elve.position.x - 1, elve.position.y - 1),
            Position(elve.position.x, elve.position.y - 1),
            Position(elve.position.x + 1, elve.position.y - 1),
            Position(elve.position.x - 1, elve.position.y),
            Position(elve.position.x, elve.position.y),
            Position(elve.position.x + 1, elve.position.y),
            Position(elve.position.x - 1, elve.position.y + 1),
            Position(elve.position.x, elve.position.y + 1),
            Position(elve.position.x + 1, elve.position.y + 1),
        )
        val elveNorth = if ((elves.contains(Elve(surroundingPositions[0]))) ||
            elves.contains(Elve(surroundingPositions[1])) ||
            elves.contains(Elve(surroundingPositions[2]))
        ) true else false

        val elveSouth = if ((elves.contains(Elve(surroundingPositions[6]))) ||
            elves.contains(Elve(surroundingPositions[7])) ||
            elves.contains(Elve(surroundingPositions[8]))
        ) true else false

        val elveWest = if ((elves.contains(Elve(surroundingPositions[0]))) ||
            elves.contains(Elve(surroundingPositions[3])) ||
            elves.contains(Elve(surroundingPositions[6]))
        ) true else false

        val elveEast = if ((elves.contains(Elve(surroundingPositions[2]))) ||
            elves.contains(Elve(surroundingPositions[5])) ||
            elves.contains(Elve(surroundingPositions[8]))
        ) true else false

        return Quadruple(elveNorth, elveSouth, elveWest, elveEast)
    }

    data class Position(val x: Int, val y: Int)

    data class Elve(var position: Position, var preferredPosition: Position? = null) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Elve

            if (position != other.position) return false

            return true
        }

        override fun hashCode(): Int {
            return position.hashCode()
        }
    }

    data class Quadruple<A, B, C, D>(var first: A, var second: B, var third: C, var fourth: D) : Serializable {
        override fun toString(): String = "($first, $second, $third, $fourth)"
    }

    enum class Direction { NORTH, SOUTH, WEST, EAST }

    val directionPreference = listOf<Direction>(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)
}
