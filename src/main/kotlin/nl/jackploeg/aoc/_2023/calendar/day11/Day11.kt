package nl.jackploeg.aoc._2023.calendar.day11

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import java.util.*
import javax.inject.Inject
import kotlin.math.abs

class Day11 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory
) {
  // find shortest paths
  fun findShortestPaths(fileName: String, expansionFactor: Long): Long
  {
    val lines = readStringFile(fileName)
    val (universe, galaxies) = expandUniverse(lines, expansionFactor)
    val shortestPaths: MutableMap<Path, Int> = mutableMapOf()
    (1..galaxies.size).forEach { first ->
      ((first + 1)..galaxies.size).forEach { second ->
        shortestPaths[Path(galaxies[first]!!, galaxies[second]!!)] = Int.MAX_VALUE
      }
    }
    (1..galaxies.size).forEach { findShortestPathsFromGalaxy(it, galaxies.size - it, universe, galaxies, shortestPaths) }

    return shortestPaths.map { it.value + abs(it.key.galaxy2.emptyRowsAbove-it.key.galaxy1.emptyRowsAbove) + abs(it.key.galaxy2.emptyColumnsLeft-it.key.galaxy1.emptyColumnsLeft) }.sum()

  }

  fun findShortestPathsFromGalaxy(
    start: Int,
    numberOfPaths: Int,
    universe: List<List<Int>>,
    galaxies: Map<Int, Galaxy>,
    shortestPaths: MutableMap<Path, Int>
  )
  {
    var startState = State(Cell(-1, -1), Int.MAX_VALUE)

    val pathsDetermined: MutableSet<Int> = mutableSetOf()
    val universeHeight = universe.size
    val universeWidth = universe[0].size
    val stateUniverse: MutableList<MutableList<State>> = mutableListOf()

    universe.forEachIndexed { row, line ->
      run {
        val stateLine: MutableList<State> = mutableListOf()
        line.forEachIndexed { column, cell ->
          if (cell == start)
          {
            startState = State(Cell(row, column), 0)
            stateLine.add(startState)
          } else
          {
            stateLine.add(State(Cell(row, column), Int.MAX_VALUE))
          }
        }
        stateUniverse.add(stateLine)
      }
    }

    while (pathsDetermined.size < numberOfPaths)
    {
      val todo: Deque<State> = ArrayDeque()
      todo.add(startState)

      fun checkCell(cell: Cell, distance: Int)
      {
        val cellToCheck = stateUniverse[cell.row][cell.column]
        cellToCheck.seen = true
        if (universe[cell.row][cell.column] != 0)
        {
          pathsDetermined.add(universe[cell.row][cell.column])
        }
        if (cellToCheck.distance > distance)
        {
          cellToCheck.distance = distance
          todo.add(cellToCheck)
        }
      }

      fun checkNeighbours(current: State)
      {
        with(current.cell) {
          if (row > 0)
          {
            checkCell(Cell(row - 1, column), current.distance + 1)
          }
          if (row < universeHeight - 1)
          {
            checkCell(Cell(row + 1, column), current.distance + 1)
          }
          if (column > 0)
          {
            checkCell(Cell(row, column - 1), current.distance + 1)
          }
          if (column < universeWidth - 1)
          {
            checkCell(Cell(row, column + 1), current.distance + 1)
          }
        }
      }

      while (!todo.isEmpty())
      {
        val current = todo.removeFirst()
        checkNeighbours(current)
      }

      stateUniverse.forEachIndexed { row, line ->
        run {
          line.forEachIndexed { column, cell ->
            if (universe[row][column] != 0 && universe[row][column] > start)
            {
              shortestPaths[Path(galaxies[start]!!, galaxies[universe[row][column]]!!)] = cell.distance
            }
          }
        }
      }
    }
  }

  fun expandUniverse(lines: List<String>, expansionFactor: Long): Pair<List<List<Int>>, Map<Int, Galaxy>>
  {
    var galaxyCounter = 1
    val universe: MutableList<MutableList<Int>> = mutableListOf()
    val emptyRows: MutableList<Int> = mutableListOf()

    lines.forEachIndexed { index, it ->
      val galaxyLine = it.map { if (it == '.') 0 else galaxyCounter++ }
      universe.add(galaxyLine.toMutableList())
      // expand empty rows
      if (!it.contains('#'))
      {
        emptyRows.add(index)
      }
    }
    // expand empty columns
    val emptyColumns: MutableList<Int> = mutableListOf()
    for (i in 0 until universe[0].size)
    {
      val column = universe.map { it[i] }
      if (column.all { it == 0 })
      {
        emptyColumns.add(i)
      }
    }

    val galaxies: MutableMap<Int, Galaxy> = mutableMapOf()
    universe.forEachIndexed { row, line ->
      line.forEachIndexed { column, cell -> if(cell!=0) galaxies[cell] = Galaxy(cell, row,column) }
    }

    for (galaxy in galaxies.values) {
      galaxy.emptyColumnsLeft = (expansionFactor-1) * (emptyColumns.count { it < galaxy.column })
      galaxy.emptyRowsAbove = (expansionFactor-1) * (emptyRows.count { it < galaxy.row })
    }


    return Pair(universe, galaxies)
  }

  data class Galaxy(val number: Int, val row: Int, val column: Int, var emptyColumnsLeft: Long = 0L, var emptyRowsAbove: Long= 0L)
  data class Path(val galaxy1: Galaxy, val galaxy2: Galaxy)
  data class Cell(val row: Int, val column: Int)
  data class State(val cell: Cell, var distance: Int, var seen: Boolean = false)
}
