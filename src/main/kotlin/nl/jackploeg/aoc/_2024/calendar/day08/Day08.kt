package nl.jackploeg.aoc._2024.calendar.day08

import nl.jackploeg.aoc.grid.Cell
import nl.jackploeg.aoc.grid.CharacterGrid
import nl.jackploeg.aoc.grid.col
import nl.jackploeg.aoc.grid.row
import org.apache.commons.math3.util.ArithmeticUtils.gcd
import javax.inject.Inject

class Day08 @Inject constructor() {
    fun partOne(filename: String): Int {
        val grid = CharacterGrid.fromFile(filename)
        val antinodes: MutableSet<Cell> = mutableSetOf()
        grid.entries.forEach { cell ->
            grid.entries.filter { it.value != '.' }
                .forEach { antenna ->
                    val distanceX = antenna.key.col - cell.key.col
                    val distanceY = antenna.key.row - cell.key.row
                    if (distanceY != 0 || distanceX != 0) {
                        val cellToCheck = Cell(cell.key.row + 2 * distanceY, cell.key.col + 2 * distanceX)
                        if (grid.contains(cellToCheck) && grid.get(cellToCheck) == grid.get(antenna.key)) {
                            antinodes.add(cell.key)
                        }
                    }
                }
        }
        return antinodes.size
    }

    fun partTwo(filename: String): Int {
        val grid = CharacterGrid.fromFile(filename)
        val antinodes: MutableSet<Cell> = mutableSetOf()
        grid.entries.forEach { cell ->
            grid.entries.filter { it.value != '.' }
                .forEach { antenna ->
                    var distanceX = antenna.key.col - cell.key.col
                    var distanceY = antenna.key.row - cell.key.row
                    if (distanceY != 0 || distanceX != 0) {
                        val gcd = gcd(distanceY, distanceX)
                        distanceY = distanceY / gcd
                        distanceX = distanceX / gcd
                        var cellToCheck = Cell(cell.key.row + 2 * distanceY, cell.key.col + 2 * distanceX)
                        while (grid.contains(cellToCheck)) {
                            if (antenna.key != cellToCheck && grid.get(cellToCheck) == grid.get(antenna.key)) {
                                antinodes.add(cell.key)
                            }
                            cellToCheck = Cell(cellToCheck.row + 2 * distanceY, cellToCheck.col + 2 * distanceX)
                        }
                    }
                }
        }
        // make sure antennas are added
        grid.entries.filter { it.value != '.' }
            .filter { antenna -> grid.entries.any { it.key != antenna.key && it.value == antenna.value } }
            .forEach { antinodes.add(it.key) }
        return antinodes.size
    }

}
