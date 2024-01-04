package nl.jackploeg.aoc._2023.calendar.day21

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import java.util.*
import javax.inject.Inject

class Day21 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(fileName: String, iterations: Int): Int {
    val map: List<List<Char>> = readStringFile(fileName).map { line -> line.toList() }
    val layoutHeight = map.size
    val layoutWidth = map[0].size
    var start = Cell(0, 0)
    map.forEachIndexed { rownum, row ->
      row.forEachIndexed { col, char ->
        if (char == 'S') start = Cell(rownum, col)
      }
    }

    val todo: Deque<Cell> = ArrayDeque()
    todo.add(start)

    val reachableCells: MutableSet<Cell> = mutableSetOf(start)
//    println(start)
    var iteration = 0
    while (iteration < iterations) {
      iteration++
      val tmp: MutableSet<Cell> = mutableSetOf()
      tmp.addAll(reachableCells)
      tmp.forEach { pos ->
        reachableCells.remove(pos)
        if (pos.row > 0) {
          if (map[pos.row - 1][pos.col] != '#') {
            reachableCells.add(Cell(pos.row - 1, pos.col))
          }
        }
        if (pos.row < layoutHeight - 1) {
          if (map[pos.row + 1][pos.col] != '#') {
            reachableCells.add(Cell(pos.row + 1, pos.col))
          }
        }
        if (pos.col > 0) {
          if (map[pos.row][pos.col - 1] != '#') {
            reachableCells.add(Cell(pos.row, pos.col - 1))
          }
        }
        if (pos.col < layoutWidth - 1) {
          if (map[pos.row][pos.col + 1] != '#') {
            reachableCells.add(Cell(pos.row, pos.col + 1))
          }
        }
      }
    }
    return reachableCells.size
  }

  fun partTwo(fileName: String, iterations: Int): Long {
    val map = mutableListOf<List<Char>>()
    lateinit var start: Location

    readStringFile(fileName).forEachIndexed { rowIndex, row ->
      val r = row.toCharArray().mapIndexed { colIndex, c ->
        when (c) {
          'S' -> '.'.also { start = Location(Point(rowIndex, colIndex), 0, 0) }
          else -> c
        }
      }
      map.add(r)
    }

    val height: Int = map.size
    val width: Int = map[0].size

    val iterationsUntilFirstEdge = iterations % width
    val cornerToCornerIterations = width * 2
    val steps = iterationsUntilFirstEdge + cornerToCornerIterations

    val locations = createLocationsFromMap(map, width, height, start, steps)
    val counts = countPositions(locations)

    val tips: List<Long> = TIPS.mapNotNull { counts[it]?.toLong() }
    val smallEdges: List<Long> = SMALL_EDGES.mapNotNull { counts[it]?.toLong() }
    val bigEdges: List<Long> = BIG_EDGES.mapNotNull { counts[it]?.toLong() }
    val evenCenter: Long = counts[CENTER_IF_EVEN]?.toLong() ?: 0
    val oddCenter: Long = counts[CENTER_IF_ODD]?.toLong() ?: 0

    val edgeCount = iterations / width

    return tips.sum() +
            (smallEdges.sum() * (edgeCount)) +
            (bigEdges.sum() * (edgeCount - 1)) +
            (evenCenter * (edgeCount) * (edgeCount)) +
            (oddCenter * (edgeCount - 1) * (edgeCount - 1))
  }

  private fun countPositions(locations: Set<Location>): Map<Pair<Int, Int>, Int> {
    return mutableMapOf<Pair<Int, Int>, Int>().apply {
      locations.forEach { location -> merge(location.x to location.y, 1, Int::plus) }
    }
  }

  private fun createLocationsFromMap(
    map: MutableList<List<Char>>,
    width: Int,
    height: Int,
    start: Location,
    steps: Int
  ): Set<Location> {
    val locations = mutableSetOf<Location>().apply { add(start) }

    repeat(steps) {
      val nextLocations = mutableSetOf<Location>().apply {
        locations.forEach { loc ->
          val (row, col) = loc.point

          loc.point.neighbors(map).forEach { add(Location(it, loc.x, loc.y)) }

          if (col == 0) add(Location(Point(row = row, col = width - 1), x = loc.x - 1, y = loc.y))
          if (col == width - 1) add(Location(Point(row = row, col = 0), x = loc.x + 1, y = loc.y))
          if (row == 0) add(Location(Point(row = height - 1, col = col), x = loc.x, y = loc.y - 1))
          if (row == height - 1) add(Location(Point(row = 0, col = col), x = loc.x, y = loc.y + 1))
        }
      }
      locations.clear()
      locations.addAll(nextLocations)
    }

    return locations
  }

  data class Cell(val row: Int, val col: Int)



  data class Point(val row: Int, val col: Int) {
    val NORTH = (-1 to 0)
    val SOUTH = (1 to 0)
    val EAST = (0 to 1)
    val WEST = (0 to -1)
    val DIRECTIONS = listOf(NORTH, SOUTH, EAST, WEST)
    fun neighbors(map: List<List<Char>>): List<Point> {
      return if (map[row][col] == '#') {
        emptyList()
      } else {
        DIRECTIONS.map { (dr, dc) -> row + dr to col + dc }
          .mapNotNull { (r, c) ->
            Point(r, c).takeIf { (0 <= r && r < map.size && 0 <= c && c < map[r].size && map[r][c] == '.') }
          }
      }
    }
  }

  data class Location(val point: Point, val x: Int, val y: Int)

  val CENTER_IF_EVEN = (0 to 1)
  val CENTER_IF_ODD = (0 to 0)

  val SMALL_EDGES = listOf(-2 to -1, -2 to 1, 2 to -1, 2 to 1)
  val BIG_EDGES = listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)
  val TIPS = listOf(-2 to 0, 2 to 0, 0 to -2, 0 to 2)
}
