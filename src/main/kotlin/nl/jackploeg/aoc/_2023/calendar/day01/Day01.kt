package nl.jackploeg.aoc._2023.calendar.day01

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import javax.inject.Inject

class Day01 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(filename: String) = generatorFactory.forFile(filename).read { input ->
    input.map { lineFromFile ->
      "${lineFromFile.first { it.isDigit() }}${lineFromFile.last { it.isDigit() }}".toInt()
    }.sum()
  }

  fun partTwo(filename: String) = generatorFactory.forFile(filename).read { input ->
    input.map { lineFromFile -> replaceDigitNames(lineFromFile) }
      .map { calibrationValue ->
        Pair(
          calibrationValue.toCharArray().first { it.isDigit() }.digitToInt(),
          calibrationValue.toCharArray().last { it.isDigit() }.digitToInt()
        )
      }.sumOf { (it.first * 10) + it.second }
  }

  private fun replaceDigitNames(input: String): String =
    input.replace("one", "o1e")
      .replace("two", "t2o")
      .replace("three", "t3e")
      .replace("four", "f4r")
      .replace("five", "f5e")
      .replace("six", "s6x")
      .replace("seven", "s7n")
      .replace("eight", "e8t")
      .replace("nine", "n9e")

}
