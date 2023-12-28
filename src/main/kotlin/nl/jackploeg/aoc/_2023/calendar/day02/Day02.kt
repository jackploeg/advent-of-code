package nl.jackploeg.aoc._2023.calendar.day02

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import javax.inject.Inject

class Day02 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  val red = 12
  val green = 13
  val blue = 14

  fun partOne(filename: String) = generatorFactory.forFile(filename).read { input ->
    input.map { line -> parseGame(line) }
    .filter { game -> game.draws.none { it.red > red || it.green > green || it.blue > blue } }.sumOf { it.id }
  }

  fun partTwo(filename: String) = generatorFactory.forFile(filename).read { input ->
    input.map { line -> parseGame(line) }.sumOf { it.getPower() }
  }

  data class Draw(val red: Int, val green: Int, val blue: Int)
  data class Game(val id: Int, val draws: List<Draw>) {
    fun getPower(): Int =
      draws.maxOf { it.red } * draws.maxOf { it.green } * draws.maxOf { it.blue }
  }

  private fun parseGame(line: String): Game {
    val gameId = line.substringBefore(":")
      .split(" ")[1].toInt()
    val draws = line.substringAfter(":")
      .split(";")
      .map { singleDraw -> singleDraw.trim().split(",") }
      .map { listOfCubesPerColor -> parseDraw(listOfCubesPerColor) }
    return Game(gameId, draws)
  }

  fun parseDraw(colorCubes: List<String>): Draw {
    var red = 0
    var green = 0
    var blue = 0
    colorCubes.forEach {
      red = if (it.contains("red")) it.trim().split(" ")[0].toInt() else red
      green = if (it.contains("green")) it.trim().split(" ")[0].toInt() else green
      blue = if (it.contains("blue")) it.trim().split(" ")[0].toInt() else blue
    }
    return Draw(red, green, blue)
  }
}
