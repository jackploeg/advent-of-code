package nl.jackploeg.aoc._2022.calendar.day01

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory

class Day01 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(filename: String) =
      generatorFactory.forFile(filename).readAs(::day01) { input ->
    -1
  }

  fun partTwo(filename: String) =
      generatorFactory.forFile(filename).readAs(::day01) { input ->
    -1
  }

  private fun day01(line: String) = 4
}
