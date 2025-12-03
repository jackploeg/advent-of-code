package nl.jackploeg.aoc._2025.calendar.day04

import java.io.File
import javax.inject.Inject
import kotlin.Int
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory

class Day04 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(filename: String): Int =
      generatorFactory.forFile(filename).readAs(::day04) { input ->
    -1
  }

  fun partTwo(filename: String): Int =
      generatorFactory.forFile(filename).readAs(::day04) { input ->
    -1
  }

  private fun day04(line: String) = 4
}
