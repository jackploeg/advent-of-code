package nl.jackploeg.aoc._2015.calendar.day03

import javax.inject.Inject
import kotlin.Int
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.grid.Cell

class Day03 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Int =
        generatorFactory.forFile(filename).readAs(::day03) { input ->
            var posX = 0
            var posY = 0
            val houses: MutableMap<Cell, Int> = mutableMapOf()
            houses[Cell(posX, posY)] = 1
            input.forEach { directions ->
                directions.forEach {
                    when (it) {
                        '>' -> posX++
                        '<' -> posX--
                        '^' -> posY++
                        'v' -> posY--
                    }
                    val current = Cell(posX, posY)
                    if (!houses.containsKey(current)) {
                        houses[current] = 1
                    } else {
                        houses[current] = houses[current]!! + 1
                    }
                }
            }
            houses.count()
        }

    fun partTwo(filename: String): Int =
        generatorFactory.forFile(filename).readAs(::day03) { input ->
            val positions: Array<Pair<Int, Int>> = arrayOf(Pair(0, 0), Pair(0, 0))
            val houses: MutableMap<Cell, Int> = mutableMapOf()
            houses[Cell(0, 0)] = 2
            input.forEach() { directions ->
                directions.forEachIndexed() { i, direction ->
                    val santaIndex = i % 2
                    when (direction) {
                        '>' -> positions[santaIndex] =
                            Pair(positions[santaIndex].first + 1, positions[santaIndex].second)

                        '<' -> positions[santaIndex] =
                            Pair(positions[santaIndex].first - 1, positions[santaIndex].second)

                        '^' -> positions[santaIndex] =
                            Pair(positions[santaIndex].first, positions[santaIndex].second + 1)

                        'v' -> positions[santaIndex] =
                            Pair(positions[santaIndex].first, positions[santaIndex].second - 1)
                    }
                    val current = Cell(positions[santaIndex].first, positions[santaIndex].second)
                    if (!houses.containsKey(current)) {
                        houses[current] = 1
                    } else {
                        houses[current] = houses[current]!! + 1
                    }
                }
            }
            houses.count()
        }

    private fun day03(line: String): CharArray =
        line.toCharArray()
}
