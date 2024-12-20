package nl.jackploeg.aoc._2023.calendar.day17

import nl.jackploeg.aoc.grid.Direction
import nl.jackploeg.aoc.grid.Direction.*

data class Node(
  val row: Int,
  val col: Int,
  val directionTraveling: Direction = EAST,
  val stepsInDirection: Int = 0
) {
  fun move(directionToMove: Direction, map: List<List<Int>>): Node? {
    val (rowDelta, columnDelta) = when (directionToMove) {
      NORTH -> -1 to  0
      SOUTH ->  1 to  0
      EAST ->  0 to  1
      WEST ->  0 to -1
    }

    val newRow = row + rowDelta
    val newCol = col + columnDelta

    return if (map.indices.contains(newRow) && map[newRow].indices.contains(newCol)) {
      val newSteps = if (directionToMove == directionTraveling) stepsInDirection + 1 else 1
      Node(newRow, newCol, directionToMove, newSteps)
    } else {
      null
    }
  }
}
