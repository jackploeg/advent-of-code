package nl.jackploeg.aoc._2023.calendar.day16

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.Direction
import nl.jackploeg.aoc.utilities.Direction.*
import nl.jackploeg.aoc.utilities.readStringFile
import java.util.*
import javax.inject.Inject

class Day16 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  // find energized tiles
  fun partOne(fileName: String): Int {
    val contraption: List<List<Tile>> =
      readStringFile(fileName).mapIndexed { y, line ->
        line.toCharArray().toTypedArray().mapIndexed { x, char -> Tile(x, y, TileType.getTile(char)) }
      }
    contraption[0][0].addDirection(RIGHT)
    return countEnergizedTiles(contraption, 0, 0)
  }

  // find max energized tiles
  fun partTwo(fileName: String): Int {
    var contraption: List<List<Tile>> =
      readStringFile(fileName).mapIndexed { y, line ->
        line.toCharArray().toTypedArray().mapIndexed { x, char -> Tile(x, y, TileType.getTile(char)) }
      }
    val width = contraption[0].count()
    val height = contraption.count()
    var max = 0
    (0 until width).forEach {
      contraption[0][it].addDirection(DOWN)
      val new = countEnergizedTiles(contraption, 0, it)
      if (new > max) max = new
      contraption =
        readStringFile(fileName).mapIndexed { y, line ->
          line.toCharArray().toTypedArray().mapIndexed { x, char -> Tile(x, y, TileType.getTile(char)) }
        }
    }
    (0 until width).forEach {
      contraption[height - 1][it].addDirection(UP)
      val new = countEnergizedTiles(contraption, height - 1, it)
      if (new > max) max = new
      contraption =
        readStringFile(fileName).mapIndexed { y, line ->
          line.toCharArray().toTypedArray().mapIndexed { x, char -> Tile(x, y, TileType.getTile(char)) }
        }
    }
    (0 until height).forEach {
      contraption[it][0].addDirection(RIGHT)
      val new = countEnergizedTiles(contraption, it, 0)
      if (new > max) max = new
      contraption =
        readStringFile(fileName).mapIndexed { y, line ->
          line.toCharArray().toTypedArray().mapIndexed { x, char -> Tile(x, y, TileType.getTile(char)) }
        }
    }
    (0 until height).forEach {
      contraption[it][width - 1].addDirection(LEFT)
      val new = countEnergizedTiles(contraption, it, width - 1)
      if (new > max) max = new
      contraption =
        readStringFile(fileName).mapIndexed { y, line ->
          line.toCharArray().toTypedArray().mapIndexed { x, char -> Tile(x, y, TileType.getTile(char)) }
        }
    }
    return max
  }

  fun countEnergizedTiles(contraption: List<List<Tile>>, startY: Int, startX: Int): Int {

    val contraptionWidth = contraption[0].count()
    val contraptionHeight = contraption.count()
    val todo: Deque<Todo> = ArrayDeque()
    todo.add(Todo(contraption[startY][startX], contraption[startY][startX].directions.toList().first().reversed()))
    val seen: MutableSet<Todo> = mutableSetOf()
    while (!todo.isEmpty()) {
      val current = todo.removeFirst()
      if (!seen.contains(current)) {
        seen.add(current)
        val tile = current.tile
        when (current.comingFrom) {
          LEFT -> when (tile.type) {
            TileType.EMPTY, TileType.HOR_SPLITTER ->
              addRight(tile, RIGHT, contraptionWidth, contraption, todo)

            TileType.VERT_SPLITTER -> {
              addTop(tile, UP, contraption, todo)
              addBottom(tile, DOWN, contraptionHeight, contraption, todo)
            }

            TileType.MIRROR_UP -> {
              addTop(tile, RIGHT, contraption, todo)
            }

            TileType.MIRROR_DOWN ->
              addBottom(tile, RIGHT, contraptionHeight, contraption, todo)
          }

          RIGHT -> when (tile.type) {
            TileType.EMPTY, TileType.HOR_SPLITTER ->
              addLeft(tile, LEFT, contraption, todo)

            TileType.VERT_SPLITTER -> {
              addTop(tile, UP, contraption, todo)
              addBottom(tile, DOWN, contraptionHeight, contraption, todo)
            }

            TileType.MIRROR_UP -> {
              addBottom(tile, DOWN, contraptionHeight, contraption, todo)
            }

            TileType.MIRROR_DOWN ->
              addTop(tile, UP, contraption, todo)
          }

          UP -> when (tile.type) {
            TileType.EMPTY, TileType.VERT_SPLITTER ->
              addBottom(tile, DOWN, contraptionHeight, contraption, todo)

            TileType.HOR_SPLITTER -> {
              addLeft(tile, LEFT, contraption, todo)
              addRight(tile, RIGHT, contraptionHeight, contraption, todo)
            }

            TileType.MIRROR_UP -> {
              addLeft(tile, LEFT, contraption, todo)
            }

            TileType.MIRROR_DOWN ->
              addRight(tile, RIGHT, contraptionWidth, contraption, todo)
          }


          DOWN -> when (tile.type) {
            TileType.EMPTY, TileType.VERT_SPLITTER ->
              addTop(tile, UP, contraption, todo)

            TileType.HOR_SPLITTER -> {
              addLeft(tile, LEFT, contraption, todo)
              addRight(tile, RIGHT, contraptionHeight, contraption, todo)
            }

            TileType.MIRROR_UP -> {
              addRight(tile, RIGHT, contraptionWidth, contraption, todo)
            }

            TileType.MIRROR_DOWN ->
              addLeft(tile, LEFT, contraption, todo)
          }
        }
      }
    }
    var count = 0
    contraption.forEach { row -> row.forEach { if (!it.directions.isEmpty()) count++ } }
    //return contraption.count { row -> row.count { !it.directions.isEmpty() } }
    return count
  }

  private fun addBottom(
    tile: Tile,
    direction: Direction,
    contraptionHeight: Int,
    contraption: List<List<Tile>>,
    todo: Deque<Todo>
  ) {
    if (tile.y < contraptionHeight - 1) {
      val next = contraption[tile.y + 1][tile.x]
      next.addDirection(direction)
      todo.add(Todo(next, UP))
    }
  }

  private fun addTop(
    tile: Tile,
    direction: Direction,
    contraption: List<List<Tile>>,
    todo: Deque<Todo>
  ) {
    if (tile.y > 0) {
      val next = contraption[tile.y - 1][tile.x]
      next.addDirection(direction)
      todo.add(Todo(next, DOWN))
    }
  }

  private fun addRight(
    tile: Tile,
    direction: Direction,
    contraptionWidth: Int,
    contraption: List<List<Tile>>,
    todo: Deque<Todo>
  ) {
    if (tile.x < contraptionWidth - 1) {
      val next = contraption[tile.y][tile.x + 1]
      next.addDirection(direction)
      todo.add(Todo(next, LEFT))
    }
  }

  private fun addLeft(
    tile: Tile,
    direction: Direction,
    contraption: List<List<Tile>>,
    todo: Deque<Todo>
  ) {
    if (tile.x > 0) {
      val next = contraption[tile.y][tile.x - 1]
      next.addDirection(direction)
      todo.add(Todo(next, RIGHT))
    }
  }

  data class Todo(val tile: Tile, val comingFrom: Direction)
  data class Tile(val x: Int, val y: Int, val type: TileType, var directions: MutableSet<Direction> = mutableSetOf()) {
    fun addDirection(direction: Direction) =
      directions.add(direction)
  }

  enum class TileType(val char: Char) {
    EMPTY('.'),
    HOR_SPLITTER('-'),
    VERT_SPLITTER('|'),
    MIRROR_DOWN('\\'),
    MIRROR_UP('/');

    companion object {
      fun getTile(char: Char) =
        TileType.values().first { it.char == char }
    }
  }
}
