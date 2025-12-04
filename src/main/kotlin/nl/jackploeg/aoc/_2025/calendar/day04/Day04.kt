package nl.jackploeg.aoc._2025.calendar.day04

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.grid.Cell
import nl.jackploeg.aoc.grid.CharacterGrid
import nl.jackploeg.aoc.grid.surroundings
import javax.inject.Inject

class Day04 @Inject constructor() {
  fun partOne(filename: String): Int {
      val grid = CharacterGrid.fromFile(filename)
      return grid.entries
          .filter { it.value == '@' }
          .filter { entry -> entry.key.surroundings().count { grid[it] == '@' } < 4 }
          .size
  }

  fun partTwo(filename: String): Int {
      val grid = CharacterGrid.fromFile(filename)
      var rollsToRemove: List<Map.Entry<Cell, Char>>
      var removedRollsCount = 0
      do {
          rollsToRemove = grid.entries
              .filter { it.value == '@' }
              .filter { entry -> entry.key.surroundings().count { grid[it] == '@' } < 4 }
          removedRollsCount += rollsToRemove.size
          rollsToRemove.forEach { grid.setValue(it.key, '.') }
      } while ( !rollsToRemove.isEmpty())
      return removedRollsCount
  }

}
