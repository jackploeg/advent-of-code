package nl.jackploeg.aoc._2022.calendar.day20

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readNumbersFile

class Day20 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(filename: String) =getGroveCoordinates(filename, 1L, 1)
  fun partTwo(filename: String) =getGroveCoordinates(filename, 811589153L, 10)

    fun getGroveCoordinates(fileName: String, decryptionKey: Long, iterations: Int): Long {
        val input = readNumbersFile(fileName)

        val inputLength = input.size
        val coordinates = mutableListOf<Coordinate>()
        val result = mutableListOf<Coordinate>()
        for ((index, number) in input.withIndex()) {
            val coordinate = Coordinate(index, number.toLong() * decryptionKey)
            coordinates.add(coordinate)
            result.add(coordinate)
        }

        for (i in 1..iterations) {
            for ((index, coordinate) in coordinates.withIndex()) {
                val currentPos = result.indexOf(coordinate)
                result.removeAt(currentPos)
                val newPos = calcNewPos(currentPos, coordinate.number, inputLength - 1)
                result.add(newPos, coordinate)
                //println(index)
            }
        }
        // find 0
        var zeroPos = 0
        for ((index, coord) in result.withIndex()) {
            if (coord.number == 0L)
                zeroPos = index
        }
        return result[(zeroPos + 1000) % inputLength].number + result[(zeroPos + 2000) % inputLength].number + result[(zeroPos + 3000) % inputLength].number
    }

    fun calcNewPos(index: Int, number: Long, inputWidth: Int): Int =
        ((((index + number) % inputWidth) + inputWidth)%inputWidth).toInt()


    data class Coordinate(val origPos: Int, val number: Long, var mixed: Boolean = false) {
        override fun toString() = number.toString()
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Coordinate

            if (origPos != other.origPos) return false

            return true
        }

        override fun hashCode(): Int {
            return origPos
        }

    }
}
