package nl.jackploeg.aoc._2025.calendar.day05

import javax.inject.Inject
import kotlin.Int
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory

class Day05 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(filename: String): Int =
      generatorFactory.forFile(filename).readAs(::day05) { input ->
    -1
  }

  fun partTwo(filename: String): Int =
      generatorFactory.forFile(filename).readAs(::day05) { input ->
    -1
  }

  private fun day05(line: String) = 4
}
